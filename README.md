# Selenium WebDriver Setup

## Prerequisites
- Java 11 or higher
- Maven 3.6+
- Chrome browser installed

## Dependencies
- **Selenium Java**: Browser automation
- **WebDriverManager**: Automatic ChromeDriver management
- **JUnit 5**: Testing framework

## Website Under Test

This project tests the Newsletter Sign-up Form application.
- **Live URL**: https://newsletter-sign-up-form-ntes.onrender.com/
- **Source Code**: https://github.com/Kalisha1234/Newsletter-sign-up-form-.git

## Setup Steps

1. Navigate to the selenium-project directory:
```bash
cd selenium-newsletter-test
```

2. Install dependencies:
```bash
mvn clean install
```

3. Run tests:
```bash
mvn test
```

## Project Structure

```
selenium-newsletter-test/
├── src/
│   ├── main/java/com/qa/pages/
│   │   ├── NewsletterPage.java    # Newsletter form page object
│   │   └── SuccessPage.java       # Success message page object
│   └── test/java/com/qa/
│       ├── NewsletterPOMTest.java # Page Object Model tests
│       └── NewsletterUITest.java  # Basic UI tests
├── .github/workflows/
│   ├── ci.yml                     # CI pipeline with email notifications
│   └── ci-with-slack.yml          # CI with email and Slack notifications
├── pom.xml
├── README.md
├── CI_SETUP.md                    # Email notification setup guide
└── SLACK_SETUP.md                 # Slack notification setup guide
```

## How It Works

- **WebDriverManager** automatically downloads and configures ChromeDriver
- No manual ChromeDriver download needed
- Tests run against live URL: https://newsletter-sign-up-form-ntes.onrender.com/
- **Page Object Model (POM)** pattern for maintainable test code
- **Page Factory** pattern for element initialization

## Test Structure

- `@BeforeAll`: Sets up ChromeDriver once
- `@BeforeEach`: Initializes browser before each test
- `@Test`: Test methods
- `@AfterEach`: Closes browser after each test

## Test Cases

1. **Page Load Test**: Verifies newsletter page loads correctly
2. **Heading Test**: Validates "Stay updated!" heading
3. **Invalid Email Test**: Checks error message for invalid email
4. **Empty Email Test**: Validates error for empty submission
5. **Valid Email Test**: Confirms successful subscription flow

## CI/CD Pipeline

GitHub Actions automatically:
- Installs dependencies
- Runs all tests on push/PR
- Generates test reports
- Sends email notifications
- Sends Slack notifications
- Uploads artifacts

### Workflow Files

- **ci.yml**: Basic pipeline with email notifications
- **ci-with-slack.yml**: Pipeline with both email and Slack notifications

See [CI_SETUP.md](CI_SETUP.md) for email configuration.
See [SLACK_SETUP.md](SLACK_SETUP.md) for Slack configuration.

## Running Tests Locally

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=NewsletterPOMTest

# Run with verbose output
mvn test -X
```
