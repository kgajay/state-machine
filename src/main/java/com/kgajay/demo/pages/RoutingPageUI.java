package com.kgajay.demo.pages;

import com.kgajay.demo.app.db.DBDao;
import com.kgajay.demo.app.domain.BankInfo;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Objects;

/**
 * @author ajay.kg created on 22/07/17.
 */
@Slf4j
public class RoutingPageUI {

    private WebDriver driver;
    private DoOperation doOperation;

    @FindBy(name = "routing-number-banks")
    private WebElement bankSearchInputField;

    @FindBy(xpath = ".//*[@id='container']/div[2]/div[1]/table[1]/tbody/tr[12]/td/div/div[1]/form[1]/div/div[2]/input")
    private WebElement findBankButton;

    @FindBy(name = "routing-number")
    private WebElement routingNumInputField;

    @FindBy(xpath = ".//*[@id='container']/div[2]/div[1]/table[1]/tbody/tr[12]/td/div/div[1]/form[2]/div/div[2]/input")
    private WebElement findRoutingNumber;

    public RoutingPageUI(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.doOperation = new DoOperation(driver);
    }

    public void storeAllBankInfoStartWithChar(DBDao dbDao, String baseUrl, String ch) {
        String urlToNavigate = baseUrl.concat("/banks.").concat(ch);
        this.driver.navigate().to(urlToNavigate);
        int rows = this.driver.findElements(By.xpath(".//*[@id='container']/div[3]/div[1]/table/tbody/tr")).size();
        int columns = this.driver.findElements(By.xpath(".//*[@id='container']/div[3]/div[1]/table/tbody/tr[1]/td")).size();
        for(int i=1; i<=rows; i++) {
            for(int j=1; j<=columns; j++) {
                String elemXpath = ".//*[@id='container']/div[3]/div[1]/table/tbody/tr[" + i + "]/td[" + j + "]/a";
                try {
                    WebElement elem = this.driver.findElement(By.xpath(elemXpath));
                    if (Objects.nonNull(elem)) {
                        String name = elem.getText();
                        this.doOperation.click(elem);
                        // this.doOperation.click(this.driver.findElement(By.linkText(name)));
                        int xRows = this.driver.findElements(By.xpath(".//*[@id='container']/div[3]/div[1]/table/tbody/tr")).size();
                        for (int k = 1; k <= xRows; k++) {
                            int routingNumbers = this.driver.findElements(By.xpath(".//*[@id='container']/div[3]/div[1]/table/tbody/tr[" + k + "]/td[1]/a")).size();
                            String routingNumber = null;
                            if (routingNumbers > 1) {
                                routingNumber = this.driver.findElement(
                                        By.xpath(".//*[@id='container']/div[3]/div[1]/table/tbody/tr[" + k + "]/td[1]/a[" + routingNumbers + "]")).getText();
                            } else {
                                routingNumber = this.driver.findElement(By.xpath(".//*[@id='container']/div[3]/div[1]/table/tbody/tr[" + k + "]/td[1]")).getText();
                            }
                            String address = this.driver.findElement(By.xpath(".//*[@id='container']/div[3]/div[1]/table/tbody/tr[" + k + "]/td[2]")).getText();
                            String city = this.driver.findElement(By.xpath(".//*[@id='container']/div[3]/div[1]/table/tbody/tr[" + k + "]/td[3]")).getText();
                            String state = this.driver.findElement(By.xpath(".//*[@id='container']/div[3]/div[1]/table/tbody/tr[" + k + "]/td[4]")).getText();
                            String zipCode = this.driver.findElement(By.xpath(".//*[@id='container']/div[3]/div[1]/table/tbody/tr[" + k + "]/td[5]")).getText();
                            BankInfo.checkAndUpdate(dbDao, routingNumber, name, city, state, zipCode, address);
                        }
                        this.driver.navigate().to(urlToNavigate);
                    }
                } catch (NoSuchElementException nse) {
                    log.error("NoSuchContextException for i: {}, j: {}, xPath: {}", i, j, elemXpath);
                }
            }
        }
    }
}
