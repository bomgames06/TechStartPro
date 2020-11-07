package br.com.bomgames06.tech.controller;

import br.com.bomgames06.tech.business.CategoryBusiness;
import br.com.bomgames06.tech.dao.generic.GenericDao;
import br.com.bomgames06.tech.entity.Category;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

// TODO: Improves json parser method
// TODO: make work injection

@Path("/category")
public class CategoryController {

    @GET
    @Produces("application/json")
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
}
