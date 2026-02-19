package com.qa.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class NewsletterPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(id = "head")
    private WebElement heading;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(css = "button[type='submit']")
    private WebElement subscribeButton;

    @FindBy(className = "message")
    private WebElement errorMessage;

    public NewsletterPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Wait for page heading to be visible
    public String getHeading() {
        wait.until(ExpectedConditions.visibilityOf(heading));
        return heading.getText();
    }

    // Enter email safely
    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(emailInput));
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    // Click subscribe safely (CI stable)
    public void clickSubscribe() {
        wait.until(ExpectedConditions.visibilityOf(subscribeButton));

        // Scroll into view (important for headless)
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", subscribeButton);

        wait.until(ExpectedConditions.elementToBeClickable(subscribeButton));
        subscribeButton.click();
    }

    // Check if error message is displayed
    public boolean isErrorMessageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(errorMessage));
            return errorMessage.isDisplayed()
                    && !errorMessage.getText().trim().isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    // Get error message text
    public String getErrorMessage() {
        wait.until(ExpectedConditions.visibilityOf(errorMessage));
        return errorMessage.getText().trim();
    }
}
