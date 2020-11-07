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

    public void save (Category object) {
        this.categoryDao.save(object);
    }

    public void delete (Category object) {
        this.categoryDao.delete(object);
    }

    public List<Category> listCategory() {
        return this.categoryDao.listCategory();
    }
}
