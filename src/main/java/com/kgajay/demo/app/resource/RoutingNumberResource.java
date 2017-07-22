package com.kgajay.demo.app.resource;

import com.codahale.metrics.annotation.Timed;
import com.kgajay.demo.config.AppConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author ajay.kg created on 22/07/17.
 */
@Slf4j
@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Service("routingNumberResource")
public class RoutingNumberResource {

    @Autowired
    private AppConfiguration configuration;


    @GET
    @Path("/app/name")
    @Timed
    public Response getAppName() {
        return Response.ok(configuration.getAppName()).build();
    }

}
