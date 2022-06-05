package com.clipboard.health.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = {"src/test/resources/features"},
        plugin = {"json:target/cucumber/cucumber.json", "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
        glue = {"com.clipboard.health.stepdefinitions"},
        publish = true,
        tags = ""
)
public class AmazonTests extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

}
