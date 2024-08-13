package com.example.notificationapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    private lateinit var textViewDisplayData: TextView
    private val CHANNEL_ID = "my_channel"
    private val NOTIFICATION_ID = 1
    private var notificationManager: NotificationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        textViewDisplayData = findViewById(R.id.textViewDisplayData)
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val title = intent.getStringExtra("extra_title")
        val description = intent.getStringExtra("extra_description")
        textViewDisplayData.text = "Title: $title\nDescription: $description"

        createNotificationChannel()
        showNotification(title, description)
    }

    private fun showNotification(title: String?, description: String?) {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)

        val builder: Notification.Builder =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Notification.Builder(this, CHANNEL_ID)
            } else {
                Notification.Builder(this)
            }

        builder.setSmallIcon(R.drawable.notification_bell)
            .setContentTitle(title)
            .setContentText(description)
            .setPriority(Notification.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notification = builder.build()
        notificationManager!!.notify(NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName: CharSequence = "My Channel"
            val channelDescription = "Channel for notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, channelName, importance)
            channel.description = channelDescription
            notificationManager!!.createNotificationChannel(channel)
        }
    }
}
