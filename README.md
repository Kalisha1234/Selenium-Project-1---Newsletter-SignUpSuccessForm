# Selenium WebDriver Setup

## Prerequisites
- Java 11 or higher
- Maven 3.6+
- Chrome browser installed

## Dependencies
- **Selenium Java**: Browser automation
- **WebDriverManager**: Automatic ChromeDriver management
- **JUnit 5**: Testing framework

## Setup Steps

1. Navigate to the selenium-project directory:
```bash
cd selenium-project
```

2. Install dependencies:
```bash
mvn clean install
```

3. Run tests:
```bash
mvn test
```

## How It Works

- **WebDriverManager** automatically downloads and configures ChromeDriver
- No manual ChromeDriver download needed
- Tests run against the local HTML files

## Test Structure

- `@BeforeAll`: Sets up ChromeDriver once
- `@BeforeEach`: Initializes browser before each test
- `@Test`: Test methods
- `@AfterEach`: Closes browser after each test
