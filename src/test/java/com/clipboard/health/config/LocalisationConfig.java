package com.clipboard.health.config;

import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.testng.Reporter;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.ResourceBundle;

@Getter
@Log4j
@Configuration
public class LocalisationConfig implements ApplicationContextAware {

    private static ApplicationContext context;
    private ResourceBundle bundle;

    @Autowired
    AppConfig appConfig;

    @Autowired
    private LocalisationConfig localisationConfig;

    @Bean
    @Primary
    @Scope("prototype")
    public ResourceBundle resourceBundle() throws MalformedURLException {
        if (this.bundle == null) {
            this.setResourceBundle(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("LOCALE"));
        }
        return this.bundle;
    }

    public void setResourceBundle(String locale) throws MalformedURLException {
        log.info("Setting automation locale to : " + locale);
        ClassLoader loader = new URLClassLoader(new URL[]{new File("src/test/resources/").toURI().toURL()});
        Locale.setDefault(new Locale(locale.split("_")[0], locale.split("_")[1]));
        this.bundle = ResourceBundle.getBundle("MessageBundle", Locale.getDefault(), loader);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        LocalisationConfig.context = applicationContext;
    }

    public static <T extends Object> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }

    /**
     *
     * @param key
     * @return
     * @throws MalformedURLException
     */
    public String getString(String key) throws MalformedURLException {
        return localisationConfig.resourceBundle().getString(key);
    }
}
