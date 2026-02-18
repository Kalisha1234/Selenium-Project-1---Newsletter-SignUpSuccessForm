package com.qa;

import com.qa.pages.NewsletterPage;
import com.qa.pages.SuccessPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;

public class NewsletterPOMTest {
    private WebDriver driver;
    private NewsletterPage newsletterPage;
    private static final String BASE_URL = "https://newsletter-sign-up-form-ntes.onrender.com/";
    private static final Logger logger = LoggerFactory.getLogger(NewsletterPOMTest.class);

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        logger.info("Setting up ChromeDriver...");
        ChromeOptions options = new ChromeOptions();
        if (System.getenv("CI") != null) {
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
        logger.info("Navigating to: {}", BASE_URL);
        driver.get(BASE_URL);
        newsletterPage = new NewsletterPage(driver);
        logger.info("Page loaded. Title: {}", driver.getTitle());
    }

    @Test
    @DisplayName("Verify newsletter page loads with correct heading")
    void testPageLoadsWithHeading() {
        logger.info("[TEST] testPageLoadsWithHeading - START");
        String heading = newsletterPage.getHeading();
        logger.info("Actual heading text: '{}'", heading);
        Assertions.assertEquals("Stay updated!", heading,
                "Expected heading 'Stay updated!' but got: '" + heading + "'");
        logger.info("[TEST] testPageLoadsWithHeading - PASSED");
    }

    @Test
    @DisplayName("Verify error message for invalid email")
    void testInvalidEmailShowsError() {
        logger.info("[TEST] testInvalidEmailShowsError - START");
        newsletterPage.enterEmail("invalid-email");
        logger.info("Entered invalid email: 'invalid-email'");
        newsletterPage.clickSubscribe();
        logger.info("Clicked Subscribe button");
        boolean errorDisplayed = newsletterPage.isErrorMessageDisplayed();
        logger.info("Error message displayed: {}", errorDisplayed);
        Assertions.assertTrue(errorDisplayed,
                "Expected error message to be displayed after submitting invalid email, but it was not.");
        String errorMsg = newsletterPage.getErrorMessage();
        logger.info("Error message text: '{}'", errorMsg);
        Assertions.assertEquals("Valid email required", errorMsg,
                "Expected 'Valid email required' but got: '" + errorMsg + "'");
        logger.info("[TEST] testInvalidEmailShowsError - PASSED");
    }

    @Test
    @DisplayName("Verify successful subscription with valid email")
    void testValidEmailShowsSuccess() {
        logger.info("[TEST] testValidEmailShowsSuccess - START");
        newsletterPage.enterEmail("test@example.com");
        logger.info("Entered valid email: 'test@example.com'");
        newsletterPage.clickSubscribe();
        logger.info("Clicked Subscribe button");
        SuccessPage successPage = new SuccessPage(driver);
        boolean iconDisplayed = successPage.isSuccessIconDisplayed();
        logger.info("Success icon displayed: {}", iconDisplayed);
        Assertions.assertTrue(iconDisplayed,
                "Expected success icon to be displayed but it was not.");
        String successHeading = successPage.getSuccessHeading();
        logger.info("Success heading text: '{}'", successHeading);
        Assertions.assertTrue(successHeading.contains("Thanks for subscribing!"),
                "Expected heading to contain 'Thanks for subscribing!' but got: '" + successHeading + "'");
        logger.info("[TEST] testValidEmailShowsSuccess - PASSED");
    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
            logger.info("WebDriver closed.");
        }
    }
}
