//package com.qa.pages;
//
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;
//import org.openqa.selenium.support.PageFactory;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import java.time.Duration;
//
//public class SuccessPage {
//    WebDriver driver;
//    WebDriverWait wait;
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
//        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//        PageFactory.initElements(driver, this);
//    }
//
//    public boolean isSuccessIconDisplayed() {
//        try {
//            wait.until(ExpectedConditions.visibilityOf(successIcon));
//            return successIcon.isDisplayed();
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    public String getSuccessHeading() {
//        wait.until(ExpectedConditions.visibilityOf(successHeading));
//        return successHeading.getText();
//    }
//
//    public void clickDismiss() {
//        wait.until(ExpectedConditions.elementToBeClickable(dismissButton));
//        dismissButton.click();
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

public class SuccessPage {
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(css = "img[src*='icon-success']")
    WebElement successIcon;

    @FindBy(tagName = "h1")
    WebElement successHeading;

    @FindBy(tagName = "button")
    WebElement dismissButton;

    public SuccessPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        PageFactory.initElements(driver, this);
    }

    public boolean isSuccessIconDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(successIcon));
            return successIcon.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getSuccessHeading() {
        wait.until(ExpectedConditions.visibilityOf(successHeading));
        return successHeading.getText();
    }

    public void clickDismiss() {
        wait.until(ExpectedConditions.visibilityOf(dismissButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dismissButton);
    }
}
