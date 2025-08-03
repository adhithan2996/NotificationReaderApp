package com.example.notificationreaderapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notificationreaderapp.databinding.ItemNotificationBinding

class NotificationAdapter : ListAdapter<NotificationItem, NotificationAdapter.NotificationViewHolder>(NotificationDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val binding = ItemNotificationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class NotificationViewHolder(private val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(notification: NotificationItem) {
            binding.apply {
                tvTitle.text = notification.title.ifEmpty { "No title" }
                tvText.text = notification.text.ifEmpty { "No content" }
                tvAppName.text = notification.appName
                tvTime.text = notification.getFormattedTime()
                
                // Set app icon (you can enhance this to load actual app icons)
                ivAppIcon.setImageResource(android.R.drawable.ic_dialog_info)
            }
        }
    }

    private class NotificationDiffCallback : DiffUtil.ItemCallback<NotificationItem>() {
        override fun areItemsTheSame(oldItem: NotificationItem, newItem: NotificationItem): Boolean {
            return oldItem.id == newItem.id && oldItem.packageName == newItem.packageName
        }

        override fun areContentsTheSame(oldItem: NotificationItem, newItem: NotificationItem): Boolean {
            return oldItem == newItem
        }
    }
} 