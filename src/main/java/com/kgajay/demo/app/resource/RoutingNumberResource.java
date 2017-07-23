package com.kgajay.demo.app.resource;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.kgajay.demo.app.domain.BankInfo;
import com.kgajay.demo.app.service.RoutingNumberService;
import com.kgajay.demo.config.AppConfiguration;
import com.kgajay.demo.utils.DefaultObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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


    @POST
    @Path("/bank-info")
    @Timed
    public Response addBankInfo(@Valid final BankInfo bankInfo) throws JsonProcessingException {
        log.info("Entry:: addBankInfo request : {}", DefaultObjectMapper.INSTANCE.getObjectMapper().writeValueAsString(bankInfo));
        BankInfo response = routingNumberService.addBankBasicDetails(bankInfo);
        log.info("Exit:: addBankInfo response : {}", DefaultObjectMapper.INSTANCE.getObjectMapper().writeValueAsString(response));
        return Response.ok(response).build();
    }

    @GET
    @Path("/bank-info/{id}")
    @Timed
    public Response getBankInfo(@PathParam("id") final Long id) throws JsonProcessingException {
        log.info("Entry:: getBankInfo for : {}", id);
        BankInfo response = routingNumberService.getBankInfoById(id);
        log.info("Exit:: getBankInfo response : {}", DefaultObjectMapper.INSTANCE.getObjectMapper().writeValueAsString(response));
        return Response.ok(response).build();
    }

    @GET
    @Path("/bank-info/search/{routingNumber}")
    @Timed
    public Response getBankInfoByRouting(@PathParam("routingNumber") final Long routingNumber) throws JsonProcessingException {
        log.info("Entry:: getBankInfoByRouting for : {}", routingNumber);
        BankInfo response = routingNumberService.getBankInfoByRoutingNumber(routingNumber);
        log.info("Exit:: getBankInfoByRouting response : {}", DefaultObjectMapper.INSTANCE.getObjectMapper().writeValueAsString(response));
        return Response.ok(response).build();
    }

    @GET
    @Path("/bank-info/search")
    @Timed
    public Response searchBankInfo(@QueryParam("routing_number") final Long routingNumber, @QueryParam("name") final String name) throws JsonProcessingException {
        log.info("Entry:: searchBankInfo for : {}", routingNumber);
        List<BankInfo> response = routingNumberService.searchBankInfo(routingNumber, name);
        log.info("Exit:: searchBankInfo response : {}", DefaultObjectMapper.INSTANCE.getObjectMapper().writeValueAsString(response));
        return Response.ok(response).build();
    }

    @PUT
    @Path("/bank-info/{routingNumber}")
    @Timed
    public Response updateBankInfo(@PathParam("routingNumber") final Long routingNumber, @Valid final BankInfo bankInfo) throws JsonProcessingException {
        log.info("Entry:: updateBankInfo for : {}", routingNumber);
        BankInfo response = routingNumberService.updateBankInfoByRoutingNumber(routingNumber, bankInfo);
        log.info("Exit:: updateBankInfo response : {}", DefaultObjectMapper.INSTANCE.getObjectMapper().writeValueAsString(response));
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/bank-info/{routingNumber}")
    @Timed
    public Response removeBankInfo(@PathParam("routingNumber") final Long routingNumber) {
        log.info("Entry:: removeBankInfo for : {}", routingNumber);
        routingNumberService.removeBankInfoByRoutingNum(routingNumber);
        return Response.ok().build();
    }
}
