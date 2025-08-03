package com.example.notificationreaderapp

import java.text.SimpleDateFormat
import java.util.*

data class NotificationItem(
    val id: Int,
    val title: String,
    val text: String,
    val packageName: String,
    val postTime: Long,
    val appName: String
) {
    fun getFormattedTime(): String {
        val date = Date(postTime)
        val now = Date()
        val diffInMillis = now.time - postTime
        
        return when {
            diffInMillis < 60000 -> "Just now" // Less than 1 minute
            diffInMillis < 3600000 -> "${diffInMillis / 60000}m ago" // Less than 1 hour
            diffInMillis < 86400000 -> "${diffInMillis / 3600000}h ago" // Less than 1 day
            else -> {
                val dateFormat = SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault())
                dateFormat.format(date)
            }
        }
    }
} 