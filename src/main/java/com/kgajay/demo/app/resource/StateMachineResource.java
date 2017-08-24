package com.kgajay.demo.app.resource;

import com.codahale.metrics.annotation.Timed;
import com.kgajay.demo.app.domain.Node;
import com.kgajay.demo.app.domain.StateMachine;
import com.kgajay.demo.app.domain.Transition;
import com.kgajay.demo.app.service.StateMachineService;
import com.kgajay.demo.config.AppConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
@Service("StateMachineResource")
public class StateMachineResource {

    @Autowired
    private AppConfiguration configuration;

    @Autowired
    private StateMachineService stateMachineService;

    @GET
    @Path("/app/name")
    @Timed
    public Response getAppName() {
        return Response.ok(configuration.getAppName()).build();
    }


    @POST
    @Path("/state-machine")
    @Timed
    public Response addStateMachine(@Valid StateMachine stateMachine) {

        StateMachine sm = stateMachineService.addStateMachine(stateMachine);

        return Response.status(Response.Status.CREATED).entity(sm).build();
    }

    @GET
    @Path("/state-machine/{id}")
    @Timed
    public Response getStateMachine(@PathParam("id") Long id) {

        StateMachine sm = stateMachineService.getStateMachineById(id);
        return Response.ok(sm).build();
    }

    @PUT
    @Path("/state-machine/{id}")
    @Timed
    public Response updateStateMachine(@PathParam("id") Long id, StateMachine stateMachine) {

        StateMachine sm = stateMachineService.updateStateMachineById(id, stateMachine);
        return Response.ok(sm).build();
    }


    @POST
    @Path("/node")
    @Timed
    public Response addNode(@Valid Node node) {

        Node nd = stateMachineService.addNode(node);

        return Response.status(Response.Status.CREATED).entity(nd).build();
    }

    @GET
    @Path("/node/{id}")
    @Timed
    public Response getNode(@PathParam("id") Long id) {

        return Response.ok(stateMachineService.getNode(id)).build();
    }

    @PUT
    @Path("/node/{id}")
    @Timed
    public Response updateNode(@PathParam("id") Long id, Node stateMachine) {

        // TODO
        return Response.ok().build();
    }

    @POST
    @Path("/transition")
    @Timed
    public Response addTransition(@Valid Transition transition) {

        Transition ts = stateMachineService.addTransition(transition);
        return Response.status(Response.Status.CREATED).entity(ts).build();
    }

    @POST
    @Path("/state-machine/execute/{nodeId}/{path}")
    @Timed
    public Response execute(@PathParam("nodeId") Long nodeId, @PathParam("path") String path) {

        Object object = stateMachineService.execute(nodeId, path);
        return Response.ok(object).build();
    }

    @GET
    @Path("/state-machine/{id}/print")
    @Timed
    public Response print(@PathParam("id") Long id) {

        Object object = stateMachineService.print(id);
        return Response.ok(object).build();
    }
}
