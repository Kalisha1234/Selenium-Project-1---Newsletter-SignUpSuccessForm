package com.qa.tests;

import com.qa.base.BaseTest;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * NewsletterUITest - UI-level tests for Newsletter page
 * Streamlined to 5 essential test cases
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Newsletter UI Tests")
public class NewsletterUITest extends BaseTest {

    @Test
    @Order(1)
    @DisplayName("Verify page heading is present and correct")
    void testPageHeadingIsDisplayed() {
        pause(500);

        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("head")));

        Assertions.assertEquals("Stay updated!", heading.getText(),
                "Heading text should be 'Stay updated!'");
    }

    @Test
    @Order(2)
    @DisplayName("Verify email input field is present and functional")
    void testEmailInputFieldExists() {
        pause(500);

        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));

        emailField.sendKeys("test@example.com");

        Assertions.assertEquals("test@example.com", emailField.getAttribute("value"),
                "Email field should accept input");
    }

    @Test
    @Order(3)
    @DisplayName("Verify form submission with valid email")
    void testFormSubmissionWithValidEmail() {
        pause(500);

        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
        emailField.clear();
        emailField.sendKeys("valid.user@example.com");

        pause(500);

        WebElement subscribeButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("subscribe-btn"))
        );
        subscribeButton.click();

        pause(1500);

        boolean successDisplayed = driver.getPageSource().contains("Thanks for subscribing");

        Assertions.assertTrue(successDisplayed,
                "Success message should appear after valid submission");
    }

    @Test
    @Order(4)
    @DisplayName("Verify error message display for invalid email")
    void testErrorMessageForInvalidEmail() {
        pause(500);

        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
        emailField.clear();
        emailField.sendKeys("invalid-email");

        pause(500);

        WebElement subscribeButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("subscribe-btn"))
        );
        subscribeButton.click();

        pause(1000);

        WebElement errorMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("email-error"))
        );

        Assertions.assertTrue(errorMessage.isDisplayed(),
                "Error message should be displayed");
    }

    @Test
    @Order(5)
    @DisplayName("Verify subscribe button is present and clickable")
    void testSubscribeButtonExists() {
        pause(500);

        WebElement subscribeButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("subscribe-btn"))
        );

        Assertions.assertTrue(subscribeButton.isDisplayed(),
                "Subscribe button should be displayed");
    }
}