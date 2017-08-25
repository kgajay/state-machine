package com.kgajay.demo.resource;

import com.kgajay.demo.BaseApplicationTest;
import com.kgajay.demo.app.db.DBDao;
import com.kgajay.demo.app.domain.Node;
import com.kgajay.demo.app.domain.StateMachine;
import com.kgajay.demo.app.domain.Transition;
import com.kgajay.demo.app.resource.StateMachineResource;
import com.kgajay.demo.app.service.StateMachineService;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

/**
 * @author ajay.kg created on 24/08/17.
 */
public class StateMachineResourceTest extends BaseApplicationTest {

    private DBDao dbDao;
    private StateMachineResource stateMachineResource;
    private StateMachineService stateMachineService;


    @BeforeClass
    public void setUp () throws Exception {

        super.setUp();
        dbDao = dbi.onDemand(DBDao.class);
        stateMachineService = new StateMachineService();
        stateMachineService.setConfiguration(configuration);
        stateMachineService.setDbDao(dbDao);
        stateMachineResource = new StateMachineResource();
        stateMachineResource.setConfiguration(configuration);
        stateMachineResource.setStateMachineService(stateMachineService);

//        when(dbDao.insertStateMachine(any(), any(), any())).thenReturn(1l);
//        when(dbDao.insertNode(any(), any(), any())).thenReturn(1l);
//        when(dbDao.insertTransition(any(), any(), any(), any())).thenReturn(1l);
    }

    @Test
    public void testAppName() {
        Response response = stateMachineResource.getAppName();
        assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
        String appName = response.getEntity().toString();
        assertEquals(appName, configuration.getAppName());
    }

    @Test
    public void createStateMachine() {
        StateMachine stateMachine = new StateMachine();
        stateMachine.setName("TEST 1");
        stateMachine.setDescription("TEST 2");
        Response response = stateMachineResource.addStateMachine(stateMachine);
        assertEquals(response.getStatus(), Response.Status.CREATED.getStatusCode());
        StateMachine actual = (StateMachine) response.getEntity();
        // verify(dbDao, atLeast(1)).insertStateMachine(any(), any(), any());

        StateMachine expected = dbDao.getStateMachineById(actual.getId());
        assertNotNull(expected);

        Response response2 = stateMachineResource.getStateMachine(actual.getId());
        assertEquals(response2.getStatus(), Response.Status.OK.getStatusCode());
        StateMachine expected2 = (StateMachine) response2.getEntity();
        assertEquals(expected, expected2);
    }

    @Test(expectedExceptions = WebApplicationException.class)
    public void shouldThrowNotFoundSM() {
        stateMachineResource.getStateMachine(23456L);
    }


    @Test
    public void createNode() {
        Node node = new Node();
        node.setName("TEST A");
        node.setDescription("TEST 2");
        Response response = stateMachineResource.addNode(node);
        assertEquals(response.getStatus(), Response.Status.CREATED.getStatusCode());
        Node actual = (Node) response.getEntity();

        Response response2 = stateMachineResource.getNode(actual.getId());
        assertEquals(response2.getStatus(), Response.Status.OK.getStatusCode());
        Node expected = (Node) response2.getEntity();
        assertEquals(expected, actual);
    }

    @Test(expectedExceptions = WebApplicationException.class)
    public void shouldThrowNotFoundNode() {
        stateMachineResource.getNode(23456L);
    }

    @Test(expectedExceptions = WebApplicationException.class)
    public void shouldThrowExceptionTransition() {
        Transition transition = new Transition();
        transition.setPath("ABDGH");
        transition.setDescription(" GOING TO ABD FROM GHJ");
        transition.setSourceNodeId(892L);
        transition.setDestinationNodeId(893L);
        Response response = null;
        try {
            stateMachineResource.addTransition(transition);
        } catch (WebApplicationException ex) {
            Response response1 = ex.getResponse();
            assertEquals(response1.getStatus(), Response.Status.NOT_ACCEPTABLE.getStatusCode());
            assertEquals(ex.getMessage(), "Node with id 892 not found");
        }

        Node node = new Node();
        node.setName("TEST AHkk");
        node.setDescription("TEST 2");
        response = stateMachineResource.addNode(node);
        assertEquals(response.getStatus(), Response.Status.CREATED.getStatusCode());
        Node actual = (Node) response.getEntity();

        transition.setSourceNodeId(actual.getId());
        stateMachineResource.addTransition(transition);

    }

    @Test
    public void createTransition() {

        Node nodeA = new Node();
        nodeA.setName("TEST AB");
        nodeA.setDescription("TEST 3");
        Response response = stateMachineResource.addNode(nodeA);
        assertEquals(response.getStatus(), Response.Status.CREATED.getStatusCode());
        nodeA = (Node) response.getEntity();

        Node nodeB = new Node();
        nodeB.setName("TEST CD");
        nodeB.setDescription("TEST 4");
        response = stateMachineResource.addNode(nodeB);
        assertEquals(response.getStatus(), Response.Status.CREATED.getStatusCode());
        nodeB = (Node) response.getEntity();


        Transition transition = new Transition();
        transition.setPath("ABDGH");
        transition.setDescription(" GOING TO ABD FROM GHJ");
        transition.setSourceNodeId(nodeA.getId());
        transition.setDestinationNodeId(nodeB.getId());
        response = stateMachineResource.addTransition(transition);
        assertEquals(response.getStatus(), Response.Status.CREATED.getStatusCode());
        Transition ts1 = (Transition) response.getEntity();
        Transition dbTs1 = dbDao.getTransition(ts1.getId());
        assertEquals(ts1, dbTs1);

    }

    @Test
    public void testExecuteMethod() {

        Node nodeA = new Node();
        nodeA.setName("NODE-A");
        nodeA.setDescription("TEST 3");
        Response response = stateMachineResource.addNode(nodeA);
        assertEquals(response.getStatus(), Response.Status.CREATED.getStatusCode());
        nodeA = (Node) response.getEntity();

        Node nodeB = new Node();
        nodeB.setName("NODE-B");
        nodeB.setDescription("TEST 4");
        response = stateMachineResource.addNode(nodeB);
        assertEquals(response.getStatus(), Response.Status.CREATED.getStatusCode());
        nodeB = (Node) response.getEntity();


        Transition transition = new Transition();
        transition.setPath("ABDGH-PATH1");
        transition.setDescription(" GOING TO ABD FROM GHJ");
        transition.setSourceNodeId(nodeA.getId());
        transition.setDestinationNodeId(nodeB.getId());
        response = stateMachineResource.addTransition(transition);
        assertEquals(response.getStatus(), Response.Status.CREATED.getStatusCode());
        Transition ts1 = (Transition) response.getEntity();
        Transition dbTs1 = dbDao.getTransition(ts1.getId());
        assertEquals(ts1, dbTs1);


        try {
            stateMachineResource.execute(nodeA.getId(), "RANDOM PATH");
        } catch (WebApplicationException ex) {
            Response response1 = ex.getResponse();
            assertEquals(response1.getStatus(), Response.Status.NOT_ACCEPTABLE.getStatusCode());
        }

        Response response1 = stateMachineResource.execute(nodeA.getId(), "ABDGH-PATH1");
        assertEquals(response1.getStatus(), Response.Status.OK.getStatusCode());
        Node actual = (Node) response1.getEntity();
        assertEquals(actual.getIsCurrent(), Boolean.TRUE);
        assertEquals(actual.getName(), nodeB.getName());
    }


    @Test
    public void testPrintMethod() {

        Node nodeA = new Node();
        nodeA.setName("NODE-A-1");
        nodeA.setDescription("TEST 3");
        Response response = stateMachineResource.addNode(nodeA);
        assertEquals(response.getStatus(), Response.Status.CREATED.getStatusCode());
        nodeA = (Node) response.getEntity();

        Node nodeB = new Node();
        nodeB.setName("NODE-B-1");
        nodeB.setDescription("TEST 4");
        response = stateMachineResource.addNode(nodeB);
        assertEquals(response.getStatus(), Response.Status.CREATED.getStatusCode());
        nodeB = (Node) response.getEntity();


        Transition transition = new Transition();
        transition.setPath("ABDGH-PATH1-A-1-B");
        transition.setDescription(" GOING TO A FROM B");
        transition.setSourceNodeId(nodeA.getId());
        transition.setDestinationNodeId(nodeB.getId());
        response = stateMachineResource.addTransition(transition);
        assertEquals(response.getStatus(), Response.Status.CREATED.getStatusCode());
        Transition ts1 = (Transition) response.getEntity();
        Transition dbTs1 = dbDao.getTransition(ts1.getId());
        assertEquals(ts1, dbTs1);

        Transition transition2 = new Transition();
        transition2.setPath("ABDGH-PATH1-B-1-A");
        transition2.setDescription(" GOING TO A FROM B");
        transition2.setSourceNodeId(nodeB.getId());
        transition2.setDestinationNodeId(nodeA.getId());
        response = stateMachineResource.addTransition(transition2);
        assertEquals(response.getStatus(), Response.Status.CREATED.getStatusCode());


        Response response1 = stateMachineResource.execute(nodeA.getId(), "ABDGH-PATH1-A-1-B");
        assertEquals(response1.getStatus(), Response.Status.OK.getStatusCode());
        Node actual = (Node) response1.getEntity();
        assertEquals(actual.getIsCurrent(), Boolean.TRUE);
        assertEquals(actual.getName(), nodeB.getName());

        StateMachine stateMachine = new StateMachine();
        stateMachine.setName("TEST SM 1");
        stateMachine.setDescription("TEST 2");
        response = stateMachineResource.addStateMachine(stateMachine);
        assertEquals(response.getStatus(), Response.Status.CREATED.getStatusCode());
        StateMachine sm = (StateMachine) response.getEntity();

        sm.setStartNodeId(nodeA.getId());
        response = stateMachineResource.updateStateMachine(sm.getId(), sm);
        sm = (StateMachine) response.getEntity();

        Response response2 = stateMachineResource.print(sm.getId());
        assertEquals(response2.getStatus(), Response.Status.OK.getStatusCode());
        List<String> path = (List<String>) response2.getEntity();
        assertNotNull(path);
        assertEquals(path.size(), 2);
        assertEquals(path.get(0), "NODE-A-1 -> NODE-B-1 ( ABDGH-PATH1-A-1-B )");
        assertEquals(path.get(1), "*NODE-B-1 -> NODE-A-1 ( ABDGH-PATH1-B-1-A )");
    }
}
