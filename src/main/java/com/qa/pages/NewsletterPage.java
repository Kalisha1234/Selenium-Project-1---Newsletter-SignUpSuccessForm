//package com.qa.pages;
//
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;
//import org.openqa.selenium.support.PageFactory;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import java.time.Duration;
//
//public class NewsletterPage {
//    WebDriverWait wait;
//    WebDriver driver;
//
//    @FindBy(id = "head")
//    WebElement heading;
//
//    @FindBy(id = "email")
//    WebElement emailInput;
//
//    @FindBy(css = "button[type='submit']")
//    WebElement subscribeButton;
//
//    @FindBy(className = "message")
//    WebElement errorMessage;
//
//    public NewsletterPage(WebDriver driver) {
//        this.driver = driver;
//        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        PageFactory.initElements(driver, this);
//    }
//
//    public String getHeading() {
//        return heading.getText();
//    }
//
//    public void enterEmail(String email) {
//        emailInput.clear();
//        emailInput.sendKeys(email);
//    }
//
//    public void clickSubscribe() {
//        subscribeButton.click();
//    }
//
//    public boolean isErrorMessageDisplayed() {
//        try {
//            return errorMessage.isDisplayed() && !errorMessage.getText().isEmpty();
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    public String getErrorMessage() {
//        return errorMessage.getText();
//    }
//}

package com.qa.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * NewsletterPage - Page Object Model for Newsletter Sign-up page
 * Uses explicit waits and proper locator strategies (id, class)
 */
public class NewsletterPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators using id and class name (best practice)
    @FindBy(id = "head")
    private WebElement headingElement;

    @FindBy(id = "email")
    private WebElement emailInputField;

    @FindBy(id = "subscribe-btn")
    private WebElement subscribeButton;

    @FindBy(className = "message")
    private WebElement errorMessageElement;

    /**
     * Constructor - Initializes page elements and wait
     */
    public NewsletterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    /**
     * Wait for page to load by checking heading visibility
     * @return true if page loaded successfully
     */
    public boolean isPageLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOf(headingElement));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get the heading text with explicit wait
     * @return heading text
     */
    public String getHeadingText() {
        wait.until(ExpectedConditions.visibilityOf(headingElement));
        return headingElement.getText();
    }

    /**
     * Enter email address with explicit wait
     * @param email - email address to enter
     */
    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(emailInputField));
        wait.until(ExpectedConditions.elementToBeClickable(emailInputField));
        emailInputField.clear();
        emailInputField.sendKeys(email);
    }

    /**
     * Click subscribe button with stability enhancements
     * Includes scroll into view and clickability wait
     */
    public void clickSubscribeButton() {
        wait.until(ExpectedConditions.visibilityOf(subscribeButton));

        // Scroll element into view (important for headless mode)
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});",
                        subscribeButton);

        // Wait for element to be clickable
        wait.until(ExpectedConditions.elementToBeClickable(subscribeButton));

        subscribeButton.click();
    }

    /**
     * Check if error message is displayed
     * @return true if error message is visible and not empty
     */
    public boolean isErrorMessageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(errorMessageElement));
            return errorMessageElement.isDisplayed()
                    && !errorMessageElement.getText().trim().isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get error message text with explicit wait
     * @return error message text
     */
    public String getErrorMessageText() {
        wait.until(ExpectedConditions.visibilityOf(errorMessageElement));
        return errorMessageElement.getText().trim();
    }

    /**
     * Check if email input field is displayed
     * @return true if email field is visible
     */
    public boolean isEmailFieldDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(emailInputField));
            return emailInputField.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if subscribe button is enabled
     * @return true if button is enabled
     */
    public boolean isSubscribeButtonEnabled() {
        wait.until(ExpectedConditions.visibilityOf(subscribeButton));
        return subscribeButton.isEnabled();
    }

    /**
     * Fluent API - Enter email and click subscribe in one flow
     * @param email - email address to subscribe with
     */
    public void subscribeWithEmail(String email) {
        enterEmail(email);
        clickSubscribeButton();
    }
}
