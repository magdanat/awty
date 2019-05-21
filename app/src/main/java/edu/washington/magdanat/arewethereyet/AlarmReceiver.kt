package edu.washington.magdanat.arewethereyet

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val phone = intent?.getStringExtra("phone")
        val message = intent?.getStringExtra("message")
        val actualPhone = intent?.getStringExtra("phone2")

        val toastMessage = "Texting{$phone}: $message"

        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
        val smsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(actualPhone, null, toastMessage, null, null)

    }
}