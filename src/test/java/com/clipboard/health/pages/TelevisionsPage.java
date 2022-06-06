package com.clipboard.health.pages;

import com.clipboard.health.utils.ActionUtils;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

@Log4j
public class TelevisionsPage extends ActionUtils {

    WebDriver driver;

    public TelevisionsPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 5), this);
    }

    @FindBy(xpath = "//div[@id='s-refinements']//span[normalize-space()='Brands']")
    private WebElement brands;

    @FindBy(xpath = "//div[@id='s-refinements']//span[normalize-space()='Brands']/..//following-sibling::ul//span[text()='Samsung']")
    private WebElement samsung;

    @FindBy(xpath = "//span[@aria-label='Sort by:']")
    private WebElement sortBy;

    @FindBy(xpath = "//a[text()='Price: High to Low']")
    private WebElement highToLowFilter;

    @FindBy(xpath = "//div[@data-cel-widget='searchresult2']")
    private WebElement secondHighestProduct;

    @FindBy(xpath = "//div[@data-cel-widget='searchresult2']//h2//span")
    private WebElement secondHighestProductText;

    public boolean isBrandsDisplayed() {
        scrollToElement(brands);
        log.debug("Landed on the televisions page");
        return isElementVisible(brands);
    }

    public boolean isSamsungOptionDisplayed() {
        log.debug("Checking if the Samsung option is available under brands");
        return isElementVisible(samsung);
    }

    public void clickOnSamsung() throws Exception {
        click(samsung);
        log.debug("Clicked on samsung");
    }

    public void clickOnSortBy() throws Exception {
        click(sortBy);
        log.debug("Clicked on sort by");
    }

    public void clickOnHighToLowFilter() throws Exception {
        click(highToLowFilter);
        log.debug("Clicked on sort by: high to low");
    }

    public String getGetSecondHighestProductText() throws Exception {
        scrollToElement(secondHighestProduct);
        log.debug(getText(secondHighestProductText));
        return getText(secondHighestProductText);
    }

    public byte[] getSecondHighestProductImage() throws Exception {
        return secondHighestProduct.getScreenshotAs(OutputType.BYTES);
    }
}
