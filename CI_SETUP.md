# CI/CD Setup Guide

## GitHub Actions Configuration

This project uses a CI pipeline that automatically runs tests and sends email notifications.

### Workflow Features (`ci.yml`)
- Triggers on push/PR to main, master, or develop branches
- Runs all Selenium tests
- Uploads test reports as artifacts (30-day retention)
- Sends email notifications on pass/fail
- Shows build status in GitHub Actions summary

## Setting Up Email Notifications

### Step 1: Create Gmail App Password

1. Enable 2-factor authentication on your Google account
2. Go to https://myaccount.google.com/apppasswords
3. Generate a new app password for "Mail"
4. Copy the generated password

### Step 2: Add GitHub Secrets

1. Go to your GitHub repository → Settings → Secrets and variables → Actions
2. Click "New repository secret" and add:
   - **Name**: `EMAIL_USERNAME` | **Value**: Your Gmail address
   - **Name**: `EMAIL_PASSWORD` | **Value**: Gmail app password from Step 1
   - **Name**: `EMAIL_TO` | **Value**: Recipient email address

## Workflow Features

- ✅ Automatic dependency installation
- ✅ Headless Chrome testing
- ✅ Test report generation
- ✅ Artifact upload (30-day retention)
- ✅ Detailed execution logs
- ✅ Email alerts on pass/fail with build details

## Viewing Test Results

1. Go to Actions tab in your GitHub repository
2. Click on the latest workflow run
3. View logs for each step
4. Download test reports from Artifacts section

## Local Testing Before Push

```bash
mvn clean test
```

This ensures tests pass locally before triggering CI pipeline.
