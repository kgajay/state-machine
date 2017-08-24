package com.kgajay.demo.app.db;

import com.kgajay.demo.app.domain.Node;
import com.kgajay.demo.app.domain.NodeMapper;
import com.kgajay.demo.app.domain.StateMachine;
import com.kgajay.demo.app.domain.StateMachineMapper;
import com.kgajay.demo.app.domain.Transition;
import com.kgajay.demo.app.domain.TransitionMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;

import java.util.List;

/**
 * @author ajay.kg created on 22/07/17.
 */
@UseStringTemplate3StatementLocator
public interface DBDao {

    @SqlQuery("select 1")
    Integer healthCheck();

    @SqlUpdate("insert into state_machine (name, description, start_node_id) values (:name, :description, :startNodeId)")
    @GetGeneratedKeys
    public Long insertStateMachine(@Bind("name") String name, @Bind("description") String description, @Bind("startNodeId") Long startNodeId);

    @SqlQuery("select * from state_machine where id=:id")
    @Mapper(StateMachineMapper.class)
    public StateMachine getStateMachineById(@Bind("id") Long id);

    @SqlUpdate("update state_machine set name=:name, description=:description, start_node_id=:startNodeId where id=:id")
    @GetGeneratedKeys
    public Long updateStateMachine(@Bind("id") Long id, @Bind("name") String name, @Bind("description") String description, @Bind("startNodeId") Long startNodeId);


    @SqlUpdate("insert into node (name, description, is_current) values (:name, :description, :isCurrent)")
    @GetGeneratedKeys
    public Long insertNode(@Bind("name") String name, @Bind("description") String description, @Bind("isCurrent") Boolean isCurrent);

    @SqlQuery("select * from node where id=:id")
    @Mapper(NodeMapper.class)
    public Node getNode(@Bind("id") Long id);

    @SqlUpdate("update node set is_current=:isCurrent where id=:id")
    public void setNodeIsCurrent(@Bind("id") Long id, @Bind("isCurrent") Boolean isCurrent);

    @SqlUpdate("insert into transition (path, description, source_node_id, destination_node_id) values (:path, :description, :sourceNodeId, :destinationNodeId)")
    @GetGeneratedKeys
    public Long insertTransition(@Bind("path") String path, @Bind("description") String description, @Bind("sourceNodeId") Long sourceNodeId, @Bind("destinationNodeId") Long destinationNodeId);

    @SqlQuery("select * from transition where id=:id")
    @Mapper(TransitionMapper.class)
    public Transition getTransition(@Bind("id") Long id);

    @SqlQuery("select * from transition where source_node_id=:nodeId and path=:path")
    @Mapper(TransitionMapper.class)
    public Transition findTransition(@Bind("nodeId") Long nodeId, @Bind("path") String path);

    @SqlQuery("select * from transition where source_node_id=:nodeId")
    @Mapper(TransitionMapper.class)
    public List<Transition> findAllTransitions(@Bind("nodeId") Long nodeId);
}
