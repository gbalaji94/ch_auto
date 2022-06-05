package com.clipboard.health.config;

import org.openqa.selenium.WebDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class WebDriverConfig {

    private Map<String, ThreadLocal<WebDriver>> drivers = new HashMap<>();
    private ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();

    public void setDriver(WebDriver driver, String appName) {
        if (appName.equalsIgnoreCase("web")) {
            webDriver.set(driver);
            drivers.put(appName, webDriver);
        }
    }

    @Bean
    @Primary
    @Scope("prototype")
    public WebDriver webDriver() {
        if (drivers.containsKey("web"))
            return drivers.get("web").get();
        return null;
    }

}
