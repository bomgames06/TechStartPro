package br.com.bomgames06.tech.business;

import br.com.bomgames06.tech.dao.CategoryDao;
import br.com.bomgames06.tech.entity.Category;

import javax.persistence.EntityManager;
import java.util.List;

public class CategoryBusiness {

    private final CategoryDao categoryDao;

    public CategoryBusiness(EntityManager entityManager) {
        this.categoryDao = new CategoryDao(entityManager);
    }

    public List<Category> listCategory() {
        return this.categoryDao.listCategory();
    }
}
