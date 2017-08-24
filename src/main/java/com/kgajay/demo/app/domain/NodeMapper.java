package com.kgajay.demo.app.domain;

import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author ajay.kg created on 24/08/17.
 */
public class NodeMapper implements ResultSetMapper<Node> {
    @Override
    public Node map(int i, ResultSet rs, StatementContext stmtCtx) throws SQLException {
        Node node = new Node();
        node.setId(rs.getLong("id"));
        node.setName(rs.getString("name"));
        node.setDescription(rs.getString("description"));
        node.setIsCurrent(rs.getBoolean("is_current"));
        node.setDeleted(rs.getBoolean("deleted"));
        node.setCreatedAt(new DateTime(rs.getTimestamp("created_at")));
        node.setUpdatedAt(new DateTime(rs.getTimestamp("updated_at")));
        return node;
    }
}
