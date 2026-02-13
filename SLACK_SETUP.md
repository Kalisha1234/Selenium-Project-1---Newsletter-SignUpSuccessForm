# Slack Notification Setup Guide

## Step 1: Create a Slack Workspace (if you don't have one)

1. Go to https://slack.com/create
2. Enter your email address
3. Check your email and enter the confirmation code
4. Create your workspace:
   - **Workspace name**: e.g., "Selenium Testing Team"
   - **Project name**: e.g., "Newsletter Tests"
5. Skip inviting teammates (or invite if you want)
6. You now have a new Slack workspace!

## Step 2: Create a Slack Channel for Notifications

1. In your Slack workspace, click the **+** next to "Channels"
2. Click **Create a channel**
3. Name it: `github-notifications` or `test-results`
4. Make it **Public** or **Private** (your choice)
5. Click **Create**

## Step 3: Create a Slack App and Incoming Webhook

### 3.1: Create Slack App

1. Go to https://api.slack.com/apps
2. Click **Create New App**
3. Choose **From scratch**
4. Fill in:
   - **App Name**: `GitHub Actions Notifier`
   - **Pick a workspace**: Select your workspace
5. Click **Create App**

### 3.2: Enable Incoming Webhooks

1. In the left sidebar, click **Incoming Webhooks**
2. Toggle **Activate Incoming Webhooks** to **On**
3. Scroll down and click **Add New Webhook to Workspace**
4. Select the channel you created (e.g., `#github-notifications`)
5. Click **Allow**

### 3.3: Copy Webhook URL

1. You'll see a **Webhook URL** that looks like:
   ```
   https://hooks.slack.com/services/T00000000/B00000000/XXXXXXXXXXXXXXXXXXXX
   ```
2. **Copy this URL** - you'll need it for GitHub Secrets

## Step 4: Add Webhook to GitHub Secrets

1. Go to your GitHub repository
2. Click **Settings** → **Secrets and variables** → **Actions**
3. Click **New repository secret**
4. Add:
   - **Name**: `SLACK_WEBHOOK_URL`
   - **Secret**: Paste the webhook URL you copied
5. Click **Add secret**

## Step 5: Test the Setup

1. Commit and push any change:
   ```bash
   git add .
   git commit -m "Add Slack notifications"
   git push
   ```

2. Go to GitHub Actions tab and watch the workflow run

3. Check your Slack channel - you should see a notification with:
   - Test status (success/failure)
   - Repository name
   - Branch name
   - Commit hash
   - Link to view details

## Workflow Files

You now have two workflow options:

### Option 1: Email Only (`ci.yml`)
- Sends email notifications only
- Simpler setup

### Option 2: Email + Slack (`ci-with-slack.yml`)
- Sends both email and Slack notifications
- Best for team collaboration

## Troubleshooting

**No Slack notification received?**
- Verify `SLACK_WEBHOOK_URL` secret is set correctly
- Check the webhook URL is complete (starts with `https://hooks.slack.com/`)
- Ensure the Slack app has permission to post in the channel
- Check GitHub Actions logs for errors in "Send Slack notification" step

**Slack notification shows error?**
- The webhook URL might be expired - regenerate it in Slack API settings
- Make sure the channel still exists

## What the Notification Looks Like

When tests run, you'll see in Slack:

```
Selenium Test Results
Repository: YourUsername/Selenium-Project-1---Newsletter-SignUpSuccessForm
Branch: main
Commit: abc123def456
Status: success

View details: [link to GitHub Actions run]
```

## Customizing Notifications

You can customize the message in `.github/workflows/ci-with-slack.yml`:

```yaml
text: |
  *Your Custom Message*
  Status: ${{ job.status }}
  Add any text you want here
```

Use Slack markdown for formatting:
- `*bold*` for **bold**
- `_italic_` for _italic_
- `` `code` `` for `code`
