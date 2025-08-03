package com.example.notificationreaderapp

import android.app.Notification
import android.content.Context
import android.content.Intent
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class NotificationService : NotificationListenerService() {
    
    companion object {
        private const val TAG = "NotificationService"
        private val notifications = mutableListOf<NotificationItem>()
        
        fun isNotificationAccessEnabled(context: Context): Boolean {
            val packageName = context.packageName
            val flat = context.contentResolver.getSettingAsString("enabled_notification_listeners")
            return flat?.contains(packageName) == true
        }
        
        fun getNotifications(): List<NotificationItem> {
            return notifications.toList()
        }
        
        fun clearAllNotifications() {
            notifications.clear()
        }
    }
    
    override fun onNotificationPosted(sbn: StatusBarNotification) {
        super.onNotificationPosted(sbn)
        
        val notification = sbn.notification
        val extras = notification.extras
        
        val title = extras.getString(Notification.EXTRA_TITLE) ?: ""
        val text = extras.getString(Notification.EXTRA_TEXT) ?: ""
        val packageName = sbn.packageName
        val postTime = sbn.postTime
        val id = sbn.id
        
        val notificationItem = NotificationItem(
            id = id,
            title = title,
            text = text,
            packageName = packageName,
            postTime = postTime,
            appName = getAppName(packageName)
        )
        
        // Add to the beginning of the list
        notifications.add(0, notificationItem)
        
        // Keep only the last 100 notifications
        if (notifications.size > 100) {
            notifications.removeAt(notifications.size - 1)
        }
        
        Log.d(TAG, "Notification received: $title - $text from $packageName")
        
        // Broadcast to update UI
        sendBroadcast(Intent("NOTIFICATION_RECEIVED"))
    }
    
    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        super.onNotificationRemoved(sbn)
        
        // Remove the notification from our list
        notifications.removeAll { it.id == sbn.id && it.packageName == sbn.packageName }
        
        Log.d(TAG, "Notification removed: ${sbn.notification.extras.getString(Notification.EXTRA_TITLE)}")
        
        // Broadcast to update UI
        sendBroadcast(Intent("NOTIFICATION_REMOVED"))
    }
    
    private fun getAppName(packageName: String): String {
        return try {
            val packageManager = packageManager
            val applicationInfo = packageManager.getApplicationInfo(packageName, 0)
            packageManager.getApplicationLabel(applicationInfo).toString()
        } catch (e: Exception) {
            Log.e(TAG, "Error getting app name for $packageName", e)
            packageName
        }
    }
} 