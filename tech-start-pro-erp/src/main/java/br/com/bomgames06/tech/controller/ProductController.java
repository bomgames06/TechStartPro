package br.com.bomgames06.tech.controller;

import br.com.bomgames06.tech.business.ProductBusiness;
import br.com.bomgames06.tech.dao.generic.GenericDao;
import br.com.bomgames06.tech.dto.ProductFormDto;
import br.com.bomgames06.tech.entity.Category;
import br.com.bomgames06.tech.entity.Product;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Path("/product")
public class ProductController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getProductList(
            @QueryParam("name") String name,
            @QueryParam("description") String description,
            @QueryParam("minValue") Double minValue,
            @QueryParam("maxValue") Double maxValue,
            @QueryParam("categoryIds") String categoryIds
    ) throws Exception {
        EntityManager entityManager = GenericDao.getEntityManager();
        try {
            entityManager.getTransaction().begin();

            List<Long> categories = null;
            if (categoryIds != null) {
                categories = Arrays.stream(categoryIds.split(",")).map(Long::new).collect(Collectors.toList());
            }

            ProductBusiness productBusiness = new ProductBusiness(entityManager);
            List<Product> products = productBusiness.listProduct(name, description, minValue, maxValue, categories);

            entityManager.getTransaction().commit();

            return products;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addProduct(ProductFormDto productDto) {
        EntityManager entityManager = GenericDao.getEntityManager();
        try {
            entityManager.getTransaction().begin();

            ProductBusiness productBusiness = new ProductBusiness(entityManager);
            Product product = new Product();
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setValue(productDto.getValue());
            product.setCategories(productDto.getCategoryIds().stream().map(categoryId -> {
                Category category = new Category();
                category.setId(categoryId);
                return category;
            }).collect(Collectors.toList()));
            productBusiness.save(product);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void editProduct(@NotNull @PathParam("id") Long id, ProductFormDto productDto) {
        EntityManager entityManager = GenericDao.getEntityManager();
        try {
            entityManager.getTransaction().begin();

            ProductBusiness productBusiness = new ProductBusiness(entityManager);
            Product product = new Product();
            product.setId(id);
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setValue(productDto.getValue());
            product.setCategories(productDto.getCategoryIds().stream().map(categoryId -> {
                Category category = new Category();
                category.setId(categoryId);
                return category;
            }).collect(Collectors.toList()));
            productBusiness.save(product);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteCategory(@NotNull @PathParam("id") Long id) {
        EntityManager entityManager = GenericDao.getEntityManager();
        try {
            entityManager.getTransaction().begin();

            ProductBusiness productBusiness = new ProductBusiness(entityManager);
            Product product = new Product();
            product.setId(id);
            productBusiness.delete(product);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }
}
