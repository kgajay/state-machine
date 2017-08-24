package com.kgajay.demo.app.service;

import com.kgajay.demo.app.db.DBDao;
import com.kgajay.demo.app.domain.Node;
import com.kgajay.demo.app.domain.StateMachine;
import com.kgajay.demo.app.domain.Transition;
import com.kgajay.demo.config.AppConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author ajay.kg created on 24/08/17.
 */

@Slf4j
@Service
public class StateMachineService {


    @Autowired
    private AppConfiguration configuration;

    @Autowired
    private DBDao dbDao;

    public StateMachine addStateMachine(final StateMachine stateMachine) {
        Long id = dbDao.insertStateMachine(stateMachine.getName(), stateMachine.getDescription(), stateMachine.getStartNodeId());
        return dbDao.getStateMachineById(id);
    }

    public StateMachine getStateMachineById(final Long id) {
        StateMachine sm = dbDao.getStateMachineById(id);
        if (Objects.isNull(sm))
            throw new WebApplicationException("State Machine not found", Response.Status.NOT_FOUND);
        return sm;
    }

    public StateMachine updateStateMachineById(final Long id, final StateMachine stateMachine) {
        StateMachine sm = dbDao.getStateMachineById(id);
        if (Objects.isNull(sm))
            throw new WebApplicationException("State Machine not found", Response.Status.NOT_FOUND);

        if (stateMachine.getName() != null)
            sm.setName(stateMachine.getName());
        if (stateMachine.getDescription() != null)
            sm.setDescription(stateMachine.getDescription());
        if (stateMachine.getStartNodeId() != null)
            sm.setStartNodeId(stateMachine.getStartNodeId());

        dbDao.updateStateMachine(id, sm.getName(), sm.getDescription(), sm.getStartNodeId());

        return sm;
    }


    public Node addNode(final Node node) {
        Long id = dbDao.insertNode(node.getName(), node.getDescription(), node.getIsCurrent());
        return dbDao.getNode(id);
    }

    public Node getNode(final Long id) {
        Node sm = dbDao.getNode(id);
        if (Objects.isNull(sm))
            throw new WebApplicationException("State Machine not found", Response.Status.NOT_FOUND);
        return sm;
    }

    public Transition addTransition(final Transition transition) {
        Node n1 = dbDao.getNode(transition.getSourceNodeId());
        if (Objects.isNull(n1))
            throw new WebApplicationException("Node with id " + transition.getSourceNodeId() + " not found", Response.Status.NOT_ACCEPTABLE);

        Node n2 = dbDao.getNode(transition.getDestinationNodeId());
        if (Objects.isNull(n2))
            throw new WebApplicationException("Node with id " + transition.getDestinationNodeId() + " not found", Response.Status.NOT_ACCEPTABLE);


        Long id = dbDao.insertTransition(transition.getPath(), transition.getDescription(), transition.getSourceNodeId(), transition.getDestinationNodeId());
        return dbDao.getTransition(id);
    }

    public Object execute(final Long nodeId, final String path) {

        Transition transition = dbDao.findTransition(nodeId, path);
        if (Objects.isNull(transition))
            throw new WebApplicationException("Can not move from nodeId: " + nodeId + ", via path: " + path, Response.Status.NOT_ACCEPTABLE);

        Node node = dbDao.getNode(nodeId);
        if (Objects.isNull(node))
            throw new WebApplicationException("Node with id " + transition.getSourceNodeId() + " not found", Response.Status.NOT_ACCEPTABLE);

        node.setIsCurrent(Boolean.FALSE);
        dbDao.setNodeIsCurrent(node.getId(), Boolean.FALSE);

        Node destination = dbDao.getNode(transition.getDestinationNodeId());
        destination.setIsCurrent(Boolean.TRUE);
        dbDao.setNodeIsCurrent(destination.getId(), Boolean.TRUE);

        return destination;
    }


    public Object print(final Long id) {

        StateMachine sm = dbDao.getStateMachineById(id);
        if (Objects.isNull(sm))
            throw new WebApplicationException("State Machine not found", Response.Status.NOT_FOUND);

        List<String> path = new ArrayList<>();

        Set<Long> visited = new HashSet<>();
        if (sm.getStartNodeId() != null) {
            dfs(sm.getStartNodeId(), visited, path);
        } else {
            throw new WebApplicationException("Start node id is not set", Response.Status.NOT_ACCEPTABLE);
        }

        return path;
    }

    public void dfs(Long nodeId, Set<Long> visited, List<String> path) {

        if (!visited.contains(nodeId)) {
            visited.add(nodeId);
            List<Transition> transitions = dbDao.findAllTransitions(nodeId);
            if (Objects.nonNull(transitions) && !transitions.isEmpty()) {
                for(Transition transition : transitions) {
                    Node source = dbDao.getNode(transition.getSourceNodeId());
                    Node destination = dbDao.getNode(transition.getDestinationNodeId());
                    if (source.getIsCurrent() == Boolean.FALSE)
                        path.add("" + source.getName() + " -> " + destination.getName() + " ( " + transition.getPath() + " )");
                    else
                        path.add("*" + source.getName() + " -> " + destination.getName() + " ( " + transition.getPath() + " )");

                    dfs(destination.getId(), visited, path);
                }
            }
        }

    }
}