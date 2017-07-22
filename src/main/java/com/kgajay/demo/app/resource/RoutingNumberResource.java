package com.kgajay.demo.app.resource;

import com.codahale.metrics.annotation.Timed;
import com.kgajay.demo.app.service.RoutingNumberService;
import com.kgajay.demo.config.AppConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

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

    @Autowired
    private RoutingNumberService routingNumberService;

    @GET
    @Path("/app/name")
    @Timed
    public Response getAppName() {
        return Response.ok(configuration.getAppName()).build();
    }


    @POST
    @Path("/search/routing-number")
    @Timed
    public Response routingNumberSearch(List<String> bank) {

        log.info("Search routing number for bank : {}", bank);
        routingNumberService.searchForBankRoutingNumber(bank);
        return Response.ok().build();
    }
}
