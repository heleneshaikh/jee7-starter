package com.realdolmen.course.integration;

import com.realdolmen.course.persistence.DataSetPersistenceTest;
import com.realdolmen.course.persistence.DatabaseEngine;
import org.junit.Before;
import org.junit.BeforeClass;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

import static org.junit.Assume.assumeTrue;

/**
 * Remote integration tests require a running server.
 */
public abstract class RemoteIntegrationTest extends DataSetPersistenceTest {
    public static final String INTEGRATION_ENABLED_SYSTEM_PROPERTY = "integration";

    private static InitialContext context;

    @BeforeClass
    public static void initializeJndiContext() throws Exception {
        assumeTrue("Integration testing is disabled (enable using -Dintegration)", isPropertySet());
        context = new InitialContext(jdniProperties());
    }

    /**
     * Integration tests invoke RMI calls on an actual running server. Since they assert database state, this means they need to run on the same datasource as the server does, which currently is mysql.
     */
    @Before
    public void verifyCorrectDatabaseEngine() {
        assertTrue("Integration testing should be run on " + DatabaseEngine.mysql + " database engine (in current implementation)", DatabaseEngine.current() == DatabaseEngine.mysql);
    }

    private static boolean isPropertySet() {
        return System.getProperty(INTEGRATION_ENABLED_SYSTEM_PROPERTY) != null;
    }

    private static Hashtable<String, Object> jdniProperties() {
        Hashtable<String, Object> properties = new Hashtable<>();
        properties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        properties.put(Context.PROVIDER_URL, "http-remoting://127.0.0.1:8080");
        properties.put("jboss.naming.client.ejb.context", true);
        return properties;
    }

    protected <T> T lookup(String jndiString) throws NamingException {
        return (T)context.lookup(jndiString);
    }
}
