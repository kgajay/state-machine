package com.kgajay.demo.app.service;

import com.google.common.base.Strings;
import com.kgajay.demo.app.db.DBDao;
import com.kgajay.demo.app.domain.BankInfo;
import com.kgajay.demo.config.AppConfiguration;
import com.kgajay.demo.pages.RoutingPageUI;
import lombok.extern.slf4j.Slf4j;
import org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Objects;

/**
 * @author ajay.kg created on 22/07/17.
 */
@Slf4j
@Service
public class RoutingNumberService {

    private static final Integer DEFAULT_LIMIT = 100;
    private static final Long DEFAULT_OFFSET = 0l;

    @Autowired
    private AppConfiguration configuration;

    @Autowired
    private WebDriverService webDriverService;

    @Autowired
    private DBDao dbDao;

    public void searchForBankRoutingNumber(List<String> bankName) {

        webDriverService.setDriver("firefox", configuration.getRoutingNumConfig().getUrl());
        webDriverService.getDriver().getTitle();
        RoutingPageUI routingPageUI = new RoutingPageUI(webDriverService.getDriver());
        webDriverService.tearDown();

    }

    public void storeBankRoutingNumber(String bankFirstChar) {
        try {
            webDriverService.setDriver("firefox", configuration.getRoutingNumConfig().getUrl());
            webDriverService.getDriver().getTitle();
            RoutingPageUI routingPageUI = new RoutingPageUI(webDriverService.getDriver());
            routingPageUI.storeAllBankInfoStartWithChar(dbDao, configuration.getRoutingNumConfig().getUrl(), bankFirstChar);
        } catch (Exception ex) {
            log.error("storeBankRoutingNumber error: {}", ex);
        } finally {
            webDriverService.tearDown();
        }
    }

    public BankInfo addBankBasicDetails(final BankInfo bankInfo) {
        try {
            Long id = dbDao.insertBankInfo(bankInfo.getRoutingNumber(), bankInfo.getName(), bankInfo.getCity(), bankInfo.getState(), bankInfo.getZipCode(), bankInfo.getAddress());
            return dbDao.getBankInfoById(id);
        } catch (Exception ex) {
            log.error("addBankBasicDetails Exception: {}", ex);
            if (ex instanceof UnableToExecuteStatementException) {
                return getBankInfoByRoutingNumber(bankInfo.getRoutingNumber());
            }
            throw new WebApplicationException(ex.getMessage(), Response.Status.NOT_FOUND);
        }
    }

    public BankInfo updateBankInfoByRoutingNumber(final String routingNumber, final BankInfo bankInfoReq) {
        BankInfo bankInfo = getBankInfoByRoutingNumber(routingNumber);
        bankInfo.merge(bankInfoReq);
        dbDao.updateBankInfo(bankInfo.getId(), bankInfo.getRoutingNumber(), bankInfo.getName(), bankInfo.getCity(), bankInfo.getState(), bankInfo.getZipCode(), bankInfo.getAddress());
        return bankInfo;
    }

    public void removeBankInfoByRoutingNum(final String routingNumber) {
        dbDao.removeBankInfoByRoutingNumber(routingNumber);
    }

    public BankInfo getBankInfoById(final Long id) {
        BankInfo bankInfo = dbDao.getBankInfoById(id);
        if (Objects.isNull(bankInfo))
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        return bankInfo;
    }

    public BankInfo getBankInfoByRoutingNumber(final String routingNumber) {
        BankInfo bankInfo = dbDao.getBankInfoByRoutingNumber(routingNumber);
        if (Objects.isNull(bankInfo))
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        return bankInfo;
    }

    public List<BankInfo> searchBankInfo(final String routingNumber, final String name, Long offset, Integer limit) {
        offset = Objects.isNull(offset) ? DEFAULT_OFFSET : offset;
        limit = Objects.isNull(limit) ? DEFAULT_LIMIT : limit;
        if (Objects.nonNull(routingNumber) && !Strings.isNullOrEmpty(name))
            return dbDao.searchBankInfo(name, routingNumber, offset, limit);
        else if (!Strings.isNullOrEmpty(name))
            return dbDao.getBankInfoByName(name, offset, limit);
        else if (Objects.nonNull(routingNumber))
            return dbDao.listBankInfoByRoutingNumber(routingNumber, offset, limit);
        else
            return dbDao.listAllBankInfo(offset, limit);
    }
}
