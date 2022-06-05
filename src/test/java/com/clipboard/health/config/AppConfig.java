package com.clipboard.health.config;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.testng.Reporter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

@Data
@Configuration
@ComponentScan(basePackages = "com.clipboard.health")
@PropertySources({
        @PropertySource("classpath:application.properties"),
})
public class AppConfig implements ApplicationContextAware {

    @Autowired
    private Environment environment;

    @Value("${app.selenium.timeout.seconds}")
    private long seleniumTimeout;

    @Value("${app.env}")
    private String env;

    private static ApplicationContext context;

    public String getParameter(String name) {
        return Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter(name);
    }

    public String getPlatformName() {
        String platformName = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("BROWSER");
        if (platformName != null && new ArrayList<>(Arrays.asList("chrome", "firefox", "safari")).contains(platformName))
            return platformName;
        else
            throw new RuntimeException("Incorrect or no platformName specified in TestNG XML");
    }

    public String getCurrentOS() {
        return System.getProperty("os.name");
    }

    public String getUrl() {
        String url = environment.getProperty("app." + getEnv() + ".url");

        if (url == null)
            throw new RuntimeException("URL is not specified, please include "
                    + "app." + env.toLowerCase(Locale.ROOT) + ".url property in the custom.properties");
        return url;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AppConfig.context = applicationContext;
    }

    public static <T extends Object> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }

}
