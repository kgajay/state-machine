package com.kgajay.demo.app.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.stereotype.Service;

/**
 * @author ajay.kg created on 22/07/17.
 */
@Slf4j
@Data
@Service
public class WebDriverService {

    private WebDriver driver;

    static String driverPath = "D:\\chromedriver\"";

    public void setDriver(String browserType, String appURL) {
        switch (browserType) {
        case "chrome":
            driver = initChromeDriver(appURL);
            break;
        case "firefox":
            driver = initFirefoxDriver(appURL);
            break;
        default:
            log.info("browser : " + browserType
                    + " is invalid, Launching Firefox as browser of choice..");
            driver = initFirefoxDriver(appURL);
        }
    }

    private WebDriver initChromeDriver(String appURL) {
        log.info("Launching google chrome with new profile..");
        System.setProperty("webdriver.chrome.driver", driverPath
                + "chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.navigate().to(appURL);
        return driver;
    }

    private WebDriver initFirefoxDriver(String appURL) {
        log.info("Launching Firefox browser..");
        WebDriver driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.navigate().to(appURL);
        return driver;
    }

    public void tearDown() {
        if (this.getDriver() != null) {
            this.getDriver().quit();
        }
    }
}
