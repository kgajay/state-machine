package com.kgajay.demo.pages;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.Select;

/**
 * @author ajay.kg created on 26/05/16.
 */
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class DoOperation {

	private WebDriver driver;

	public synchronized void type(WebElement element, String text) {
		try {
			log.info("Typing into {}", element.toString().split(":")[2].replace("]", ""));
		} catch (Exception e) {
			log.info("TYPE-INTO-TEXT-BOX element: {}, exception: {}", element.toString(), e);
		}
		highLightElement(element, "red");
		element.clear();
		element.sendKeys(text);

	}

	public synchronized void click(WebElement element) {
		try {
			log.info("Clicking {}", element.toString().split("->")[1].replace("]", ""));
		} catch (Exception e) {
			log.info("CLICK-EVENT element: {}, exception: {}", element.toString(), e);
		}
		highLightElement(element, "red");
		element.click();
	}

	public synchronized void highLightElement(WebElement element, String color) {
		try {
			if (driver instanceof JavascriptExecutor) {
				((JavascriptExecutor) driver).executeScript("arguments[0].style.border='2px solid " + color + "'", element);
			}
		} catch (Throwable t) {
			log.info("HIGHLIGHTING-AN-ELEMENT-FAILED:: elem: {}, exception: {}", element.toString(), t);
		}
	}

	public synchronized void executeScript(String script)
	{
		log.info("EXECUTE-JAVA-SCRIPT:: {}", script);
		if (driver instanceof FirefoxDriver) {
			((FirefoxDriver) driver).executeScript(script);
		} else if (driver instanceof ChromeDriver) {
			((ChromeDriver) driver).executeScript(script);
		} else if(driver instanceof SafariDriver) {
			((SafariDriver) driver).executeScript(script);
		}
	}

	public synchronized void selectInDropDown(WebElement element, String valueToSelect) {
		try {
			log.info("Selecting "+ valueToSelect + " from " + element.toString().split(":")[2].replace("]", ""));
		} catch (ArrayIndexOutOfBoundsException a) {
			log.info("SELECT-IN-DROP-DOWN element: {}, exception: {}", element.toString(), a);
		}
		highLightElement(element, "red");
		Select select = new Select(element);
		select.selectByVisibleText(valueToSelect);
	}

	public synchronized void selectInDropDownByXpath(WebElement element, String valueToSelect) {
		try {
			log.info("Selecting "+ valueToSelect + " from " + element.toString().split(":")[2].replace("]", ""));
		} catch (ArrayIndexOutOfBoundsException a) {
			log.info("SELECT-IN-DROP-DOWN element: {}, exception: {}", element.toString(), a);
		}
		String optionValue = element.findElement(By.xpath("//option[contains(text(),'"+ valueToSelect +"')]")).getAttribute("value");
		Select select = new Select(element);
		select.selectByValue(optionValue);
	}

	public synchronized void selectInDropDownUsingIndex(WebElement dropDown, int dropDownValueToSelect) {
		try {
			log.info("Selecting value from " + dropDown.toString().split(":")[2].replace("]", ""));
		} catch (ArrayIndexOutOfBoundsException a) {
			log.info("SELECT-IN-DROP-DOWN-USING-INDEX element: {}, exception: {}", dropDown.toString(), a);
		}
		highLightElement(dropDown, "red");
		Select select = new Select(dropDown);
		select.selectByIndex(dropDownValueToSelect);
	}

	public synchronized void checkCheckBox(WebElement element) {
		highLightElement(element, "red");
		boolean selected = element.isSelected();
		if(!selected) {
			try {
				log.info("Checking the checkbox of element " + element.toString().split(":")[2].replace("]", "") + " ");
			} catch (ArrayIndexOutOfBoundsException a) {
				log.info("CHECK-CHECKBOX element: {}, exception: {}", element.toString(), a);
			}
			element.click();
		} else {
			try {
				log.info("Checkbox of " + element.toString().split(":")[2].replace("]", "") + " is already checked");
			} catch (ArrayIndexOutOfBoundsException a) {
				log.info("CHECK-CHECKBOX element: {}, exception: {}", element.toString(), a);
			}
		}
	}

	public synchronized void unCheckCheckedBox(WebElement element) {
		highLightElement(element, "red");
		boolean selected = element.isSelected();
		if(selected) {
			try {
				log.info("Unchecking the chekcbox of {}", element.toString().split(":")[2].replace("]", ""));
			} catch (ArrayIndexOutOfBoundsException a) {
				log.info("CHECK-UN-CHECKBOX element: {}, exception: {}", element.toString(), a);
			}
			element.click();
		} else {
			try{
				log.info("Checkbox of " + element.toString().split(":")[2].replace("]", "") + "is already unchekced");
			} catch (ArrayIndexOutOfBoundsException a) {
				log.info("CHECK-UN-CHECKBOX element: {}, exception: {}", element.toString(), a);
			}
		}
	}

	public synchronized void clickAlertOK() {
		try {
			log.info("Clicking OK in the java scrip alert");
			driver.switchTo().alert().accept();
			log.info("Clicking alert");
		} catch(NoAlertPresentException e) {
			log.info("Java script alert is not displayed");
		}
	}
}
