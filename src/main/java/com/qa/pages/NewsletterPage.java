package com.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * NewsletterPage - Page Object for Newsletter Sign-up page
 * Based on actual HTML: index.html
 */
public class NewsletterPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(id = "head")
    private WebElement heading;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "subscribe-btn")
    private WebElement subscribeButton;

    @FindBy(id = "email-error")
    private WebElement errorMessage;

    @FindBy(id = "signup-form")
    private WebElement signupForm;

    /**
     * Constructor
     */
    public NewsletterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    /**
     * Check if newsletter page is loaded
     */
    public boolean isNewsletterPageLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOf(heading));
            return heading.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get heading text
     */
    public String getHeadingText() {
        wait.until(ExpectedConditions.visibilityOf(heading));
        return heading.getText();
    }

    /**
     * Enter email address
     */
    public NewsletterPage enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(emailInput));
        emailInput.clear();
        emailInput.sendKeys(email);
        return this;
    }

    /**
     * Click subscribe button
     */
    public void clickSubscribeButton() {
        wait.until(ExpectedConditions.elementToBeClickable(subscribeButton));

        // Scroll into view for headless stability
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});",
                subscribeButton
        );

        subscribeButton.click();
    }

    /**
     * Subscribe with email (fluent method)
     */
    public void subscribeWithEmail(String email) {
        enterEmail(email);
        clickSubscribeButton();
    }

    /**
     * Check if error message is displayed
     */
    public boolean isErrorMessageDisplayed() {
        try {
            Thread.sleep(1000); // Wait for error to appear

            // Check if error message has visible class or is displayed
            if (driver.findElements(By.id("email-error")).size() > 0) {
                WebElement msg = driver.findElement(By.id("email-error"));
                String displayStyle = msg.getCssValue("display");
                String visibility = msg.getCssValue("visibility");
                String opacity = msg.getCssValue("opacity");

                return !displayStyle.equals("none") &&
                        !visibility.equals("hidden") &&
                        !opacity.equals("0") &&
                        msg.isDisplayed();
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get error message text
     */
    public String getErrorMessageText() {
        try {
            if (driver.findElements(By.id("email-error")).size() > 0) {
                return driver.findElement(By.id("email-error")).getText().trim();
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Check if email field is displayed
     */
    public boolean isEmailFieldDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(emailInput));
            return emailInput.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if subscribe button is displayed
     */
    public boolean isSubscribeButtonDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(subscribeButton));
            return subscribeButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if subscribe button is enabled
     */
    public boolean isSubscribeButtonEnabled() {
        wait.until(ExpectedConditions.visibilityOf(subscribeButton));
        return subscribeButton.isEnabled();
    }

    /**
     * Clear email field
     */
    public NewsletterPage clearEmailField() {
        wait.until(ExpectedConditions.visibilityOf(emailInput));
        emailInput.clear();
        return this;
    }

    /**
     * Get email field placeholder text
     */
    public String getEmailFieldPlaceholder() {
        return emailInput.getAttribute("placeholder");
    }
}