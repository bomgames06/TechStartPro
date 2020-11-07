package br.com.bomgames06.tech.dao;

import br.com.bomgames06.tech.dao.generic.GenericDao;
import br.com.bomgames06.tech.entity.Product;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class ProductDao extends GenericDao<Product> {

    public ProductDao(EntityManager entityManager) {
        super(entityManager);
    }

    public List<Product> listCategory(String name, String description, Double minValue, Double maxValue, List<Long> categories) {
        try {
            String hql = "select p from Product p " +
                    "join p.categories c " +
                    "where 1 = 1 ";
            if (name != null && !name.isEmpty()) {
                hql += "and lower(p.name) like lower(:name) ";
            }
            if (description != null && !description.isEmpty()) {
                hql += "and lower(p.description) like lower(:description) ";
            }
            if (minValue != null) {
                hql += "and p.value >= :minValue ";
            }
            if (minValue != null) {
                hql += "and p.value <= :maxValue ";
            }
            if (categories != null && !categories.isEmpty()) {
                hql += "and c.id in (:categories) ";
            }
            hql += "order by p.name";

            TypedQuery<Product> query = entityManager.createQuery(hql, Product.class);

            if (name != null && !name.isEmpty()) {
                query.setParameter("name", "%" + name + "%");
            }
            if (description != null && !description.isEmpty()) {
                query.setParameter("description", "%" + description + "%");
            }
            if (minValue != null) {
                query.setParameter("minValue", minValue);
            }
            if (minValue != null) {
                query.setParameter("maxValue", maxValue);
            }
            if (categories != null && !categories.isEmpty()) {
                query.setParameter("categories", categories);
            }

            return query.getResultList();
        } catch (NoResultException nre) {
            return new ArrayList<>();
        }
    }
}
