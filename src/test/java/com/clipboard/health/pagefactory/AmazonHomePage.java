package com.clipboard.health.pagefactory;

import com.clipboard.health.utils.SeleniumUtils;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
@Log4j
public class AmazonHomePage extends SeleniumUtils {

    WebDriver driver;

    public AmazonHomePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 5), this);
    }

    @FindAll({
            @FindBy(id = "nav-logo-sprites"),
            @FindBy(xpath = "//a[@aria-label='Amazon']")
    })
    WebElement amazonLogo;

    @FindAll({
            @FindBy(id = "//i[@class='hm-icon nav-sprite']"),
            @FindBy(css = ".hm-icon-label")
    })
    WebElement hamburgerIcon;

    @FindBy(css = ".hmenu.hmenu-visible")
    WebElement menuSection;

    @FindBy(xpath = "//a[@class='hmenu-item']/div[normalize-space()='TV, Appliances, Electronics']")
    WebElement tvAppliancesElectronicsLink;

    @FindBy(xpath = "(//div[contains(text(),'main menu')])[8]")
    WebElement subMenu;

    @FindBy(xpath = "//ul//li/a[normalize-space()='Televisions']")
    WebElement televisions;

    public boolean isAmazonLogoDisplayed() {
        log.debug("Landed on the Amazon Home page");
        return _isElementVisible(amazonLogo);
    }

    public void clickOnMenu() throws Exception {
        _click(hamburgerIcon);
        log.debug("Clicked on the hamburger icon");
    }

    public void clickOnElectronicsSection() throws Exception {
        _scrollWithinElement(menuSection, 300);
        _click(tvAppliancesElectronicsLink);
        log.debug("Clicked on TV, Appliances and Electronics section");
    }

    public boolean isSubMenuDisplayed(){
        return _isElementVisible(subMenu);
    }

    public void clickOnTelevisions() throws Exception {
        _click(televisions);
        log.debug("Clicked on Televisions section");
    }
}
