package com.kgajay.demo.app.db;

import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;

/**
 * @author ajay.kg created on 22/07/17.
 */
@UseStringTemplate3StatementLocator
public interface DBDao {

    @SqlQuery("select 1")
    Integer healthCheck();

}
