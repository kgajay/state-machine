package com.kgajay.demo.utils;

import com.google.common.base.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;

import java.util.concurrent.TimeUnit;

/**
 * @author ajay.kg created on 26/05/16.
 */
public class WebDriverUtils {

	/**
	 * wait for the element to be displayed on web page
	 *
	 * @param element => element to wait for
	 * @param waitSec => duration to wait
	 */
	public static void waitForElement(WebElement element, int waitSec) {
		new FluentWait<WebElement>(element)
				.withTimeout(waitSec, TimeUnit.SECONDS)
				.pollingEvery(100, TimeUnit.MILLISECONDS)
				.until(new Function<WebElement, Boolean>() {
					public Boolean apply(WebElement element) {
						Boolean status = element.isDisplayed() && element.isEnabled();
						return status;
					}
				});
	}


	/**
	 * wait for the element to be displayed on web page
	 *
	 * @param element => element to wait for
	 * @param value   => value to be appeared on the element
	 * @param waitSec => duration to wait
	 */
	public static void waitForElementValue(WebElement element, final String value, final int waitSec) {
		new FluentWait<WebElement>(element)
				.withTimeout(waitSec, TimeUnit.SECONDS)
				.pollingEvery(100, TimeUnit.MILLISECONDS)
				.until(new Function<WebElement, Boolean>() {
					public Boolean apply(WebElement element) {
						String elemValue = element.getAttribute("value");
						Boolean status = element.isDisplayed() && elemValue.equalsIgnoreCase(value);
						return status;
					}
				});
	}


	/**
	 * wait for the element to be displayed on web page
	 *
	 * @param element => element to wait for
	 * @param value   => value to be appeared on the element
	 * @param waitSec => duration to wait
	 */
	public static void waitForSelectElementValue(WebElement element, final String value, final int waitSec) {
		new FluentWait<WebElement>(element)
				.withTimeout(waitSec, TimeUnit.SECONDS)
				.pollingEvery(100, TimeUnit.MILLISECONDS)
				.until(new Function<WebElement, Boolean>() {
					public Boolean apply(WebElement element) {
						try {
							String optionValue = element.findElement(By.xpath("//option[contains(text(),'" + value + "')]")).getAttribute("value");
							Select select = new Select(element);
							select.selectByValue(optionValue);
							return true;
						} catch (Throwable t) {
							return false;
						}
					}
				});
	}


	/**
	 * wait for the element to be displayed on web page
	 *
	 * @param driver => webDriver
	 * @param locator => locateElement
	 * @param sec => duration to wait
	 */
	public static void waitForWebElementToAppear(WebDriver driver, final By locator, int sec) {
		new FluentWait<WebDriver>(driver)
				.withTimeout(sec, TimeUnit.SECONDS)
				.pollingEvery(500, TimeUnit.MILLISECONDS)
				.ignoring(NoSuchElementException.class)
				.until(new Function<WebDriver, WebElement>() {
					public WebElement apply(WebDriver driver) {
						return driver.findElement(locator);
					}
				});
	}

}
