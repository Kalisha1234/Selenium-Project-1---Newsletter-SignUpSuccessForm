package com.qa;

import com.qa.pages.NewsletterPage;
import com.qa.pages.SuccessPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;

public class NewsletterPOMTest {
    private WebDriver driver;
    private NewsletterPage newsletterPage;
    private static final String BASE_URL = "https://newsletter-sign-up-form-ntes.onrender.com/";

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        // Use headless mode in CI, normal mode locally
        if (System.getenv("CI") != null) {
            options.addArguments("--headless");
        }
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(BASE_URL);
        newsletterPage = new NewsletterPage(driver);
    }

    @Test
    @DisplayName("Verify newsletter page loads with correct heading")
    void testPageLoadsWithHeading() throws InterruptedException {
        Assertions.assertEquals("Wrong Heading!", newsletterPage.getHeading());
        if (System.getenv("CI") == null) Thread.sleep(5000);
    }

    @Test
    @DisplayName("Verify error message for invalid email")
    void testInvalidEmailShowsError() throws InterruptedException {
        newsletterPage.enterEmail("invalid-email");
        newsletterPage.clickSubscribe();
        Assertions.assertTrue(newsletterPage.isErrorMessageDisplayed());
        Assertions.assertEquals("Valid email required", newsletterPage.getErrorMessage());
        if (System.getenv("CI") == null) Thread.sleep(5000);
    }

    @Test
    @DisplayName("Verify successful subscription with valid email")
    void testValidEmailShowsSuccess() throws InterruptedException {
        newsletterPage.enterEmail("test@example.com");
        newsletterPage.clickSubscribe();
        SuccessPage successPage = new SuccessPage(driver);
        Assertions.assertTrue(successPage.isSuccessIconDisplayed());
        Assertions.assertTrue(successPage.getSuccessHeading().contains("Thanks for subscribing!"));
        if (System.getenv("CI") == null) Thread.sleep(5000);
    }

    @AfterEach
    void teardown() {
        // Close browser in CI, keep open locally
        if (System.getenv("CI") != null) {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
