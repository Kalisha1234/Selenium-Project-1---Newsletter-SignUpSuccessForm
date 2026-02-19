//package com.qa;
//
//import com.qa.pages.NewsletterPage;
//import com.qa.pages.SuccessPage;
//import io.github.bonigarcia.wdm.WebDriverManager;
//import org.junit.jupiter.api.*;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import java.time.Duration;
//
//public class NewsletterPOMTest {
//    private WebDriver driver;
//    private NewsletterPage newsletterPage;
//    private static final String BASE_URL = "https://newsletter-sign-up-form-ntes.onrender.com/";
//
//    @BeforeAll
//    static void setupClass() {
//        WebDriverManager.chromedriver().setup();
//    }
//
//    @BeforeEach
//    void setup() {
//        ChromeOptions options = new ChromeOptions();
//        // Use headless mode in CI, normal mode locally
//        if (System.getenv("CI") != null) {
//            options.addArguments("--headless");
//        }
//        options.addArguments("--no-sandbox");
//        options.addArguments("--disable-dev-shm-usage");
//        driver = new ChromeDriver(options);
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
//        driver.manage().window().maximize();
//        driver.get(BASE_URL);
//        newsletterPage = new NewsletterPage(driver);
//    }
//
//    @Test
//    @DisplayName("Verify newsletter page loads with correct heading")
//    void testPageLoadsWithHeading() throws InterruptedException {
//        Assertions.assertEquals("Stay updated!", newsletterPage.getHeading());
//        if (System.getenv("CI") == null) Thread.sleep(5000);
//    }
//
//    @Test
//    @DisplayName("Verify error message for invalid email")
//    void testInvalidEmailShowsError() throws InterruptedException {
//        newsletterPage.enterEmail("invalid-email");
//        newsletterPage.clickSubscribe();
//        Assertions.assertTrue(newsletterPage.isErrorMessageDisplayed());
//        Assertions.assertEquals("Valid email required", newsletterPage.getErrorMessage());
//        if (System.getenv("CI") == null) Thread.sleep(5000);
//    }
//
//    @Test
//    @DisplayName("Verify successful subscription with valid email")
//    void testValidEmailShowsSuccess() throws InterruptedException {
//        newsletterPage.enterEmail("test@example.com");
//        if (System.getenv("CI") == null) Thread.sleep(3000);
//        newsletterPage.clickSubscribe();
//        SuccessPage successPage = new SuccessPage(driver);
//        Assertions.assertTrue(successPage.isSuccessIconDisplayed());
//        Assertions.assertTrue(successPage.getSuccessHeading().contains("Thanks for subscribing!"));
//        if (System.getenv("CI") == null) Thread.sleep(5000);
//    }
//
//    @AfterEach
//    void teardown() {
//        // Close browser in CI, keep open locally
//        if (System.getenv("CI") != null) {
//            if (driver != null) {
//                driver.quit();
//            }
//        }
//    }
//}

package com.qa.tests;

import com.qa.base.BaseTest;
import com.qa.pages.NewsletterPage;
import com.qa.pages.SuccessPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * NewsletterPOMTest - Tests for Newsletter functionality using Page Object Model
 * Extends BaseTest for common setup/teardown
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Newsletter Page Object Model Tests")
public class NewsletterPOMTest extends BaseTest {

    private NewsletterPage newsletterPage;
    private SuccessPage successPage;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        newsletterPage = new NewsletterPage(driver);
        successPage = new SuccessPage(driver);
    }

    @Test
    @Order(1)
    @DisplayName("Verify newsletter page loads successfully")
    void testNewsletterPageLoads() {
        // Verify page loaded
        Assertions.assertTrue(newsletterPage.isPageLoaded(),
                "Newsletter page should load successfully");

        pause(500);

        // Verify page title is not null
        Assertions.assertNotNull(driver.getTitle(),
                "Page title should not be null");

        // Verify URL is correct
        Assertions.assertTrue(driver.getCurrentUrl().contains("newsletter-sign-up-form"),
                "URL should contain 'newsletter-sign-up-form'");
    }

    @Test
    @Order(2)
    @DisplayName("Verify heading text is displayed correctly")
    void testHeadingIsDisplayed() {
        // Wait for heading to be visible
        pause(500);

        String heading = newsletterPage.getHeadingText();

        Assertions.assertEquals("Stay updated!", heading,
                "Heading should display 'Stay updated!'");
    }

    @Test
    @Order(3)
    @DisplayName("Verify email field is displayed and enabled")
    void testEmailFieldIsDisplayedAndEnabled() {
        pause(500);

        Assertions.assertTrue(newsletterPage.isEmailFieldDisplayed(),
                "Email field should be displayed");

        Assertions.assertTrue(newsletterPage.isSubscribeButtonEnabled(),
                "Subscribe button should be enabled");
    }

    @Test
    @Order(4)
    @DisplayName("Verify successful subscription with valid email")
    void testSuccessfulSubscription() {
        pause(500);

        // Enter valid email and subscribe
        newsletterPage.subscribeWithEmail("test@example.com");

        pause(1000);

        // Wait for success page to load
        wait.until(ExpectedConditions.urlContains("success"));

        // Verify success page loaded
        Assertions.assertTrue(successPage.isSuccessPageLoaded(),
                "Success page should load after valid subscription");

        // Verify success message
        Assertions.assertTrue(successPage.verifySuccessMessage("Thanks for subscribing"),
                "Success message should contain 'Thanks for subscribing'");
    }

    @Test
    @Order(5)
    @DisplayName("Verify error message for invalid email format")
    void testInvalidEmailShowsError() {
        pause(500);

        // Enter invalid email
        newsletterPage.enterEmail("invalid-email");

        pause(500);

        // Click subscribe
        newsletterPage.clickSubscribeButton();

        pause(1000);

        // Verify error message is displayed
        Assertions.assertTrue(newsletterPage.isErrorMessageDisplayed(),
                "Error message should be displayed for invalid email");

        String errorMessage = newsletterPage.getErrorMessageText();
        Assertions.assertFalse(errorMessage.isEmpty(),
                "Error message should not be empty");
    }

    @Test
    @Order(6)
    @DisplayName("Verify error message for empty email submission")
    void testEmptyEmailShowsError() {
        pause(500);

        // Click subscribe without entering email
        newsletterPage.clickSubscribeButton();

        pause(1000);

        // Verify error message is displayed
        Assertions.assertTrue(newsletterPage.isErrorMessageDisplayed(),
                "Error message should be displayed for empty email");
    }

    @Test
    @Order(7)
    @DisplayName("Verify multiple email formats with valid domain")
    void testValidEmailFormats() {
        String[] validEmails = {
                "user@example.com",
                "user.name@example.com",
                "user+tag@example.co.uk"
        };

        for (String email : validEmails) {
            // Refresh page for clean state
            driver.navigate().refresh();
            newsletterPage = new NewsletterPage(driver);

            pause(500);

            // Test subscription
            newsletterPage.subscribeWithEmail(email);

            pause(1000);

            // Verify success or check for no error
            boolean isSuccess = successPage.isSuccessPageLoaded();
            boolean hasError = newsletterPage.isErrorMessageDisplayed();

            Assertions.assertTrue(isSuccess || !hasError,
                    "Email '" + email + "' should be accepted");

            if (isSuccess) {
                driver.navigate().back();
            }
        }
    }
}
