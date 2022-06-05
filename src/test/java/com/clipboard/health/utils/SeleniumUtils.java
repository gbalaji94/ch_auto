package com.clipboard.health.utils;

import com.clipboard.health.config.AppConfig;
import com.clipboard.health.config.LocalisationConfig;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import java.net.MalformedURLException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Log4j
public class SeleniumUtils {

    private final WebDriver driver;

    AppConfig appConfig;
    LocalisationConfig localisationConfig;

    public SeleniumUtils(WebDriver driver) {
        this.driver = driver;
        this.appConfig = AppConfig.getBean(AppConfig.class);
        this.localisationConfig = LocalisationConfig.getBean(LocalisationConfig.class);
    }

    /**
     * Waits for a Element by with explicit wait and returns true if found
     *
     * @param element By value of element
     * @return boolean
     */
    public boolean _isElementVisible(WebElement element) {
        try {
            new FluentWait<WebDriver>(driver)
                    .withTimeout(Duration.of(appConfig.getSeleniumTimeout(), ChronoUnit.SECONDS))
                    .pollingEvery(Duration.of(10, ChronoUnit.SECONDS))
                    .ignoring(NoSuchElementException.class)
                    .ignoring(StaleElementReferenceException.class)
                    .ignoring(ElementNotInteractableException.class)
                    .ignoring(ElementClickInterceptedException.class)
                    .until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * This will explicitly wait for 30 seconds till the page is loaded
     */
    public void _waitForPageLoad() {
        if (driver != null) {
            ExpectedCondition<Boolean> expectation = driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
            try {
                Thread.sleep(3000);
                WebDriverWait wait = new WebDriverWait(driver, Duration.of(appConfig.getSeleniumTimeout(), ChronoUnit.SECONDS));
                wait.until(expectation);
            } catch (Throwable error) {
                Assert.fail("Timeout waiting for Page Load Request to complete.");
            }
        }
    }

    /**
     * Performs a click operation on a WebElement with explicit wait
     *
     * @param element WebElement
     */
    public void _click(WebElement element) throws Exception {
        if (!_isElementVisible(element))
            throw new RuntimeException("Unable to locate the element even after waiting for " + appConfig.getSeleniumTimeout() + " seconds");
        element.click();
    }

    /**
     * Performs a click operation on a WebElement with explicit wait
     *
     * @param element WebElement
     */
    public boolean _clickIfDisplayed(WebElement element) throws Exception {
       return _isElementVisible(element);
    }


    /**
     * Explicitly waits for the element to be present and return the text
     *
     * @param element WebElement
     * @return String
     */
    public String _getText(WebElement element) throws Exception {
        if (!_isElementVisible(element))
            throw new RuntimeException("Unable to locate the element even after waiting for " + appConfig.getSeleniumTimeout() + " seconds");
        return element.getText();
    }

    /**
     * Performs sendKeys in an element with explicit wait
     *
     * @param element      WebElement
     * @param keysSequence Text to type-in
     */
    public void _sendKeys(WebElement element, String keysSequence) throws Exception {
        if (!_isElementVisible(element))
            throw new RuntimeException("Unable to locate the element even after waiting for " + appConfig.getSeleniumTimeout() + " seconds");
        element.clear();
        element.sendKeys(keysSequence);
    }

    /**
     * Performs clear in an element with explicit wait
     *
     * @param element      WebElement
     */

    public void _clear(WebElement element) throws Exception {
        if (!_isElementVisible(element))
            throw new RuntimeException("Unable to locate the element even after waiting for " + appConfig.getSeleniumTimeout() + " seconds");
        element.clear();
    }

    /**
     * Set a value in the dropdown
     *
     * @param element WebElement
     * @param locator xpath of the dropdown value
     */
    public void _setValue(WebElement element, String locator) throws Exception {
        _click(element);
        _click(driver.findElement(By.xpath(locator)));
    }

    /**
     * Performs a scroll on a web page based on the web element co-ordinates
     *
     * @param element WebElement
     */
    public void _scrollToElement(WebElement element) {
        Coordinates cor = ((Locatable) element).getCoordinates();
        cor.inViewPort();
    }

    /**
     * Performs a scroll within the provided web element
     *
     * @param webElement WebElement
     */
    public void _scrollWithinElement(WebElement webElement) {
        ((JavascriptExecutor) this.driver).executeScript("arguments[0].scrollTop=500;", webElement);
    }


    /**
     * Perform a browser back actions
     */
    public void _goBack() {
        this.driver.navigate().back();
    }

    /**
     * Perform a browser forward actions
     */
    public void _goForward() {
        this.driver.navigate().forward();
    }

    /**
     * Redirects to the provide URL
     *
     * @param URI URL
     */
    public void _goTo(String URI) {
        this.driver.get(URI);
        _waitForPageLoad();
    }

    /**
     * Refreshes the page and wait for 5 seconds
     */
    public void _refreshPage() {
        this.driver.navigate().refresh();
        _waitForPageLoad();
        _sleep(5);
    }

    /**
     * Introduces a Thread.sleep for specified duration. Use this only when no other wait solves your problem
     *
     * @param sleepTime in seconds
     */
    public void _sleep(int sleepTime) {
        try {
            Thread.sleep(sleepTime * 1000L);
        } catch (Throwable error) {
            error.printStackTrace();
        }
        Assert.fail("Timeout waiting for Page Load Request to complete.");
    }

    /**
     * Performs a mouseover on the given element
     *
     * @param element WebElement
     */
    public void _mouseOverAndClick(WebElement element) {
        Actions action = new Actions(this.driver);
        action.moveToElement(element).click().build().perform();
    }

    public void _scrollUpJS() {
        JavascriptExecutor jse1 = (JavascriptExecutor) this.driver;
        jse1.executeScript("window.scrollBy(0,-250)", "");
    }

    public void _scrollDownJS() {
        JavascriptExecutor jse1 = (JavascriptExecutor) this.driver;
        jse1.executeScript("window.scrollBy(0,250)", "");
    }

    public String _getString(String key) throws MalformedURLException {
        return localisationConfig.resourceBundle().getString(key);
    }

}
