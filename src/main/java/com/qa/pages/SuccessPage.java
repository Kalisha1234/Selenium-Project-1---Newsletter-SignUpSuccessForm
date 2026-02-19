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
//public class SuccessPage {
//    WebDriverWait wait;
//    WebDriver driver;
//
//    @FindBy(css = "img[src*='icon-success']")
//    WebElement successIcon;
//
//    @FindBy(tagName = "h1")
//    WebElement successHeading;
//
//    @FindBy(tagName = "button")
//    WebElement dismissButton;
//
//    public SuccessPage(WebDriver driver) {
//        this.driver = driver;
//        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        PageFactory.initElements(driver, this);
//    }
//
//    public boolean isSuccessIconDisplayed() {
//        return successIcon.isDisplayed();
//    }
//
//    public String getSuccessHeading() {
//        return successHeading.getText();
//    }
//
//}

package com.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * SuccessPage - Page Object Model for Success page after subscription
 * Uses explicit waits and proper locator strategies (id, class)
 */
public class SuccessPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators using id and class (best practice)
    @FindBy(id = "success-icon")
    private WebElement successIconElement;

    @FindBy(id = "success-heading")
    private WebElement successHeadingElement;

    @FindBy(id = "dismiss-btn")
    private WebElement dismissButton;

    // Fallback locators if ids are not available
    @FindBy(className = "success-message")
    private WebElement successMessageElement;

    /**
     * Constructor - Initializes page elements and wait
     */
    public SuccessPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    /**
     * Wait for success page to load
     * @return true if success page loaded
     */
    public boolean isSuccessPageLoaded() {
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOf(successIconElement),
                    ExpectedConditions.visibilityOf(successHeadingElement)
            ));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if success icon is displayed
     * @return true if icon is visible
     */
    public boolean isSuccessIconDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(successIconElement));
            return successIconElement.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get success heading text with explicit wait
     * @return heading text
     */
    public String getSuccessHeadingText() {
        wait.until(ExpectedConditions.visibilityOf(successHeadingElement));
        return successHeadingElement.getText();
    }

    /**
     * Click dismiss button with explicit wait
     */
    public void clickDismissButton() {
        wait.until(ExpectedConditions.elementToBeClickable(dismissButton));
        dismissButton.click();
    }

    /**
     * Check if dismiss button is displayed
     * @return true if button is visible
     */
    public boolean isDismissButtonDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(dismissButton));
            return dismissButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get the complete success message
     * @return success message text
     */
    public String getSuccessMessage() {
        try {
            wait.until(ExpectedConditions.visibilityOf(successMessageElement));
            return successMessageElement.getText();
        } catch (Exception e) {
            // Fallback to heading if message element not found
            return getSuccessHeadingText();
        }
    }

    /**
     * Verify success page displays correct confirmation
     * @param expectedMessage - expected confirmation message
     * @return true if message matches
     */
    public boolean verifySuccessMessage(String expectedMessage) {
        String actualMessage = getSuccessHeadingText();
        return actualMessage.contains(expectedMessage);
    }
}
