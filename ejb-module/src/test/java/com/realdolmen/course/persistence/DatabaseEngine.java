package com.realdolmen.course.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum DatabaseEngine {
    /**
     * MySQL based database engine for running against a production-mirror.
     */
    mysql("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/", "root", "", false),

    /**
     * H2 based database enigne for running (fast) in-memory.
     */
    h2("org.h2.Driver", "jdbc:h2:mem:", "sa", "", true);

    private static final DatabaseEngine DEFAULT_DATABASE_ENGINE = DatabaseEngine.mysql;
    private static final String DATABASE_ENGINE_SYSTEM_PARAMETER = "databaseEngine";
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseEngine.class);
    private static final String DATABASE_SCHEMA_NAME = "realdolmen";

    public final String url;
    public final String username;
    public final String driverClass;
    public final String password;
    public final String schema = DATABASE_SCHEMA_NAME;
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
            databaseEngine = DEFAULT_DATABASE_ENGINE;
            LOGGER.warn("Missing system property -DdatabaseEngine. Using default (" + databaseEngine + ").");
        } else {
            databaseEngine = DatabaseEngine.valueOf(databaseEngineProperty);
        }
        LOGGER.info("Using database engine: " + databaseEngine);
        return databaseEngine;
    }
}
