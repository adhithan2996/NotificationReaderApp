package com.example.notificationreaderapp

class NotificationManager {
    
    fun getNotifications(): List<NotificationItem> {
        return NotificationService.getNotifications()
    }
    
    fun clearAllNotifications() {
        NotificationService.clearAllNotifications()
    }
} 