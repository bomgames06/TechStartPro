package br.com.bomgames06.tech.dao.generic;


import br.com.bomgames06.tech.entity.generic.GenericEntity;

import javax.persistence.*;

public class GenericDao<T extends GenericEntity> {

    private static EntityManagerFactory factory;

    static {
        factory = Persistence.createEntityManagerFactory("database");
    }

    public EntityManager entityManager;

    public GenericDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }

    public void save(T object) {
        if (object.getCode() == null) {
            entityManager.persist(object);
        } else {
            entityManager.merge(object);
        }
    }

    public void delete (T object) {
        entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));
    }
}
