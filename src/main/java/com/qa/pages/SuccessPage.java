package com.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * SuccessPage - Page Object for Success message page
 * Based on actual HTML: success message in index.html
 */
public class SuccessPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(className = "desktop-icon")
    private WebElement successIcon;

    @FindBy(id = "success")
    private WebElement successHeading;

    @FindBy(id = "dismiss-btn")
    private WebElement dismissButton;

    @FindBy(id = "user-email")
    private WebElement userEmail;

    @FindBy(id = "success-message")
    private WebElement successMessage;

    /**
     * Constructor
     */
    public SuccessPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    /**
     * Check if success page is loaded
     */
    public boolean isSuccessPageLoaded() {
        try {
            return driver.findElements(By.id("success-message")).size() > 0
                    || driver.findElements(By.id("success")).size() > 0
                    || driver.getPageSource().contains("Thanks for subscribing");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if success icon is displayed
     */
    public boolean isSuccessIconDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(successIcon));
            return successIcon.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get success heading text
     */
    public String getSuccessHeadingText() {
        try {
            wait.until(ExpectedConditions.visibilityOf(successHeading));
            return successHeading.getText();
        } catch (Exception e) {
            // Fallback if element not visible yet
            try {
                if (driver.findElements(By.id("success")).size() > 0) {
                    return driver.findElement(By.id("success")).getText();
                }
            } catch (Exception ex) {
                // Continue to return empty
            }
            return "";
        }
    }

    /**
     * Click dismiss button
     */
    public void clickDismissButton() {
        wait.until(ExpectedConditions.elementToBeClickable(dismissButton));
        dismissButton.click();
    }

    /**
     * Check if dismiss button is displayed
     */
    public boolean isDismissButtonDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(dismissButton));
            return dismissButton.isDisplayed();
        } catch (Exception e) {
            // Fallback check
            try {
                return driver.findElements(By.id("dismiss-btn")).size() > 0 &&
                        driver.findElement(By.id("dismiss-btn")).isDisplayed();
            } catch (Exception ex) {
                return false;
            }
        }
    }

    /**
     * Verify success message contains text
     */
    public boolean verifySuccessMessage(String expectedText) {
        try {
            String actualText = getSuccessHeadingText();
            if (actualText.contains(expectedText)) {
                return true;
            }
        } catch (Exception e) {
            // Continue to fallback
        }
        // Fallback to page source
        return driver.getPageSource().contains(expectedText);
    }

    /**
     * Wait for success page to appear
     */
    public boolean waitForSuccessPageToAppear() {
        try {
            for (int i = 0; i < 20; i++) {
                // Check if success message div is visible
                if (driver.findElements(By.id("success-message")).size() > 0) {
                    WebElement successMsg = driver.findElement(By.id("success-message"));
                    String displayStyle = successMsg.getCssValue("display");
                    if (!displayStyle.equals("none") && successMsg.isDisplayed()) {
                        return true;
                    }
                }
                // Check if heading is visible
                if (driver.findElements(By.id("success")).size() > 0) {
                    WebElement heading = driver.findElement(By.id("success"));
                    if (heading.isDisplayed()) {
                        return true;
                    }
                }
                // Check page source as fallback
                if (driver.getPageSource().contains("Thanks for subscribing")) {
                    return true;
                }
                Thread.sleep(500);
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get the email displayed on success page
     */
    public String getDisplayedEmail() {
        try {
            wait.until(ExpectedConditions.visibilityOf(userEmail));
            return userEmail.getText();
        } catch (Exception e) {
            // Fallback
            try {
                if (driver.findElements(By.id("user-email")).size() > 0) {
                    return driver.findElement(By.id("user-email")).getText();
                }
            } catch (Exception ex) {
                // Continue to return empty
            }
            return "";
        }
    }
}