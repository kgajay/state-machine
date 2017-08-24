package com.kgajay.demo.app.domain;

import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author ajay.kg created on 24/08/17.
 */
public class TransitionMapper implements ResultSetMapper<Transition> {
    @Override
    public Transition map(int i, ResultSet rs, StatementContext stmtCtx) throws SQLException {
        Transition transition = new Transition();
        transition.setId(rs.getLong("id"));
        transition.setPath(rs.getString("path"));
        transition.setDescription(rs.getString("description"));
        transition.setSourceNodeId(rs.getLong("source_node_id"));
        transition.setDestinationNodeId(rs.getLong("destination_node_id"));
        transition.setDeleted(rs.getBoolean("deleted"));
        transition.setCreatedAt(new DateTime(rs.getTimestamp("created_at")));
        transition.setUpdatedAt(new DateTime(rs.getTimestamp("updated_at")));
        return transition;
    }
}
