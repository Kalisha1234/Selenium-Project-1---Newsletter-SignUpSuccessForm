package com.qa.base;

import com.qa.factory.DriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * BaseTest - Parent class for all test classes
 * Handles common setup and teardown operations
 */
public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    // Configuration
    protected static final String BASE_URL = "https://newsletter-sign-up-form-ntes.onrender.com/";
    protected static final int EXPLICIT_WAIT_SECONDS = 15;
    private static final String DEFAULT_BROWSER = "chrome";

    /**
     * Setup method - runs before each test
     * Initializes WebDriver and navigates to base URL
     */
    @BeforeEach
    public void setUp() {
        // Get browser from system property or use default
        String browser = System.getProperty("browser", DEFAULT_BROWSER);

        // Initialize driver using factory
        driver = DriverFactory.initializeDriver(browser);

        // Initialize explicit wait
        wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_SECONDS));

        // Navigate to base URL
        driver.get(BASE_URL);
    }

    /**
     * Teardown method - runs after each test
     * Quits the WebDriver instance
     */
    @AfterEach
    public void tearDown() {
        DriverFactory.quitDriver();
    }

    /**
     * Utility method to add visual delay (for local debugging only)
     * Does not run in CI environment
     */
    protected void pause(int milliseconds) {
        if (System.getenv("CI") == null) {
            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Thread was interrupted", e);
            }
        }
    }

    /**
     * Get current driver instance
     */
    protected WebDriver getDriver() {
        return driver;
    }

    /**
     * Get current wait instance
     */
    protected WebDriverWait getWait() {
        return wait;
    }
}
