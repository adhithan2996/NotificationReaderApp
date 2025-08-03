package com.example.notificationreaderapp

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notificationreaderapp.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var notificationAdapter: NotificationAdapter
    private val notificationManager = NotificationManager()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupToolbar()
        setupRecyclerView()
        setupClickListeners()
        checkNotificationAccess()
    }
    
    override fun onResume() {
        super.onResume()
        checkNotificationAccess()
        loadNotifications()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
    }
    
    private fun setupRecyclerView() {
        notificationAdapter = NotificationAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = notificationAdapter
        }
    }
    
    private fun setupClickListeners() {
        binding.btnEnableAccess.setOnClickListener {
            openNotificationSettings()
        }
        
        binding.fabRefresh.setOnClickListener {
            loadNotifications()
        }
    }
    
    private fun checkNotificationAccess() {
        val isEnabled = NotificationService.isNotificationAccessEnabled(this)
        binding.permissionCard.visibility = if (isEnabled) View.GONE else View.VISIBLE
        
        if (isEnabled) {
            loadNotifications()
        }
    }
    
    private fun openNotificationSettings() {
        val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
        startActivity(intent)
    }
    
    private fun loadNotifications() {
        if (!NotificationService.isNotificationAccessEnabled(this)) {
            showEmptyState()
            return
        }
        
        val notifications = notificationManager.getNotifications()
        if (notifications.isEmpty()) {
            showEmptyState()
        } else {
            showNotifications(notifications)
        }
    }
    
    private fun showEmptyState() {
        binding.emptyState.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
    }
    
    private fun showNotifications(notifications: List<NotificationItem>) {
        binding.emptyState.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
        notificationAdapter.submitList(notifications)
    }
    
    private fun clearAllNotifications() {
        notificationManager.clearAllNotifications()
        loadNotifications()
        Snackbar.make(binding.root, "All notifications cleared", Snackbar.LENGTH_SHORT).show()
    }
    
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_clear_all -> {
                clearAllNotifications()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
} 