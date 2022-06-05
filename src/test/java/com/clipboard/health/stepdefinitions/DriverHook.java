package com.clipboard.health.stepdefinitions;

import com.clipboard.health.config.AppConfig;
import com.clipboard.health.config.WebDriverConfig;
import com.clipboard.health.drivers.WebDriverBuilder;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;


@Log4j
@RequiredArgsConstructor
@CucumberContextConfiguration
@ContextConfiguration(classes = {AppConfig.class})
public class DriverHook {

    private final WebDriverBuilder webDriverBuilder;

    private final AppConfig appConfig;

    private final WebDriverConfig webDriverConfig;

    WebDriver driver;

    @Given("Launch the browser")
    public void launchTheBrowser() {
        this.driver = webDriverBuilder.setupDriver(appConfig.getPlatformName());
        webDriverConfig.setDriver(this.driver, "web");
        driver.get(appConfig.getUrl());
    }

    @After(order = 1)
    public void addScreenshotsToReport(Scenario scenario) {
        if (scenario.isFailed()) {
            log.info(scenario.getName() + " failed!! - Capturing Screenshots ");
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenario.getName());
        }
    }

    @Then("^Close the (browser|app)$")
    public void closeTheBrowser(String appType) {
        if (driver != null)
            driver.quit();
    }

}