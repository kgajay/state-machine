package com.kgajay.demo.app.db;

import com.kgajay.demo.app.domain.BankInfo;
import com.kgajay.demo.app.domain.BankInfoMapper;
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

    @SqlUpdate("insert into bank_info (routing_number, name, city, state, zip_code, address) values (:routingNumber, :name, :city, :state, :zipCode, :address)")
    @GetGeneratedKeys
    public Long insertBankInfo(@Bind("routingNumber") String routingNumber, @Bind("name") String name, @Bind("city") String city, @Bind("state") String state, @Bind("zipCode") String zipCode, @Bind("address") String address);

    @SqlUpdate("update bank_info set routing_number=:routingNumber, name=:name, city=:city, state=:state, zip_code=:zipCode, address=:address where id=:id")
    public void updateBankInfo(@Bind("id") Long id, @Bind("routingNumber") String routingNumber, @Bind("name") String name, @Bind("city") String city, @Bind("state") String state, @Bind("zipCode") String zipCode, @Bind("address") String address);

    @SqlUpdate("update bank_info set deleted=true where routing_number=:routingNumber")
    public void removeBankInfoByRoutingNumber(@Bind("routingNumber") String routingNumber);

    @SqlQuery("select * from bank_info where id=:id")
    @Mapper(BankInfoMapper.class)
    public BankInfo getBankInfoById(@Bind("id") Long id);

    @SqlQuery("select * from bank_info where routing_number=:routingNumber and deleted=false")
    @Mapper(BankInfoMapper.class)
    public BankInfo getBankInfoByRoutingNumber(@Bind("routingNumber") String routingNumber);

    @SqlQuery("select * from bank_info where name like concat(:name,'%') and deleted=false limit :limit offset :offset")
    @Mapper(BankInfoMapper.class)
    public List<BankInfo> getBankInfoByName(@Bind("name") String name, @Bind("offset") Long offset, @Bind("limit") Integer limit);

    @SqlQuery("select * from bank_info where deleted=false limit :limit offset :offset")
    @Mapper(BankInfoMapper.class)
    public List<BankInfo> listAllBankInfo(@Bind("offset") Long offset, @Bind("limit") Integer limit);

    @SqlQuery("select * from bank_info where name like concat(:name,'%') and routing_number=:routingNumber and deleted=false limit :limit offset :offset")
    @Mapper(BankInfoMapper.class)
    public List<BankInfo> searchBankInfo(@Bind("name") String name, @Bind("routingNumber") String routingNumber, @Bind("offset") Long offset, @Bind("limit") Integer limit);

}
