package com.example.taskapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.tasksapp.R
import kotlinx.android.synthetic.main.activity_makeorjoingroup.*

class makeorjoingroup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_makeorjoingroup)


        joingroupbutton.setOnClickListener{
            val intent1 = Intent(this, joingroup::class.java)
            startActivity(intent1)
            finish()
        }
        creategroupbutton.setOnClickListener{
            val inten2 = Intent(this, creategroup::class.java)
            startActivity(inten2)
            finish()
        }
        val clickforinfos = findViewById(R.id.textView22) as TextView

        clickforinfos.setOnClickListener{
            val intent = Intent(this, whydoineedagroup::class.java)
            intent.putExtra("from", "makeorjoingroup")
            startActivity(intent)
            finish()
        }


    }
}