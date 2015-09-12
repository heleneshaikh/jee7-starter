package com.realdolmen.course.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum DatabaseEngine {
    mysql("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/", "root", "root", false),
    h2("org.h2.Driver", "jdbc:h2:mem:", "sa", "", true);

    private static final String DATABASE_ENGINE_SYSTEM_PARAMETER = "current";
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseEngine.class);

    public final String url;
    public final String username;
    public final String driverClass;
    public final String password;
    public final String schema = "test";
    public final boolean isInMemory;

    DatabaseEngine(String driverClass, String urlPrefix, String username, String password, boolean isInMemory) {
        this.password = password;
        this.driverClass = driverClass;
        this.username = username;
        this.isInMemory = isInMemory;
        this.url = urlPrefix + schema;
    }

    public static DatabaseEngine current() {
        String databaseEngineProperty = System.getProperty(DATABASE_ENGINE_SYSTEM_PARAMETER);
        DatabaseEngine databaseEngine;
        if(databaseEngineProperty == null) {
            LOGGER.warn("Missing system property -DdatabaseEngine. Using default.");
            databaseEngine = DatabaseEngine.mysql;
        } else {
            databaseEngine = DatabaseEngine.valueOf(databaseEngineProperty);
        }
        LOGGER.info("Using database engine: " + databaseEngine);
        return databaseEngine;
    }
}
