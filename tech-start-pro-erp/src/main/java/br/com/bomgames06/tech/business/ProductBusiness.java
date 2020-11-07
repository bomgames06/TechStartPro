package br.com.bomgames06.tech.business;

import br.com.bomgames06.tech.dao.ProductDao;
import br.com.bomgames06.tech.entity.Product;

import javax.persistence.EntityManager;
import java.util.List;

public class ProductBusiness {

    private final ProductDao productDao;

    public ProductBusiness(EntityManager entityManager) {
        this.productDao = new ProductDao(entityManager);
    }

    public void save (Product object) {
        this.productDao.save(object);
    }

    public void delete (Product object) {
        this.productDao.delete(object);
    }

    public List<Product> listProduct(String name, String description, Double minValue, Double maxValue, List<Long> categories) {
        return this.productDao.listCategory(name, description, minValue, maxValue, categories);
    }
}
