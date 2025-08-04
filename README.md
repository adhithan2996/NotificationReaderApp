# Notification Reader App

A native Android app built with Kotlin that reads and displays all phone notifications in a beautiful Material Design 3 interface.

## Features

- **Real-time Notification Reading**: Captures all notifications from all apps
- **Material Design 3**: Modern, beautiful UI following the latest Material Design guidelines
- **Permission Management**: Handles notification access permissions gracefully
- **Notification History**: Displays notifications with timestamps and app information
- **Clear All**: Option to clear all stored notifications
- **Refresh**: Manual refresh capability

## Setup Instructions

1. **Clone or download the project**
2. **Open in Android Studio**
3. **Sync Gradle files**
4. **Build the project**

## Usage

1. **Install the app** on your Android device
2. **Grant notification access** when prompted:
   - Go to Settings > Apps & notifications > Special app access > Notification access
   - Enable access for "Notification Reader"
3. **The app will start displaying notifications** as they arrive

## Permissions

The app requires the following permissions:
- `BIND_NOTIFICATION_LISTENER_SERVICE`: To read notifications
- `FOREGROUND_SERVICE`: For the notification service
- `POST_NOTIFICATIONS`: To post notifications (Android 13+)

## Building

```bash
# Using Gradle wrapper
./gradlew build

# Using Android Studio
# Simply click "Build > Make Project"
```

## Testing

1. Build and install the app
2. Grant notification permissions
3. Send test notifications from other apps
4. Verify notifications appear in the app
