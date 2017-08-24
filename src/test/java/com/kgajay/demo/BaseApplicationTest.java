package com.kgajay.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import com.kgajay.demo.config.AppConfiguration;
import io.dropwizard.Configuration;
import io.dropwizard.configuration.ConfigurationFactory;
import io.dropwizard.jackson.Jackson;
import org.testng.annotations.BeforeClass;

import javax.validation.Validation;
import javax.validation.Validator;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author ajay.kg created on 24/08/17.
 */
public class BaseApplicationTest {

    protected final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    protected final ObjectMapper objectMapper = Jackson.newObjectMapper();
    protected AppConfiguration configuration;

    @BeforeClass
    public void setUp() throws Exception {
        configuration = getConfiguration("config.yml", AppConfiguration.class);
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
}
