package com.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NewsletterPage {
    WebDriver driver;

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
        PageFactory.initElements(driver, this);
    }

    public String getHeading() {
        return heading.getText();
    }

    public void enterEmail(String email) {
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    public void clickSubscribe() {
        subscribeButton.click();
    }

    public boolean isErrorMessageDisplayed() {
        return errorMessage.isDisplayed() && !errorMessage.getText().isEmpty();
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }
}
