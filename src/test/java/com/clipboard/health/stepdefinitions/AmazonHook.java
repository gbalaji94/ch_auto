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
public class AmazonHook {

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
        Assert.assertTrue(amazonHomePage.isSubMenuDisplayed(), "Submenu for electronics isn't displayed!");
        amazonHomePage.clickOnTelevisions();
        Thread.sleep(50000);
    }

    @When("the user lands on the television section, scroll down to brands and select Samsung")
    public void theUserLandsOnTheTelevisionSectionScrollDownToBrandsAndSelectSamsung() throws Exception {
        televisionsPage = new TelevisionsPage(driver);
        Assert.assertTrue(televisionsPage.isBrandsDisplayed(), "User didn't on the televisions page!");
        Assert.assertTrue(televisionsPage.isSamsungOptionDisplayed(), "Samsung option is not present under Brands!");
        televisionsPage.clickOnSamsung();
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
