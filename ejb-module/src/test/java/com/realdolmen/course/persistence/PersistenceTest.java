package com.realdolmen.course.persistence;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Prepares a persistence context for testing with JPA.
 * Use {@link #properties()} to configure database properties
 */
public abstract class PersistenceTest extends Assert {
    public static final String DRIVER = "javax.persistence.jdbc.driver";
    public static final String URL = "javax.persistence.jdbc.url";
    public static final String USER = "javax.persistence.jdbc.user";
    public static final String PASSWORD = "javax.persistence.jdbc.password";

    private static final Logger logger = LoggerFactory.getLogger(PersistenceTest.class);

    private static final String PERSISTENCE_UNIT_NAME = "MyTestPersistenceUnit";

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction transaction;

    @Before
    public void initialize() throws SQLException {
        initializeEntityManagerFactory();
        logger.info("Creating transacted EntityManager");
        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();
        transaction.begin();
    }

    @After
    public void destroy() {
        completeTransaction();
        destroyEntityManager();
        destroyEntityManagerFactory();
    }

    private void initializeEntityManagerFactory() throws SQLException {
        recreateSchema();
        loadPersistenceUnit();
    }

    private void loadPersistenceUnit() {
        logger.info("Creating EntityManagerFactory from persistence unit " + PERSISTENCE_UNIT_NAME);
        entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, properties());
    }

    private void recreateSchema() throws SQLException {
        DatabaseEngine databaseEngine = DatabaseEngine.current();
        if(!databaseEngine.isInMemory) {
            logger.info("Recreating database schema '" + databaseEngine.schema + "'");
            try (Connection connection = newConnection()) {
                connection.createStatement().execute("drop schema " + databaseEngine.schema);
                connection.createStatement().execute("create schema " + databaseEngine.schema);
            }
        }
    }

    /**
     * Provides connection settings for the database. These settings will merge with the ones already in the test persistence.xml.
     * Subclasses can override this to customize.
     * @return Map of JPA properties.
     */
    protected Map<String, String> properties() {
        DatabaseEngine databaseEngine = DatabaseEngine.current();
        HashMap<String, String> properties = new HashMap<>();
        properties.put(DRIVER, databaseEngine.driverClass);
        properties.put(URL, databaseEngine.url);
        properties.put(USER, databaseEngine.username);
        properties.put(PASSWORD, databaseEngine.password);
        return Collections.unmodifiableMap(properties);
    }

    private void destroyEntityManager() {
        if(entityManager != null) {
            entityManager.close();
        }
    }

    private void completeTransaction() {
        logger.info("Committing and closing transacted EntityManager");
        if(transaction != null) {
            if(transaction.getRollbackOnly()) {
                transaction.rollback();
            } else {
                transaction.commit();
            }
        }
    }

    public void destroyEntityManagerFactory() {
        logger.info("Closing EntityManagerFactory");
        if(entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }

    /**
     * Obtains the current EntityManager. Use this to write tests against.
     */
    protected EntityManager entityManager() {
        return this.entityManager;
    }

    /**
     * Obtains a <strong>new</strong> JDBC connection using connection settings defined in {@link #properties()}
     * @return A new JDBC connection. Callsite is responsible for closing.
     * @throws SQLException When the shit hits the fan.
     */
    protected Connection newConnection() throws SQLException {
        Map<String, String> properties = properties();
        return DriverManager.getConnection(properties.get(URL), properties.get(USER), properties.get(PASSWORD));
    }
}
