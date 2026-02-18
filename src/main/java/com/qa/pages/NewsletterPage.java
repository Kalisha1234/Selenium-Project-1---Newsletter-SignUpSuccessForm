package com.qa.pages;

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

    @FindBy(css = "button[type='submit']")
    WebElement subscribeButton;

    @FindBy(className = "message")
    WebElement errorMessage;

    public NewsletterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    public String getHeading() {
        wait.until(ExpectedConditions.visibilityOf(heading));
        return heading.getText();
    }

    public void enterEmail(String email) {
        wait.until(ExpectedConditions.elementToBeClickable(emailInput));
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    public void clickSubscribe() {
        wait.until(ExpectedConditions.elementToBeClickable(subscribeButton));
        subscribeButton.click();
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
