package com.kgajay.demo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.kgajay.demo.app.healthcheck.DataSourceHealthCheck;
import com.kgajay.demo.app.resource.StateMachineResource;
import com.kgajay.demo.config.AppConfiguration;
import com.kgajay.demo.config.SpringConfig;
import com.kgajay.demo.utils.SpringProvider;
import io.dropwizard.Application;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ajay.kg created on 22/07/17.
 */
@Slf4j
public class SyncApplication extends Application<AppConfiguration> {

    @Override
    public String getName() {
        return "UI Scrapping";
    }

    @Override
    public void initialize(Bootstrap<AppConfiguration> bootstrap) {
        super.initialize(bootstrap);
    }

    public void run(AppConfiguration configuration, Environment environment) throws Exception {
        environment.getObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        SpringProvider.INSTANCE.getContext().getBeanFactory().registerSingleton("configuration", configuration);
        SpringProvider.INSTANCE.getContext().getBeanFactory().registerSingleton("environment", environment);
        SpringProvider.INSTANCE.getContext().register(SpringConfig.class);
        SpringProvider.INSTANCE.getContext().refresh();

        environment.lifecycle().manage(new Managed() {

            @Override
            public void start() throws Exception {

            }

            @Override
            public void stop() throws Exception {
                SpringProvider.INSTANCE.getContext().destroy();
            }
        });


        environment.healthChecks().register("database", SpringProvider.INSTANCE.getContext().getBean(DataSourceHealthCheck.class));

        environment.jersey().register(SpringProvider.INSTANCE.getContext().getBean(StateMachineResource.class));

    }


    public static void main(final String[] args) throws Exception {
        log.info("Starting Sync Application");
        new SyncApplication().run("server", args[0]);
    }
}
