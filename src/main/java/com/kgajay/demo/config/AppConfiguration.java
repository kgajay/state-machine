package com.kgajay.demo.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import lombok.Data;

/**
 * @author ajay.kg created on 22/07/17.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppConfiguration extends Configuration {

    private DataSourceFactory database;

    private String appName;

}
