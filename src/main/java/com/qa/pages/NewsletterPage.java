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

public class NewsletterPage {
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(id = "head")
    WebElement heading;

    @FindBy(id = "email")
    WebElement emailInput;

//    @FindBy(css = "button[type='submit']")
    @FindBy(css = "button")
    WebElement subscribeButton;

    @FindBy(className = "message")
    WebElement errorMessage;

    public NewsletterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        PageFactory.initElements(driver, this);
        // Ensure page is fully loaded by waiting for body
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
    }

    public String getHeading() {
        wait.until(ExpectedConditions.visibilityOf(heading));
        return heading.getText();
    }

    public void enterEmail(String email) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("email")));
        wait.until(ExpectedConditions.visibilityOf(emailInput));
        wait.until(ExpectedConditions.elementToBeClickable(emailInput));
        emailInput.clear();
        emailInput.sendKeys(email);
    }

//    public void clickSubscribe() {
//        // Wait for page to be interactive
//        wait.until(webDriver ->
//                ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete")
//        );
//
//        // Wait for button to be present in DOM first
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button[type='submit']")));
//
//        // Then wait for it to be visible
//        wait.until(ExpectedConditions.visibilityOf(subscribeButton));
//
//        // Wait for it to be clickable
//        wait.until(ExpectedConditions.elementToBeClickable(subscribeButton));
//
//        // Scroll button into view to ensure it's not hidden off-screen
//        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", subscribeButton);
//
//        // Small pause to ensure scroll completes
//        try {
//            Thread.sleep(200);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//
//        // Use JS click as it bypasses any overlay or interactability issue in headless CI
//        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", subscribeButton);
//    }

    public void clickSubscribe() {
        try {
            // Wait extra time for page to fully render in CI
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Wait for button to be clickable
        wait.until(ExpectedConditions.elementToBeClickable(subscribeButton));

        // Scroll into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", subscribeButton);

        // Another small wait after scroll
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Click using JavaScript
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", subscribeButton);
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
