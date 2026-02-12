package com.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SuccessPage {
    WebDriver driver;

    @FindBy(css = "img[src*='icon-success']")
    WebElement successIcon;

    @FindBy(tagName = "h1")
    WebElement successHeading;

    @FindBy(tagName = "button")
    WebElement dismissButton;

    public SuccessPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isSuccessIconDisplayed() {
        return successIcon.isDisplayed();
    }

    public String getSuccessHeading() {
        return successHeading.getText();
    }

    public void clickDismiss() {
        dismissButton.click();
    }
}
