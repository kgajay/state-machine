package com.kgajay.demo.app.healthcheck;

import com.codahale.metrics.health.HealthCheck;
import com.kgajay.demo.app.db.DBDao;
import io.dropwizard.db.DataSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ajay.kg created on 22/07/17.
 */
public class DataSourceHealthCheck extends HealthCheck {

    @Autowired
    private DBDao dbDao;

    @Autowired
    private DataSourceFactory database;

    @Override
    protected Result check() throws Exception {
        if (dbDao.healthCheck() > 0) {
            return Result.healthy();
        } else {
            return Result.unhealthy("Cannot connect to mysql " + database.getUrl());
        }
    }
}
