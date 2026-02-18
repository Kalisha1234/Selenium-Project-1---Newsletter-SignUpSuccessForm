//package com.qa.pages;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;
//import org.openqa.selenium.support.PageFactory;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import java.time.Duration;
//
//public class NewsletterPage {
//    WebDriver driver;
//    WebDriverWait wait;
//
//    @FindBy(id = "head")
//    WebElement heading;
//
//    @FindBy(id = "email")
//    WebElement emailInput;
//
////    @FindBy(css = "button[type='submit']")
//    @FindBy(css = "button")
//    WebElement subscribeButton;
//
//    @FindBy(className = "message")
//    WebElement errorMessage;
//
//    public NewsletterPage(WebDriver driver) {
//        this.driver = driver;
//        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
//        PageFactory.initElements(driver, this);
//        // Ensure page is fully loaded by waiting for body
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
//    }
//
//    public String getHeading() {
//        wait.until(ExpectedConditions.visibilityOf(heading));
//        return heading.getText();
//    }
//
//    public void enterEmail(String email) {
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("email")));
//        wait.until(ExpectedConditions.visibilityOf(emailInput));
//        wait.until(ExpectedConditions.elementToBeClickable(emailInput));
//        emailInput.clear();
//        emailInput.sendKeys(email);
//    }
//
//    public void clickSubscribe() {
//        try {
//            // Wait extra time for page to fully render in CI
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//
//        // Wait for button to be clickable
//        wait.until(ExpectedConditions.elementToBeClickable(subscribeButton));
//
//        // Scroll into view
//        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", subscribeButton);
//
//        // Another small wait after scroll
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//
//        // Click using JavaScript
//        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", subscribeButton);
//    }
//
//    public boolean isErrorMessageDisplayed() {
//        try {
//            wait.until(ExpectedConditions.visibilityOf(errorMessage));
//            return errorMessage.isDisplayed() && !errorMessage.getText().isEmpty();
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    public String getErrorMessage() {
//        wait.until(ExpectedConditions.visibilityOf(errorMessage));
//        return errorMessage.getText();
//    }
//}

package com.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;

public class NewsletterPage {
    WebDriver driver;
    WebDriverWait wait;
    private static final Logger logger = LoggerFactory.getLogger(NewsletterPage.class);

    @FindBy(id = "head")
    WebElement heading;

    @FindBy(id = "email")
    WebElement emailInput;

    @FindBy(className = "message")
    WebElement errorMessage;

    public NewsletterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        // Wait for page to be fully loaded
        try {
            logger.info("Waiting for document ready state...");
            wait.until(webDriver ->
                    ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete")
            );
            logger.info("Document ready state: complete");

            // Log page source to see what's actually there
            String pageSource = driver.getPageSource();
            logger.info("Page source length: {}", pageSource.length());
            logger.info("Page contains 'button': {}", pageSource.contains("button"));
            logger.info("Page contains 'Subscribe': {}", pageSource.contains("Subscribe"));

        } catch (Exception e) {
            logger.error("Error waiting for page load: {}", e.getMessage());
        }

        PageFactory.initElements(driver, this);
    }

    public String getHeading() {
        wait.until(ExpectedConditions.visibilityOf(heading));
        return heading.getText();
    }

    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(emailInput));
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    public void clickSubscribe() {
        logger.info("Looking for subscribe button...");

        try {
            // Try multiple selectors
            String[] selectors = {
                    "button",
                    "button[type='submit']",
                    "input[type='submit']",
                    "[type='submit']",
                    "button:contains('Subscribe')",
                    ".btn",
                    "#submit",
                    "form button"
            };

            WebElement button = null;
            String usedSelector = null;

            for (String selector : selectors) {
                try {
                    logger.info("Trying selector: {}", selector);
                    button = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(selector)));
                    if (button != null) {
                        usedSelector = selector;
                        logger.info("Found button with selector: {}", selector);
                        break;
                    }
                } catch (Exception e) {
                    logger.warn("Selector {} failed: {}", selector, e.getMessage());
                }
            }

            if (button == null) {
                // Last resort - find by text
                logger.info("Trying XPath with text...");
                button = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//button[contains(text(), 'Subscribe')] | //input[@type='submit']")
                ));
                usedSelector = "XPath with Subscribe text";
            }

            logger.info("Button found using: {}", usedSelector);

            // Wait for visibility
            wait.until(ExpectedConditions.visibilityOf(button));
            logger.info("Button is visible");

            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", button);
            logger.info("Scrolled button into view");

            Thread.sleep(500);

            // Click
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
            logger.info("Button clicked successfully");

        } catch (Exception e) {
            logger.error("FAILED to click subscribe button: {}", e.getMessage());

            // Dump page source for debugging
            logger.error("Current page URL: {}", driver.getCurrentUrl());
            logger.error("Page title: {}", driver.getTitle());
            String source = driver.getPageSource();
            logger.error("Page source (first 500 chars): {}", source.substring(0, Math.min(500, source.length())));

            throw new RuntimeException("Could not find or click subscribe button", e);
        }
    }

    public boolean isErrorMessageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(errorMessage));
            return errorMessage.isDisplayed() && !errorMessage.getText().isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorMessage() {
        wait.until(ExpectedConditions.visibilityOf(errorMessage));
        return errorMessage.getText();
    }
}