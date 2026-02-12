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
        options.addArguments("--headless");
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
    void testPageLoadsWithHeading() {
        Assertions.assertEquals("Stay updated!", newsletterPage.getHeading());
    }

    @Test
    @DisplayName("Verify error message for invalid email")
    void testInvalidEmailShowsError() {
        newsletterPage.enterEmail("invalid-email");
        newsletterPage.clickSubscribe();
        Assertions.assertTrue(newsletterPage.isErrorMessageDisplayed());
        Assertions.assertEquals("Valid email required", newsletterPage.getErrorMessage());
    }

    @Test
    @DisplayName("Verify error message for empty email")
    void testEmptyEmailShowsError() {
        newsletterPage.clickSubscribe();
        Assertions.assertTrue(newsletterPage.isErrorMessageDisplayed());
    }

    @Test
    @DisplayName("Verify successful subscription with valid email")
    void testValidEmailShowsSuccess() {
        newsletterPage.enterEmail("test@example.com");
        newsletterPage.clickSubscribe();
        SuccessPage successPage = new SuccessPage(driver);
        Assertions.assertTrue(successPage.isSuccessIconDisplayed());
        Assertions.assertTrue(successPage.getSuccessHeading().contains("Thanks for subscribing!"));
    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
