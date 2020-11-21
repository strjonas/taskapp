package com.example.taskapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tasksapp.R
import kotlinx.android.synthetic.main.activity_whydoineedagroup.*

class howdoesitwork : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_howdoesitwork)

        buttonokay.setOnClickListener{
            val from = getIntent().getStringExtra("from")

            when (from){
                "makeorjoingroup" -> {

                    val intente = Intent(this, makeorjoingroup::class.java)
                    startActivity(intente)
                    finish()

                }
                "joingroup" -> {

                    val intente = Intent(this, joingroup::class.java)
                    startActivity(intente)
                    finish()

                }
                "creategroup" -> {

                    val intente = Intent(this, creategroup::class.java)
                    startActivity(intente)
                    finish()

                }
                "newtask" -> {
                    val intente = Intent(this, NewTask::class.java)
                    startActivity(intente)
                    finish()
                }
            }
        }
    }
}