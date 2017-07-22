package com.kgajay.demo.pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @author ajay.kg created on 22/07/17.
 */
@Slf4j
public class RoutingPageUI {

    private WebDriver driver;
    private DoOperation doOperation;

    @FindBy(name = "username")
    private WebElement userNameText;

    @FindBy(name = "password")
    private WebElement passwordText;

    @FindBy(xpath = ".//*[@id='login']/form/p/input")
    private WebElement loginButton;

    public RoutingPageUI(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.doOperation = new DoOperation(driver);
    }
}
