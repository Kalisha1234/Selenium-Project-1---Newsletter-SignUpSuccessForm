# CI/CD Setup Guide

## GitHub Actions Configuration

This project includes two CI workflows:

### 1. Basic CI Pipeline (`ci.yml`)
- Triggers on push/PR to main, master, or develop branches
- Runs all Selenium tests
- Uploads test reports as artifacts
- Shows build status in GitHub Actions summary

### 2. CI with Notifications (`ci-with-notifications.yml`)
- All features from basic CI
- Email notifications
- Slack notifications

## Setting Up Notifications

### Email Notifications Setup

1. Go to your GitHub repository → Settings → Secrets and variables → Actions
2. Add the following secrets:
   - `EMAIL_USERNAME`: Your Gmail address
   - `EMAIL_PASSWORD`: Gmail app password (not your regular password)
   - `EMAIL_TO`: Recipient email address

**To create Gmail App Password:**
1. Enable 2-factor authentication on your Google account
2. Go to https://myaccount.google.com/apppasswords
3. Generate a new app password for "Mail"
4. Use this password in `EMAIL_PASSWORD` secret

### Slack Notifications Setup

1. Create a Slack Incoming Webhook:
   - Go to https://api.slack.com/apps
   - Create a new app or select existing
   - Enable "Incoming Webhooks"
   - Add webhook to your workspace
   - Copy the webhook URL

2. Add to GitHub Secrets:
   - Go to repository Settings → Secrets and variables → Actions
   - Add secret: `SLACK_WEBHOOK_URL` with your webhook URL

## Workflow Features

- ✅ Automatic dependency installation
- ✅ Headless Chrome testing
- ✅ Test report generation
- ✅ Artifact upload (30-day retention)
- ✅ Detailed execution logs
- ✅ Build status notifications
- ✅ Email/Slack alerts on pass/fail

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
