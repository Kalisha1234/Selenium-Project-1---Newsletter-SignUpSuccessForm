package com.qa.tests;

import com.qa.base.BaseTest;
import com.qa.pages.NewsletterPage;
import com.qa.pages.SuccessPage;
import org.junit.jupiter.api.*;

/**
 * NewsletterPOMTest - Tests for Newsletter functionality using Page Object Model
 * Streamlined to 5 essential test cases
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Newsletter Page Object Model Tests")
public class NewsletterPOMTest extends BaseTest {

    private NewsletterPage newsletterPage;
    private SuccessPage successPage;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        newsletterPage = new NewsletterPage(driver);
        successPage = new SuccessPage(driver);
    }

    @Test
    @Order(1)
    @DisplayName("TC001 - Verify page heading displays correctly")
    void testHeadingIsDisplayed() {
        pause(500);

        String heading = newsletterPage.getHeadingText();

        Assertions.assertEquals("Stay updated!", heading.trim(),
                "Heading should be 'Stay updated!'");
    }

    @Test
    @Order(2)
    @DisplayName("TC002 - Verify successful subscription with valid email")
    void testSuccessfulSubscriptionWithValidEmail() {
        pause(500);

        newsletterPage.subscribeWithEmail("test@example.com");

        pause(2000);

        boolean successDisplayed = successPage.waitForSuccessPageToAppear();

        Assertions.assertTrue(successDisplayed,
                "Success page should appear after valid subscription");
    }

    @Test
    @Order(3)
    @DisplayName("TC003 - Verify error message for invalid email format")
    void testInvalidEmailShowsError() {
        pause(500);

        newsletterPage.enterEmail("invalid-email");
        pause(500);

        newsletterPage.clickSubscribeButton();
        pause(1500);

        boolean hasError = newsletterPage.isErrorMessageDisplayed();

        Assertions.assertTrue(hasError,
                "Error should be shown for invalid email");
    }

    @Test
    @Order(4)
    @DisplayName("TC004 - Verify success message displays")
    void testSuccessMessageDisplays() {
        pause(500);

        newsletterPage.subscribeWithEmail("success@example.com");
        pause(2000);

        boolean successMessage = successPage.verifySuccessMessage("Thanks for subscribing");

        Assertions.assertTrue(successMessage,
                "Success message should display 'Thanks for subscribing'");
    }

    @Test
    @Order(5)
    @DisplayName("TC005 - Verify page elements are present")
    void testPageElementsPresent() {
        pause(500);

        Assertions.assertTrue(newsletterPage.isEmailFieldDisplayed(),
                "Email field should be present");

        Assertions.assertTrue(newsletterPage.isSubscribeButtonDisplayed(),
                "Subscribe button should be present");
    }
}
