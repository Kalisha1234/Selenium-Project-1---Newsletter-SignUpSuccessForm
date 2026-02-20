package com.qa.factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

/**
 * DriverFactory - Manages WebDriver instantiation and configuration
 * Implements Factory pattern for browser driver creation
 */
public class DriverFactory {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final int IMPLICIT_WAIT_SECONDS = 10;
    private static final int PAGE_LOAD_TIMEOUT_SECONDS = 30;

    /**
     * Initialize WebDriver based on browser type
     * @param browser - chrome, firefox, or edge
     * @return WebDriver instance
     */
    public static WebDriver initializeDriver(String browser) {
        WebDriver webDriver;


                WebDriverManager.chromedriver().setup();
                webDriver = new ChromeDriver(getChromeOptions());

        // Configure timeouts
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT_SECONDS));
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(PAGE_LOAD_TIMEOUT_SECONDS));
        webDriver.manage().window().maximize();

        driver.set(webDriver);
        return webDriver;
    }

    /**
     * Get ChromeOptions with CI/CD compatible settings
     */
    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();

        // CI environment detection
        if (isCI()) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
        }
        // Common options for stability
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-popup-blocking");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

        return options;
    }

    /**
     * Check if running in CI environment
     */
    private static boolean isCI() {
        return System.getenv("CI") != null ||
                System.getProperty("CI") != null;
    }
    /**
     * Get the current driver from ThreadLocal
     */
    public static WebDriver getDriver() {
        return driver.get();
    }

    /**
     * Quit driver and remove from ThreadLocal
     */
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
