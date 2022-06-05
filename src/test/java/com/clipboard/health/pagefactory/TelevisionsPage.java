package com.clipboard.health.pagefactory;

import com.clipboard.health.utils.SeleniumUtils;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import java.io.File;

@Log4j
public class TelevisionsPage extends SeleniumUtils {

    WebDriver driver;

    public TelevisionsPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 5), this);
    }

    @FindBy(xpath = "//div[@id='s-refinements']//span[normalize-space()='Brands']")
    WebElement brands;

    @FindBy(xpath = "//div[@id='s-refinements']//span[normalize-space()='Brands']/..//following-sibling::ul//span[text()='Samsung']")
    WebElement samsung;

    @FindBy(xpath = "//span[@aria-label='Sort by:']")
    WebElement sortBy;

    @FindBy(xpath = "//a[text()='Price: High to Low']")
    WebElement highToLowFilter;

    @FindBy(xpath = "//div[@data-cel-widget='search_result_2']")
    WebElement secondHighestProduct;

    @FindBy(xpath = "//div[@data-cel-widget='search_result_2']//h2//span")
    WebElement secondHighestProductText;

    public boolean isBrandsDisplayed() {
        _scrollToElement(brands);
        log.debug("Landed on the televisions page");
        return _isElementVisible(brands);
    }

    public boolean isSamsungOptionDisplayed() {
        log.debug("Checking if the Samsung option is available under brands");
        return _isElementVisible(samsung);
    }

    public void clickOnSamsung() throws Exception {
        _click(samsung);
        log.debug("Clicked on samsung");
    }

    public void clickOnSortBy() throws Exception {
        _click(sortBy);
        log.debug("Clicked on sort by");
    }

    public void clickOnHighToLowFilter() throws Exception {
        _click(highToLowFilter);
        log.debug("Clicked on sort by: high to low");
    }

    public String getGetSecondHighestProductText() throws Exception {
        _scrollToElement(secondHighestProduct);
        log.debug(_getText(secondHighestProductText));
        return _getText(secondHighestProductText);
    }

    public byte[] getSecondHighestProductImage() throws Exception {
        return secondHighestProduct.getScreenshotAs(OutputType.BYTES);
    }
}
