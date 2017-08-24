package com.kgajay.demo.config;

import com.kgajay.demo.app.db.DBDao;
import com.kgajay.demo.utils.SpringProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;

/**
 * @author ajay.kg created on 22/07/17.
 */
@Configuration
@ComponentScan({ "com.kgajay.demo.app" })
public class SpringConfig {

    @Inject
    private AppConfiguration configuration;

    @Inject
    private Environment environment;

    @Bean
    public DBIFactory dbiFactory() {
        return new DBIFactory();
    }

    @Bean
    public DataSourceFactory database(){
        return configuration.getDatabase();
    }

    @Bean
    @Inject
    public DBDao dbDao(DBIFactory dbiFactory) {
        return dbiFactory.build(environment, configuration.getDatabase(), "dbDao").onDemand(DBDao.class);
    }


    public static <T> T getBean(Class<T> cls) {
        return SpringProvider.INSTANCE.getContext().getBean(cls);
    }

}
