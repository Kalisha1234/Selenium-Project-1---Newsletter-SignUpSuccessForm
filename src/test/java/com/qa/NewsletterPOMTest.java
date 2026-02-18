//package com.qa;
//
//import com.qa.pages.NewsletterPage;
//import com.qa.pages.SuccessPage;
//import io.github.bonigarcia.wdm.WebDriverManager;
//import org.junit.jupiter.api.*;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import java.time.Duration;
//
//public class NewsletterPOMTest {
//    private WebDriver driver;
//    private NewsletterPage newsletterPage;
//    private static final String BASE_URL = "https://newsletter-sign-up-form-ntes.onrender.com/";
//    private static final Logger logger = LoggerFactory.getLogger(NewsletterPOMTest.class);
//    private static final boolean IS_CI = System.getenv("CI") != null;
//
//    @BeforeAll
//    static void setupClass() {
//        WebDriverManager.chromedriver().setup();
//    }
//
//@BeforeEach
//void setup() {
//    logger.info("Setting up ChromeDriver...");
//    ChromeOptions options = new ChromeOptions();
//    if (IS_CI) {
//        options.addArguments("--headless=new");
//        options.addArguments("--window-size=1920,1080");
//        options.addArguments("--disable-gpu");
//        options.addArguments("--disable-extensions");
//        options.addArguments("--disable-dev-shm-usage");
//        logger.info("Running in headless mode (CI environment detected)");
//    }
//    options.addArguments("--no-sandbox");
//
//    driver = new ChromeDriver(options);
//    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
//    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
//    driver.manage().window().maximize();
//
//    logger.info("Navigating to: {}", BASE_URL);
//    driver.get(BASE_URL);
//
//    // Critical: Wait for page to fully load
//    try {
//        Thread.sleep(2000);
//    } catch (InterruptedException e) {
//        Thread.currentThread().interrupt();
//    }
//
//    newsletterPage = new NewsletterPage(driver);
//    logger.info("Page loaded. Title: {}", driver.getTitle());
//}
//
//    @Test
//    @DisplayName("Verify newsletter page loads with correct heading")
//    void testPageLoadsWithHeading() {
//        logger.info("[TEST] testPageLoadsWithHeading - START");
//        String heading = newsletterPage.getHeading();
//        logger.info("Actual heading text: '{}'", heading);
//        Assertions.assertEquals("Stay updated!", heading,
//                "Expected heading 'Stay updated!' but got: '" + heading + "'");
//        logger.info("[TEST] testPageLoadsWithHeading - PASSED");
//        pauseIfLocal(); // keep browser open for 3s locally so you can see the result
//    }
//
//    @Test
//    @DisplayName("Verify error message for invalid email")
//    void testInvalidEmailShowsError() {
//        logger.info("[TEST] testInvalidEmailShowsError - START");
//        newsletterPage.enterEmail("invalid-email");
//        logger.info("Entered invalid email: 'invalid-email'");
//        newsletterPage.clickSubscribe();
//        logger.info("Clicked Subscribe button");
//        boolean errorDisplayed = newsletterPage.isErrorMessageDisplayed();
//        logger.info("Error message displayed: {}", errorDisplayed);
//        Assertions.assertTrue(errorDisplayed,
//                "Expected error message to be displayed after submitting invalid email, but it was not.");
//        String errorMsg = newsletterPage.getErrorMessage();
//        logger.info("Error message text: '{}'", errorMsg);
//        Assertions.assertEquals("Valid email required", errorMsg,
//                "Expected 'Valid email required' but got: '" + errorMsg + "'");
//        logger.info("[TEST] testInvalidEmailShowsError - PASSED");
//        pauseIfLocal(); // keep browser open for 3s locally so you can see the result
//    }
//
//    @Test
//    @DisplayName("Verify successful subscription with valid email")
//    void testValidEmailShowsSuccess() {
//        logger.info("[TEST] testValidEmailShowsSuccess - START");
//        newsletterPage.enterEmail("test@example.com");
//        logger.info("Entered valid email: 'test@example.com'");
//        newsletterPage.clickSubscribe();
//        logger.info("Clicked Subscribe button");
//        SuccessPage successPage = new SuccessPage(driver);
//        boolean iconDisplayed = successPage.isSuccessIconDisplayed();
//        logger.info("Success icon displayed: {}", iconDisplayed);
//        Assertions.assertTrue(iconDisplayed,
//                "Expected success icon to be displayed but it was not.");
//        String successHeading = successPage.getSuccessHeading();
//        logger.info("Success heading text: '{}'", successHeading);
//        Assertions.assertTrue(successHeading.contains("Thanks for subscribing!"),
//                "Expected heading to contain 'Thanks for subscribing!' but got: '" + successHeading + "'");
//        logger.info("[TEST] testValidEmailShowsSuccess - PASSED");
//        pauseIfLocal(); // keep browser open for 3s locally so you can see the result
//    }
//
//    @AfterEach
//    void teardown() {
//        if (driver != null) {
//            driver.quit();
//            logger.info("WebDriver closed.");
//        }
//    }
//
//    /**
//     * Pauses for 3 seconds ONLY when running locally (not in CI).
//     * This lets you see the browser state after each test action.
//     */
//    private void pauseIfLocal() {
//        if (!IS_CI) {
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//        }
//    }
//}


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
    private static final boolean IS_CI = System.getenv("CI") != null;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        logger.info("Setting up ChromeDriver...");
        logger.info("IS_CI: {}", IS_CI);

        ChromeOptions options = new ChromeOptions();
        if (IS_CI) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--disable-gpu");
            options.addArguments("--disable-software-rasterizer");
            logger.info("Running in headless mode (CI environment detected)");
        }
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(120));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0)); // Turn off implicit waits
        driver.manage().window().maximize();

        logger.info("Navigating to: {}", BASE_URL);
        driver.get(BASE_URL);
        logger.info("Navigation complete");

        // Wait for page to be interactive and JavaScript to execute
        org.openqa.selenium.support.ui.WebDriverWait pageWait =
                new org.openqa.selenium.support.ui.WebDriverWait(driver, Duration.ofSeconds(60));

        // Wait for document.readyState = complete
        pageWait.until(webDriver ->
                ((org.openqa.selenium.JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState").equals("complete")
        );
        logger.info("Document ready state: complete");

        // Wait longer in CI for JavaScript to execute and render DOM
        if (IS_CI) {
            logger.info("CI detected - waiting for JavaScript to render page...");
            try {
                Thread.sleep(15000); // 15 seconds for render.com cold start + JS execution
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            logger.info("Wait complete");
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        logger.info("Current URL: {}", driver.getCurrentUrl());
        logger.info("Page title: {}", driver.getTitle());

        // Log if button exists in DOM
        try {
            long buttonCount = (Long) ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("return document.querySelectorAll('button').length");
            logger.info("Number of buttons found in DOM: {}", buttonCount);
        } catch (Exception e) {
            logger.warn("Could not count buttons: {}", e.getMessage());
        }

        newsletterPage = new NewsletterPage(driver);
        logger.info("NewsletterPage initialized");
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
        pauseIfLocal();
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
        pauseIfLocal();
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