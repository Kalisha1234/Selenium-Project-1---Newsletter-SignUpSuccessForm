package com.qa;

import com.qa.pages.NewsletterPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;

public class NewsletterPOMTest {
    private WebDriver driver;
    private NewsletterPage newsletterPage;
    private String baseUrl;

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
        baseUrl = "file:///" + System.getProperty("user.dir").replace("\\", "/") + "/../index.html";
        driver.get(baseUrl);
        newsletterPage = new NewsletterPage(driver);
    }

    @Test
    @DisplayName("Verify newsletter page loads with correct heading")
    void testPageLoadsWithHeading() {
        Assertions.assertEquals("Stay updated!", newsletterPage.getHeading());
    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
