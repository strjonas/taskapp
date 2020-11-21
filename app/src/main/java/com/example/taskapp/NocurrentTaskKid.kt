package com.example.taskapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tasksapp.R
import kotlinx.android.synthetic.main.activity_nocurrent_task_kid.*

class NocurrentTaskKid : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nocurrent_task_kid)

        backtotasksbutton.setOnClickListener{
            val intent = Intent(this, KidMainScreen::class.java)
            startActivity(intent)
            finish()
        }
    }
}