package com.clipboard.health.stepdefinitions;

import com.clipboard.health.pages.AmazonHomePage;
import com.clipboard.health.pages.TelevisionsPage;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;

@Log4j
public class AmazonStepDefinition {

    @Autowired
    WebDriver driver;

    @Autowired
    Scenario scenario;

    AmazonHomePage amazonHomePage;
    TelevisionsPage televisionsPage;

    @Then("the user should land on the amazon home page")
    public void theUserShouldLandOnTheAmazonHomePage() {
        amazonHomePage = new AmazonHomePage(driver);
        Assert.assertTrue(amazonHomePage.isAmazonLogoDisplayed(), "User didn't land on the amazon home page!");
    }

    @And("click on the hamburger icon")
    public void clickOnTheHamburgerIcon() throws Exception {
        amazonHomePage.clickOnMenu();
    }

    @And("click on TV, Appliances, Electronics section")
    public void clickOnTVAppliancesElectronicsSection() throws Exception {
        amazonHomePage.clickOnElectronicsSection();
    }

    @When("the subsection gets displayed, click on Televisions")
    public void theSubsectionGetsDisplayedClickOnTelevisions() throws Exception {
        amazonHomePage.clickOnTelevisions();
    }

    @When("the user lands on the television section, scroll down to brands and select {string}")
    public void theUserLandsOnTheTelevisionSectionScrollDownToBrandsAndSelectSamsung(String brand) throws Exception {
        televisionsPage = new TelevisionsPage(driver);
        televisionsPage.clickOnABrandByName(brand);
    }

    @And("sort the list by price in descending order")
    public void sortTheListByPriceInDescendingOrder() throws Exception {
        televisionsPage.clickOnSortBy();
        televisionsPage.clickOnHighToLowFilter();
    }

    @Then("capture the second item in the search result and print the description")
    public void captureTheSecondItemInTheSearchResultAndPrintTheDescription() throws Exception {
        log.info(televisionsPage.getGetSecondHighestProductText());
        byte[] screenshot = televisionsPage.getSecondHighestProductImage();
        scenario.attach(screenshot, "image/png", scenario.getName());
    }
}
