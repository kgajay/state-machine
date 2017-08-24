package com.kgajay.demo.resource;

import com.kgajay.demo.BaseApplicationTest;
import com.kgajay.demo.app.db.DBDao;
import com.kgajay.demo.app.resource.StateMachineResource;
import com.kgajay.demo.app.service.StateMachineService;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;

import static org.mockito.Mockito.mock;
import static org.testng.AssertJUnit.assertEquals;

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
        dbDao = mock(DBDao.class);
        stateMachineService = new StateMachineService();
        stateMachineService.setConfiguration(configuration);
        stateMachineService.setDbDao(dbDao);
        stateMachineResource = new StateMachineResource();
        stateMachineResource.setConfiguration(configuration);
        stateMachineResource.setStateMachineService(stateMachineService);
    }

    @Test
    public void testAppName() {
        Response response = stateMachineResource.getAppName();
        assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
        String appName = response.getEntity().toString();
        assertEquals(appName, configuration.getAppName());
    }

}
