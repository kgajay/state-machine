package com.kgajay.demo.app.domain;

import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author ajay.kg created on 23/07/17.
 */
public class BankInfoMapper implements ResultSetMapper<BankInfo> {

    @Override
    public BankInfo map(int i, ResultSet rs, StatementContext stmt) throws SQLException {
        BankInfo bankInfo = new BankInfo();
        bankInfo.setId(rs.getLong("id"));
        bankInfo.setRoutingNumber(rs.getString("routing_number"));
        bankInfo.setName(rs.getString("name"));
        bankInfo.setCity(rs.getString("city"));
        bankInfo.setState(rs.getString("state"));
        bankInfo.setAddress(rs.getString("address"));
        bankInfo.setZipCode(rs.getString("zip_code"));
        bankInfo.setDeleted(rs.getBoolean("deleted"));
        bankInfo.setCreatedAt(new DateTime(rs.getTimestamp("created_at")));
        bankInfo.setUpdatedAt(new DateTime(rs.getTimestamp("updated_at")));
        return bankInfo;
    }
}
