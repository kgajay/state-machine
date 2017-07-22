package com.kgajay.demo.app.service;

import com.kgajay.demo.config.AppConfiguration;
import com.kgajay.demo.pages.RoutingPageUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ajay.kg created on 22/07/17.
 */
@Service
public class RoutingNumberService {

    @Autowired
    private AppConfiguration configuration;

    @Autowired
    private WebDriverService webDriverService;

    public void searchForBankRoutingNumber(List<String> bankName) {

//        WebDriverUtils webDriverUtils = new WebDriverUtils();
        webDriverService.setDriver("firefox", configuration.getRoutingNumConfig().getUrl());
        webDriverService.getDriver().getTitle();
        RoutingPageUI routingPageUI = new RoutingPageUI(webDriverService.getDriver());
        webDriverService.tearDown();

    }


}
