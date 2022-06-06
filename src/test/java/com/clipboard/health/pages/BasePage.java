package com.clipboard.health.pages;

import com.clipboard.health.config.AppConfig;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Log4j
public class BasePage {

    private final WebDriver driver;

    AppConfig appConfig;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.appConfig = AppConfig.getBean(AppConfig.class);
    }

    /**
     * Waits for a Element by with explicit wait and returns true if found
     *
     * @param element By value of element
     * @return boolean
     */
    public boolean isElementVisible(WebElement element) {
        try {
            new FluentWait<>(driver)
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
     * Performs a click operation on a WebElement with explicit wait
     *
     * @param element WebElement
     */
    public void click(WebElement element) throws Exception {
        if (!isElementVisible(element))
            throw new RuntimeException("Unable to locate the element even after waiting for " + appConfig.getSeleniumTimeout() + " seconds");
        element.click();
    }

    /**
     * Performs a scroll within a given element by cords
     *
     * @param webElement
     * @param cords
     */
    public void scrollWithinElement(WebElement webElement, Integer cords) {
        String args = "arguments[0].scrollTop=" + cords + ";";
        ((JavascriptExecutor) this.driver).executeScript(args, webElement);
    }

    /**
     * Performs a scroll on a web page based on the web element co-ordinates
     *
     * @param element WebElement
     */
    public void scrollToElement(WebElement element) {
        Coordinates cor = ((Locatable) element).getCoordinates();
        cor.inViewPort();
    }

    /**
     * Explicitly waits for the element to be present and return the text
     *
     * @param element WebElement
     * @return String
     */
    public String getText(WebElement element) throws Exception {
        if (!isElementVisible(element))
            throw new RuntimeException("Unable to locate the element even after waiting for " + appConfig.getSeleniumTimeout() + " seconds");
        return element.getText();
    }

    /**
     * This will explicitly wait for 30 seconds till the page is loaded
     */
    public void waitForPageLoad() {
        if (driver != null) {
            ExpectedCondition<Boolean> expectation = driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
            try {
                Thread.sleep(3000);
                WebDriverWait wait = new WebDriverWait(driver, Duration.of(appConfig.getSeleniumTimeout(), ChronoUnit.SECONDS));
                wait.until(expectation);
            } catch (Throwable error) {
                throw new RuntimeException("Timeout waiting for Page Load Request to complete.");
            }
        }
    }

}
