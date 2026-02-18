
package com.qa;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;

public class NewsletterUITest {
    private WebDriver driver;
    private static final String BASE_URL = "https://newsletter-sign-up-form-ntes.onrender.com/";
    private static final Logger logger = LoggerFactory.getLogger(NewsletterUITest.class);
    private static final boolean IS_CI = System.getenv("CI") != null;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        logger.info("Setting up ChromeDriver...");
        ChromeOptions options = new ChromeOptions();
        if (IS_CI) {
            options.addArguments("--headless");
            options.addArguments("--window-size=1920,1080");
            logger.info("Running in headless mode (CI environment detected)");
        }
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().window().maximize();
    }

    @Test
    @DisplayName("Verify newsletter page loads successfully")
    void testNewsletterPageLoads() {
        logger.info("[TEST] testNewsletterPageLoads - START");
        logger.info("Navigating to: {}", BASE_URL);
        driver.get(BASE_URL);
        String title = driver.getTitle();
        String currentUrl = driver.getCurrentUrl();
        logger.info("Page title: '{}'", title);
        logger.info("Current URL: '{}'", currentUrl);
        Assertions.assertNotNull(title,
                "Expected page title to be non-null, but it was null.");
        Assertions.assertTrue(currentUrl.contains("newsletter-sign-up-form"),
                "Expected URL to contain 'newsletter-sign-up-form' but got: '" + currentUrl + "'");
        logger.info("[TEST] testNewsletterPageLoads - PASSED");
        pauseIfLocal();
    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
            logger.info("WebDriver closed.");
        }
    }

    private void pauseIfLocal() {
        if (!IS_CI) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
