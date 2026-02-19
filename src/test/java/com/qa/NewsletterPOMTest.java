package com.qa;

import com.qa.pages.NewsletterPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;

import java.time.Duration;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NewsletterPOMTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private NewsletterPage newsletterPage;

    private static final String BASE_URL = "https://newsletter-sign-up-form-ntes.onrender.com/";

    @BeforeEach
    void setup() {

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        // Headless ONLY in CI
        if (System.getenv("CI") != null) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
        }

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get(BASE_URL);

        newsletterPage = new NewsletterPage(driver);
    }



    private void slowDown() {
        if (System.getenv("CI") == null) {
            try {
                Thread.sleep(1200); // Visible locally only
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void waitForElement(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }


    @Test
    @Order(1)
    void verifyHeadingIsDisplayed() {

        waitForElement(By.id("head"));
        slowDown();

        Assertions.assertEquals(
                "Stay updated!",
                newsletterPage.getHeading()
        );
    }

    @Test
    @Order(2)
    void verifySuccessfulSubscription() {

        waitForElement(By.id("email"));
        slowDown();

        newsletterPage.enterEmail("test@example.com");
        slowDown();

        newsletterPage.clickSubscribe();
        slowDown();

        waitForElement(By.tagName("h1"));

        Assertions.assertTrue(
                driver.getPageSource().contains("Thanks for subscribing!")
        );
    }

    @Test
    @Order(3)
    void verifyInvalidEmailShowsError() {

        waitForElement(By.id("email"));
        slowDown();

        newsletterPage.enterEmail("invalid-email");
        slowDown();

        newsletterPage.clickSubscribe();
        slowDown();

        waitForElement(By.className("message"));

        Assertions.assertTrue(
                newsletterPage.isErrorMessageDisplayed()
        );
    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
