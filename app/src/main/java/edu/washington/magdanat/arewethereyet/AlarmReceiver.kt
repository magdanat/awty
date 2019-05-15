package edu.washington.magdanat.arewethereyet

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val phone = intent?.getStringExtra("phone")
        val message = intent?.getStringExtra("message")

        val toastMessage = "Texting{$phone}: $message"

        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
    }
}