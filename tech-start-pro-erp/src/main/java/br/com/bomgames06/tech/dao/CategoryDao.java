package br.com.bomgames06.tech.dao;

import br.com.bomgames06.tech.dao.generic.GenericDao;
import br.com.bomgames06.tech.entity.Category;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao extends GenericDao<Category> {

    public CategoryDao(EntityManager entityManager) {
        super(entityManager);
    }

    public List<Category> listCategory() {
        try {
            String hql =
                    "select c from Category c " +
                    "order by c.name";

            TypedQuery<Category> query = entityManager.createQuery(hql, Category.class);

            return query.getResultList();
        } catch (NoResultException nre) {
            return new ArrayList<>();
        }
    }
}
