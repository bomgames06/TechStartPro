package br.com.bomgames06.tech.controller;

import br.com.bomgames06.tech.business.CategoryBusiness;
import br.com.bomgames06.tech.dao.generic.GenericDao;
import br.com.bomgames06.tech.dto.CategoryFormDto;
import br.com.bomgames06.tech.entity.Category;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

// TODO: make work injection
// TODO: add validations

@Path("/category")
public class CategoryController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Category> getCategoryList() throws Exception {
        EntityManager entityManager = GenericDao.getEntityManager();
        try {
            entityManager.getTransaction().begin();

            CategoryBusiness categoryBusiness = new CategoryBusiness(entityManager);
            List<Category> categories = categoryBusiness.listCategory();
            entityManager.getTransaction().commit();

            return categories;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addCategory(CategoryFormDto categoryDto) {
        EntityManager entityManager = GenericDao.getEntityManager();
        try {
            entityManager.getTransaction().begin();

            CategoryBusiness categoryBusiness = new CategoryBusiness(entityManager);
            Category category = new Category();
            category.setName(categoryDto.getName());
            categoryBusiness.save(category);

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
    public void editCategory(@NotNull @PathParam("id") Long id, CategoryFormDto categoryDto) {
        EntityManager entityManager = GenericDao.getEntityManager();
        try {
            entityManager.getTransaction().begin();

            CategoryBusiness categoryBusiness = new CategoryBusiness(entityManager);
            Category category = new Category();
            category.setId(id);
            category.setName(categoryDto.getName());
            categoryBusiness.save(category);

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

            CategoryBusiness categoryBusiness = new CategoryBusiness(entityManager);
            Category category = new Category();
            category.setId(id);
            categoryBusiness.delete(category);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }
}
