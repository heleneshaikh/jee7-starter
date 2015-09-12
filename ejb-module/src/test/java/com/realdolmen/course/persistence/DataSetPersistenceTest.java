package com.realdolmen.course.persistence;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Loads a DBUnit test set before every unit test.
 */
public abstract class DataSetPersistenceTest extends PersistenceTest {
    private static final Logger logger = LoggerFactory.getLogger(DataSetPersistenceTest.class);

    @Before
    public void loadDataSet() throws Exception {
        logger.info("Loading datasets");
        try(Connection jdbcConnection = newConnection()) {
            IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);
            if(DatabaseEngine.current() == DatabaseEngine.mysql) {
                connection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory()); // Set factorytype in dbconfig to remove warning
            }
            DatabaseOperation.INSERT.execute(connection, createDataSet());
        }
    }

    /**
     * Builds a combination dataset, including replacing {null} by null.
     * @return The combo dataset that should be executed.
     * @throws DataSetException When the stars are in the wrong position.
     */
    private IDataSet createDataSet() throws DataSetException {
        final FlatXmlDataSetBuilder dataSetBuilder = new FlatXmlDataSetBuilder();
        HashMap<String, Object> replacements = new HashMap<>();
        replacements.put("{null}", null);
        return new ReplacementDataSet(new CompositeDataSet(Arrays.stream(dataSets()).<IDataSet>map(s -> {
            try {
                return dataSetBuilder.build(getClass().getResource(s));
            } catch (DataSetException e) {
                throw new RuntimeException("Problem loading DBUnit dataset", e);
            }
        }).toArray(IDataSet[]::new)), replacements, null);
    }

    /**
     * Provides a list of datasets that should be loaded.
     * Subclasses can override this to provide customization.
     * @return An array of Strings pointing to class-path relative dataset XML files.
     */
    protected String[] dataSets() {
        return new String[] { "/data.xml" };
    }
}
