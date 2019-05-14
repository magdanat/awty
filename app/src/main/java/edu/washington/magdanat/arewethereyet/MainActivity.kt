package edu.washington.magdanat.arewethereyet

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var message : EditText
    private lateinit var phone : EditText
    private lateinit var interval : EditText
    private lateinit var button : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        message = findViewById(R.id.editMessage)
        phone = findViewById(R.id.editPhone)
        interval = findViewById(R.id.editInterval)
        button = findViewById(R.id.button)

        val alarmSystem = getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        var intent = Intent(this, AlarmReceiver::class.java)

        button.setOnClickListener {
            if (button.text == "Start") {
                if (valid()) {
                    val phoneNumber = phone.text.toString().replace("\\D+".toRegex(), "")
                    val area = phoneNumber.substring(0, 3)
                    val num1 = phoneNumber.substring(3,6)
                    val num2 = phoneNumber.substring(6)
                    val reformattedNumber = "($area) $num1-$num2"

                    val intent = Intent(this, AlarmReceiver::class.java).apply {
                        putExtra("message", message.text.toString())
                        putExtra("interval", interval.text.toString().toInt())
                        putExtra("phone", reformattedNumber)
                    }


//                    Toast.makeText(this@MainActivity, "This is a test!", Toast.LENGTH_SHORT).show()
                    Log.i("Info", "Alert should start")

                    val newIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

                    val secondInterval = (interval.text.toString().toInt() * 60000).toLong()

                    alarmSystem.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), secondInterval, newIntent)

                    Log.i("Info", "Alert should have been sent")

                    button.text = "Stop"
                }
            } else {
                val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
                alarmSystem.cancel(pendingIntent)
                button.text = "Start"
                Log.i("Info", "Alert should stop")
            }
        }
    }

    private fun valid(): Boolean {
        val phoneNumber = phone.text.toString().replace("\\D+".toRegex(), "")
        if (message.text.isNotEmpty() && phone.text.isNotEmpty() && interval.text.isNotEmpty()) {
            if ((interval.text.toString().toInt() > 0)) {
                if (phoneNumber.length == 10) {
                    return true
                }
            }
        }
        return false
    }

}
