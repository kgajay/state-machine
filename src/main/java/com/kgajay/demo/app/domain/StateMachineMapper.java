package com.kgajay.demo.app.domain;

import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author ajay.kg created on 24/08/17.
 */
public class StateMachineMapper implements ResultSetMapper<StateMachine> {

    @Override
    public StateMachine map(int i, ResultSet rs, StatementContext stmt) throws SQLException {
        StateMachine sm = new StateMachine();
        sm.setId(rs.getLong("id"));
        sm.setName(rs.getString("name"));
        sm.setDescription(rs.getString("description"));
        sm.setStartNodeId(rs.getLong("start_node_id"));
        sm.setDeleted(rs.getBoolean("deleted"));
        sm.setCreatedAt(new DateTime(rs.getTimestamp("created_at")));
        sm.setUpdatedAt(new DateTime(rs.getTimestamp("updated_at")));
        return sm;
    }
}
