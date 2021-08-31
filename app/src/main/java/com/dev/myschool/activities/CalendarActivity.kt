package com.dev.myschool.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.view.View
import android.widget.*
import com.dev.myschool.R

class CalendarActivity : AppCompatActivity() {

    private lateinit var tvTitle: EditText
    private lateinit var tvDesc: EditText
    private lateinit var tvLocation: EditText
    private lateinit var btnCreate: Button
    private lateinit var ivClose: ImageView
    private lateinit var tvWarning: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        initViews()

        btnCreate.setOnClickListener {
            addCalendarEvent()
        }

        ivClose.setOnClickListener {
            TeacherActivity.teacherActivity.finish()
            val intent = Intent(this, TeacherActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun initViews() {
        tvTitle = findViewById(R.id.calendar_title_et)
        tvDesc = findViewById(R.id.calendar_desc_et)
        tvLocation = findViewById(R.id.calendar_location_et)
        btnCreate = findViewById(R.id.calendar_btn)
        ivClose = findViewById(R.id.calendar_close_iv)
        tvWarning = findViewById(R.id.calendar_warning_tv)
    }

    private fun addCalendarEvent() {
        val title = tvTitle.text.toString().trim()
        val desc = tvDesc.text.toString().trim()
        val location = tvLocation.text.toString().trim()

        if (title.isNotEmpty() && desc.isNotEmpty() && location.isNotEmpty()) {
            tvWarning.visibility = View.GONE

            val intent = Intent(Intent.ACTION_INSERT)
            intent.data = CalendarContract.Events.CONTENT_URI
            intent.putExtra(CalendarContract.Events.TITLE, title)
            intent.putExtra(CalendarContract.Events.DESCRIPTION, desc)
            intent.putExtra(CalendarContract.Events.EVENT_LOCATION, location)
            intent.putExtra(CalendarContract.Events.ALL_DAY, true)
            intent.putExtra(Intent.EXTRA_EMAIL, "devrahul2215@gmail.com")

            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(
                    this,
                    "There is no app that can support this action",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            tvWarning.visibility = View.VISIBLE
        }

    }

}