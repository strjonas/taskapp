package com.example.taskapp

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionManager
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.tasksapp.MainActivity
import com.example.tasksapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.content_group_leaderboard.*
import kotlinx.android.synthetic.main.editusergroup.*

class group_leaderboard : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_leaderboard)
        setSupportActionBar(findViewById(R.id.toolbar))
        val actionBar = supportActionBar
        actionBar?.title = gruppe

        val buttonreset = findViewById<Button>(R.id.button9)
        val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)
        val button3 = findViewById<Button>(R.id.button3)
        val button4 = findViewById<Button>(R.id.button4)
        val button5 = findViewById<Button>(R.id.button5)
        val button6 = findViewById<Button>(R.id.button6)
        val button7 = findViewById<Button>(R.id.button7)
        val button8 = findViewById<Button>(R.id.button8)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        var width = displayMetrics.widthPixels
        var height = displayMetrics.heightPixels

        auth = Firebase.auth
        val userID = auth.currentUser?.uid
        val db = Firebase.firestore

        var mitglieder = mutableListOf<Int>()

        val minsize = 300

        var user1 = mutableListOf<String>("elias","23","parent")
        var user2 = mutableListOf<String>("jonas","20","kid")
        var user3 = mutableListOf<String>("noah","6","kid")
        var user4 = mutableListOf<String>("laf","0","kid")
        var user5 = mutableListOf<String>("daf","0","kid")
        var user6 = mutableListOf<String>("egeg","0","kid")
        var user7 = mutableListOf<String>("aefa","0","kid")
        var user8 = mutableListOf<String>("dd","0","parent")

        db.collection("groups").document(gruppe)
            .get()
            .addOnSuccessListener {

                var anymap = it.get("user1")
                if (anymap != "notdefined") {
                    var hasmap = anymap as HashMap<String, String>
                    user1.clear()
                    user1.add(hasmap.get("name")!!)
                    user1.add(hasmap.get("points")!!)
                    user1.add(hasmap.get("role")!!)
                    user1.add(hasmap.get("id")!!)
                    mitglieder.add(1)
                    if (hasmap.get("role") == "parent"){
                        button1.setBackgroundResource(R.drawable.parentbutton)
                        button1.text =hasmap.get("name") + " - parent"
                    }else {
                        button1.text = hasmap.get("name") + " - " + hasmap.get("points")
                    }


                }
                anymap = it.get("user2")
                if (anymap != "notdefined") {
                    var hasmap = anymap as HashMap<String, String>
                    user2.clear()
                    user2.add(hasmap.get("name")!!)
                    user2.add(hasmap.get("points")!!)
                    user2.add(hasmap.get("role")!!)
                    user2.add(hasmap.get("id")!!)
                    mitglieder.add(1)
                    if (hasmap.get("role") == "parent") {
                        button2.setBackgroundResource(R.drawable.parentbutton)
                        button2.text = hasmap.get("name") + " - parent"
                    } else {
                        button2.text = hasmap.get("name") + " - " + hasmap.get("points")
                    }

                }

                anymap = it.get("user3")
                if (anymap != "notdefined") {
                    var hasmap = anymap as HashMap<String, String>
                    user3.clear()
                    user3.add(hasmap.get("name")!!)
                    user3.add(hasmap.get("points")!!)
                    user3.add(hasmap.get("role")!!)
                    user3.add(hasmap.get("id")!!)
                    mitglieder.add(1)
                    if (hasmap.get("role") == "parent"){
                        button3.setBackgroundResource(R.drawable.parentbutton)
                        button3.text =hasmap.get("name") + " - parent"
                    }else {
                        button3.text = hasmap.get("name") + " - " + hasmap.get("points")
                    }
                }

                anymap = it.get("user4")
                if (anymap != "notdefined") {
                    var hasmap = anymap as HashMap<String, String>
                    user4.clear()
                    user4.add(hasmap.get("name")!!)
                    user4.add(hasmap.get("points")!!)
                    user4.add(hasmap.get("role")!!)
                    user4.add(hasmap.get("id")!!)
                    mitglieder.add(1)
                    if (hasmap.get("role") == "parent"){
                        button4.setBackgroundResource(R.drawable.parentbutton)
                        button4.text =hasmap.get("name") + " - parent"
                    }else {
                        button4.text = hasmap.get("name") + " - " + hasmap.get("points")
                    }
                }

                anymap = it.get("user5")
                if (anymap != "notdefined") {
                    var hasmap = anymap as HashMap<String, String>
                    user5.clear()
                    user5.add(hasmap.get("name")!!)
                    user5.add(hasmap.get("points")!!)
                    user5.add(hasmap.get("role")!!)
                    user5.add(hasmap.get("id")!!)
                    mitglieder.add(1)
                    if (hasmap.get("role") == "parent"){
                        button5.setBackgroundResource(R.drawable.parentbutton)
                        button5.text =hasmap.get("name") + " - parent"
                    }else {
                        button5.text = hasmap.get("name") + " - " + hasmap.get("points")
                    }
                }

                anymap = it.get("user6")
                if (anymap != "notdefined") {
                    var hasmap = anymap as HashMap<String, String>
                    user6.clear()
                    user6.add(hasmap.get("name")!!)
                    user6.add(hasmap.get("points")!!)
                    user6.add(hasmap.get("role")!!)
                    user6.add(hasmap.get("id")!!)
                    mitglieder.add(1)
                    if (hasmap.get("role") == "parent"){
                        button6.setBackgroundResource(R.drawable.parentbutton)
                        button6.text =hasmap.get("name") + " - parent"
                    }else {
                        button6.text = hasmap.get("name") + " - " + hasmap.get("points")
                    }
                }

                anymap = it.get("user7")
                if (anymap != "notdefined") {
                    var hasmap = anymap as HashMap<String, String>
                    user7.clear()
                    user7.add(hasmap.get("name")!!)
                    user7.add(hasmap.get("points")!!)
                    user7.add(hasmap.get("role")!!)
                    user7.add(hasmap.get("id")!!)
                    mitglieder.add(1)
                    if (hasmap.get("role") == "parent"){
                        button7.setBackgroundResource(R.drawable.parentbutton)
                        button7.text =hasmap.get("name") + " - parent"
                    }else {
                        button7.text = hasmap.get("name") + " - " + hasmap.get("points")
                    }
                }

                anymap = it.get("user8")
                if (anymap != "notdefined") {
                    var hasmap = anymap as HashMap<String, String>
                    user8.clear()
                    user8.add(hasmap.get("name")!!)
                    user8.add(hasmap.get("points")!!)
                    user8.add(hasmap.get("role")!!)
                    user8.add(hasmap.get("id")!!)
                    mitglieder.add(1)
                    if (hasmap.get("role") == "parent"){
                        button8.setBackgroundResource(R.drawable.parentbutton)
                        button8.text =hasmap.get("name") + " - parent"
                    }else {
                        button8.text = hasmap.get("name") + " - " + hasmap.get("points")
                    }
                }
                button1.layoutParams = LinearLayout.LayoutParams(user1[1].toInt() * 10 + minsize ,120)
                button2.layoutParams = LinearLayout.LayoutParams(user2[1].toInt() * 10+ minsize, 120)
                button3.layoutParams = LinearLayout.LayoutParams(user3[1].toInt() * 10+ minsize, 120)
                button4.layoutParams = LinearLayout.LayoutParams(user4[1].toInt() * 10+ minsize, 120)
                button5.layoutParams = LinearLayout.LayoutParams(user5[1].toInt() * 10+ minsize, 120)
                button6.layoutParams = LinearLayout.LayoutParams(user6[1].toInt() * 10+ minsize, 120)
                button7.layoutParams = LinearLayout.LayoutParams(user7[1].toInt() * 10+ minsize, 120)
                button8.layoutParams = LinearLayout.LayoutParams(user8[1].toInt() * 10+ minsize, 120)


                when (mitglieder.size){
                    1 -> button1.visibility = View.VISIBLE
                    2 -> {button1.visibility = View.VISIBLE
                        button2.visibility = View.VISIBLE}
                    3 -> {button1.visibility = View.VISIBLE
                        button2.visibility = View.VISIBLE
                        button3.visibility = View.VISIBLE}
                    4 ->{button1.visibility = View.VISIBLE
                        button2.visibility = View.VISIBLE
                        button3.visibility = View.VISIBLE
                        button4.visibility = View.VISIBLE}
                    5 ->{button1.visibility = View.VISIBLE
                        button2.visibility = View.VISIBLE
                        button3.visibility = View.VISIBLE
                        button4.visibility = View.VISIBLE
                        button5.visibility = View.VISIBLE}
                    6 ->{button1.visibility = View.VISIBLE
                        button2.visibility = View.VISIBLE
                        button3.visibility = View.VISIBLE
                        button4.visibility = View.VISIBLE
                        button5.visibility = View.VISIBLE
                        button6.visibility = View.VISIBLE}
                    7 ->{button1.visibility = View.VISIBLE
                        button2.visibility = View.VISIBLE
                        button3.visibility = View.VISIBLE
                        button4.visibility = View.VISIBLE
                        button5.visibility = View.VISIBLE
                        button6.visibility = View.VISIBLE
                        button7.visibility = View.VISIBLE}
                    8 ->{  button1.visibility = View.VISIBLE
                        button2.visibility = View.VISIBLE
                        button3.visibility = View.VISIBLE
                        button4.visibility = View.VISIBLE
                        button5.visibility = View.VISIBLE
                        button6.visibility = View.VISIBLE
                        button7.visibility = View.VISIBLE
                        button8.visibility = View.VISIBLE
                    }
                    else ->{
                        button1.visibility = View.VISIBLE
                    }
                }
            }
        buttonreset.setOnClickListener {

                    if (rolle != "parent") {
                        Toast.makeText(
                            this,
                            resources.getString(R.string.yourenotaparent),
                            Toast.LENGTH_SHORT
                        ).show()
                        return@setOnClickListener
                    }
                    user1[1] = "0"
                    user2[1] = "0"
                    user3[1] = "0"
                    user4[1] = "0"
                    user5[1] = "0"
                    user6[1] = "0"
                    user7[1] = "0"
                    user8[1] = "0"
                    db.collection("groups").document(gruppe)
                        .get()
                        .addOnSuccessListener {

                            var anymap = it.get("user1")
                            if (anymap != "notdefined") {
                                var hasmap = anymap as HashMap<String, String>
                                hasmap.set("points", "0")
                                db.collection("groups").document(gruppe)
                                    .update("user1", hasmap)

                            }
                            anymap = it.get("user2")
                            if (anymap != "notdefined") {
                                var hasmap = anymap as HashMap<String, String>
                                hasmap.set("points", "0")
                                db.collection("groups").document(gruppe)
                                    .update("user2", hasmap)
                            }

                            anymap = it.get("user3")
                            if (anymap != "notdefined") {
                                var hasmap = anymap as HashMap<String, String>
                                hasmap.set("points", "0")
                                db.collection("groups").document(gruppe)
                                    .update("user3", hasmap)
                            }

                            anymap = it.get("user4")
                            if (anymap != "notdefined") {
                                var hasmap = anymap as HashMap<String, String>
                                hasmap.set("points", "0")
                                db.collection("groups").document(gruppe)
                                    .update("user4", hasmap)
                            }

                            anymap = it.get("user5")
                            if (anymap != "notdefined") {
                                var hasmap = anymap as HashMap<String, String>
                                hasmap.set("points", "0")
                                db.collection("groups").document(gruppe)
                                    .update("user5", hasmap)
                            }

                            anymap = it.get("user6")
                            if (anymap != "notdefined") {
                                var hasmap = anymap as HashMap<String, String>
                                hasmap.set("points", "0")
                                db.collection("groups").document(gruppe)
                                    .update("user6", hasmap)
                            }

                            anymap = it.get("user7")
                            if (anymap != "notdefined") {
                                var hasmap = anymap as HashMap<String, String>
                                hasmap.set("points", "0")
                                db.collection("groups").document(gruppe)
                                    .update("user7", hasmap)
                            }

                            anymap = it.get("user8")
                            if (anymap != "notdefined") {
                                var hasmap = anymap as HashMap<String, String>
                                hasmap.set("points", "0")
                                db.collection("groups").document(gruppe)
                                    .update("user8", hasmap)
                            }

                            button1.layoutParams =
                                LinearLayout.LayoutParams(user1[1].toInt() * 10 + minsize, 120)
                            button2.layoutParams =
                                LinearLayout.LayoutParams(user2[1].toInt() * 10 + minsize, 120)
                            button3.layoutParams =
                                LinearLayout.LayoutParams(user3[1].toInt() * 10 + minsize, 120)
                            button4.layoutParams =
                                LinearLayout.LayoutParams(user4[1].toInt() * 10 + minsize, 120)
                            button5.layoutParams =
                                LinearLayout.LayoutParams(user5[1].toInt() * 10 + minsize, 120)
                            button6.layoutParams =
                                LinearLayout.LayoutParams(user6[1].toInt() * 10 + minsize, 120)
                            button7.layoutParams =
                                LinearLayout.LayoutParams(user7[1].toInt() * 10 + minsize, 120)
                            button8.layoutParams =
                                LinearLayout.LayoutParams(user8[1].toInt() * 10 + minsize, 120)
                            button1.text = user1[0] + " - " +user1[1]
                            button2.text = user2[0] +  " - " +user2[1]
                            button3.text = user3[0] + " - " + user3[1]
                            button4.text = user4[0] +  " - " +user4[1]
                            button5.text = user5[0] + " - " + user5[1]
                            button6.text = user6[0] + " - " + user6[1]
                            button7.text = user7[0] + " - " + user7[1]
                            button8.text = user8[0] +  " - " +user8[1]


                }

        }
        button11.setOnClickListener{
            if(rolle == "parent"){
                val intent = Intent(this, ParentMainScreen::class.java)
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(this, KidMainScreen::class.java)
                startActivity(intent)
                finish()
            }

        }
    }

}

