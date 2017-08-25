package com.kgajay.demo;

import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import com.kgajay.demo.config.AppConfiguration;
import io.dropwizard.Configuration;
import io.dropwizard.configuration.ConfigurationFactory;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import io.dropwizard.testing.FixtureHelpers;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import javax.validation.Validation;
import javax.validation.Validator;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author ajay.kg created on 24/08/17.
 * references:  http://wakandan.github.io/2015/09/21/setup-dropwizard-testing-for-dao.html
 */
public class BaseApplicationTest {

    protected final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    protected final ObjectMapper objectMapper = Jackson.newObjectMapper();
    protected AppConfiguration configuration;

    // set up h2 datbase
    protected DBI dbi;
    protected Handle handle;
    protected DataSourceFactory dataSourceFactory;

    @BeforeClass
    public void setUp() throws Exception {
        configuration = getConfiguration("config.yml", AppConfiguration.class);

        Environment environment = new Environment("test-env", objectMapper, null, new MetricRegistry(), null);
        dataSourceFactory = getDataSourceFactory();
        dbi = new DBIFactory().build(environment, dataSourceFactory, "test");
        configuration.setDatabase(dataSourceFactory);
        handle = dbi.open();
        createDatabase();

    }

    /**
     * Returns a configuration object read in from the {@code fileName}.
     */
    protected <T extends Configuration> T getConfiguration(String filename,
            Class<T> configurationClass) throws Exception {
        final ConfigurationFactory<T> configurationFactory = new ConfigurationFactory<>(
                configurationClass, validator, objectMapper, "dw");
        if (filename != null) {
            final File file = new File(Resources.getResource(filename).getFile());
            if (!file.exists())
                throw new FileNotFoundException("File " + file + " not found");
            return configurationFactory.build(file);
        }

        return configurationFactory.build();
    }


    /**
     * This is where you create your databases using handle.createScript() or handle.createStatement() and so on....Remember to
     * wrap them with handle.begin() and handle.commit() so that the change is visible for test code
     */
    public void createDatabase() {
        handle.begin();
        handle.createScript(FixtureHelpers.fixture("script/state_machine.sql")).execute();
        handle.commit();
    }

    /**
     * I'm using h2db so I don't bother clearing up the database...You got the idea
     */

    @AfterClass
    protected void after() {
        handle.close();
    }

    private DataSourceFactory getDataSourceFactory() {
        DataSourceFactory dataSourceFactory = new DataSourceFactory();
        dataSourceFactory.setDriverClass("org.h2.Driver");
        dataSourceFactory.setUrl(String.format("jdbc:h2:mem:test-%s;MODE=MySQL;TRACE_LEVEL_FILE=3",   //this is for in-memory db (*)
                //              "jdbc:h2:./db/test-%s;MODE=MySQL;TRACE_LEVEL_FILE=3",   //this is for file db
                System.currentTimeMillis()));
        dataSourceFactory.setUser("sa");
        dataSourceFactory.setPassword("sa");
        return dataSourceFactory;
    }
}
