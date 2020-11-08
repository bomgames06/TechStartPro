package br.com.bomgames06.tech.listener;

import br.com.bomgames06.tech.business.CategoryBusiness;
import br.com.bomgames06.tech.dao.generic.GenericDao;
import br.com.bomgames06.tech.entity.Category;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import javax.persistence.EntityManager;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

public class StartListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        EntityManager entityManager = GenericDao.getEntityManager();
        try {
            entityManager.getTransaction().begin();

            InputStream inputStream = getClass().getResourceAsStream("/META-INF/application.properties");
            Properties properties = new Properties();
            properties.load(inputStream);
            boolean enableImportCsvCategory = Boolean.parseBoolean(properties.getProperty("csv.import.category.enable"));
            if (enableImportCsvCategory) {
                Reader categoryReader = new InputStreamReader(getClass().getResourceAsStream("/META-INF/csv/category.csv"));
                CSVParser csvCategory = CSVFormat.DEFAULT.parse(categoryReader);
                for (CSVRecord record : csvCategory) {
                    String name = record.get(0);
                    CategoryBusiness categoryBusiness = new CategoryBusiness(entityManager);
                    Category category = new Category();
                    category.setName(name);
                    categoryBusiness.save(category);
                }
            }

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            entityManager.close();
            e.printStackTrace();
        }
    }
}
