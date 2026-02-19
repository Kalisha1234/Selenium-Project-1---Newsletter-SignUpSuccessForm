//package com.qa;
//
//import io.github.bonigarcia.wdm.WebDriverManager;
//import org.junit.jupiter.api.*;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import java.time.Duration;
//
//public class NewsletterUITest {
//    private WebDriver driver;
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
//
//        }
//        options.addArguments("--no-sandbox");
//        options.addArguments("--disable-dev-shm-usage");
//        driver = new ChromeDriver(options);
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
//        driver.manage().window().maximize();
//    }
//
//    @Test
//    @DisplayName("Verify newsletter page loads successfully")
//    void testNewsletterPageLoads() throws InterruptedException {
//        driver.get(BASE_URL);
//        Assertions.assertNotNull(driver.getTitle());
//        Assertions.assertTrue(driver.getCurrentUrl().contains("newsletter-sign-up-form"));
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
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * NewsletterUITest - UI-level tests for Newsletter page
 * Extends BaseTest for common setup/teardown
 * Tests basic UI elements and page functionality
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Newsletter UI Tests")
public class NewsletterUITest extends BaseTest {

    @Test
    @Order(1)
    @DisplayName("Verify newsletter page loads successfully")
    void testNewsletterPageLoads() {
        pause(500);

        // Verify page title exists
        Assertions.assertNotNull(driver.getTitle(),
                "Page title should not be null");

        // Verify URL is correct
        Assertions.assertTrue(driver.getCurrentUrl().contains("newsletter-sign-up-form"),
                "URL should contain 'newsletter-sign-up-form'");

        pause(1000);
    }

    @Test
    @Order(2)
    @DisplayName("Verify page heading is present and correct")
    void testPageHeadingIsDisplayed() {
        pause(500);

        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("head")));

        Assertions.assertTrue(heading.isDisplayed(),
                "Page heading should be displayed");

        Assertions.assertEquals("Stay updated!", heading.getText(),
                "Heading text should be 'Stay updated!'");

        pause(500);
    }

    @Test
    @Order(3)
    @DisplayName("Verify email input field is present and functional")
    void testEmailInputFieldExists() {
        pause(500);

        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));

        Assertions.assertTrue(emailField.isDisplayed(),
                "Email input field should be displayed");

        Assertions.assertTrue(emailField.isEnabled(),
                "Email input field should be enabled");

        // Test input functionality
        emailField.sendKeys("test@example.com");

        Assertions.assertEquals("test@example.com", emailField.getAttribute("value"),
                "Email field should accept input");

        pause(500);
    }

    @Test
    @Order(4)
    @DisplayName("Verify subscribe button is present and clickable")
    void testSubscribeButtonExists() {
        pause(500);

        WebElement subscribeButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("subscribe-btn"))
        );

        Assertions.assertTrue(subscribeButton.isDisplayed(),
                "Subscribe button should be displayed");

        Assertions.assertTrue(subscribeButton.isEnabled(),
                "Subscribe button should be enabled");

        pause(500);
    }

    @Test
    @Order(5)
    @DisplayName("Verify form submission with valid email")
    void testFormSubmissionWithValidEmail() {
        pause(500);

        // Locate and fill email field
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
        emailField.clear();
        emailField.sendKeys("valid.user@example.com");

        pause(500);

        // Locate and click subscribe button
        WebElement subscribeButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("subscribe-btn"))
        );
        subscribeButton.click();

        pause(1500);

        // Verify navigation to success page or success message
        boolean urlChanged = wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("success"),
                ExpectedConditions.urlMatches(".*\\?.*")
        ));

        Assertions.assertTrue(urlChanged,
                "Page should navigate or show success after valid submission");
    }

    @Test
    @Order(6)
    @DisplayName("Verify error message display for invalid email")
    void testErrorMessageForInvalidEmail() {
        pause(500);

        // Enter invalid email
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
        emailField.clear();
        emailField.sendKeys("invalid-email");

        pause(500);

        // Click subscribe
        WebElement subscribeButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("subscribe-btn"))
        );
        subscribeButton.click();

        pause(1000);

        // Verify error message appears
        WebElement errorMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.className("message"))
        );

        Assertions.assertTrue(errorMessage.isDisplayed(),
                "Error message should be displayed");

        Assertions.assertFalse(errorMessage.getText().trim().isEmpty(),
                "Error message text should not be empty");

        pause(500);
    }

    @Test
    @Order(7)
    @DisplayName("Verify page responsiveness and layout")
    void testPageLayout() {
        pause(500);

        // Verify essential elements are present
        Assertions.assertTrue(
                wait.until(ExpectedConditions.presenceOfElementLocated(By.id("head"))).isDisplayed(),
                "Heading should be present"
        );

        Assertions.assertTrue(
                wait.until(ExpectedConditions.presenceOfElementLocated(By.id("email"))).isDisplayed(),
                "Email field should be present"
        );

        Assertions.assertTrue(
                wait.until(ExpectedConditions.presenceOfElementLocated(By.id("subscribe-btn"))).isDisplayed(),
                "Subscribe button should be present"
        );

        pause(500);
    }

    @Test
    @Order(8)
    @DisplayName("Verify browser back navigation works")
    void testBrowserBackNavigation() {
        pause(500);

        String originalUrl = driver.getCurrentUrl();

        // Submit valid email
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
        emailField.sendKeys("navigation@test.com");

        WebElement subscribeButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("subscribe-btn"))
        );
        subscribeButton.click();

        pause(1500);

        // Navigate back
        driver.navigate().back();

        pause(1000);

        // Verify we're back on the newsletter page
        Assertions.assertEquals(originalUrl, driver.getCurrentUrl(),
                "Should navigate back to newsletter page");

        pause(500);
    }
}
