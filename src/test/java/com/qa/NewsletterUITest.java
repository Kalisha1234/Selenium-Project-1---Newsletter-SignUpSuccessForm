package com.qa;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;

public class NewsletterUITest {
    private WebDriver driver;
    private static final String BASE_URL = "https://newsletter-sign-up-form-ntes.onrender.com/";

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    @DisplayName("Verify newsletter page loads successfully")
    void testNewsletterPageLoads() throws InterruptedException {
        driver.get(BASE_URL);
        Assertions.assertNotNull(driver.getTitle());
        Assertions.assertTrue(driver.getCurrentUrl().contains("newsletter-sign-up-form"));
        Thread.sleep(5000);
    }

    @AfterEach
    void teardown() {
        // Browser stays open - comment out driver.quit()
        // if (driver != null) {
        //     driver.quit();
        // }
    }
}
