package com.clipboard.health.drivers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

@Component
public class WebDriverBuilder {

    public WebDriver setupDriver(String platformName) throws MalformedURLException {
        WebDriver driver;
        switch (platformName.toLowerCase()) {
            case "firefox": {
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                WebDriverManager.firefoxdriver().setup();
                driver = new RemoteWebDriver(new URL("http://localhost:4444"), firefoxOptions);
                break;
            }
            case "safari": {
                driver = new SafariDriver();
                break;
            }
            case "chrome":
            default: {
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                driver = new RemoteWebDriver(new URL("http://localhost:4444"), chromeOptions);
            }
        }
        driver.manage().window().maximize();
        return driver;
    }

}
