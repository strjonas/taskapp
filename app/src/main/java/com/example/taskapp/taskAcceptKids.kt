package com.example.taskapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tasksapp.R

import android.content.Intent
import android.util.Log

import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_task_accept_kids.*

class taskAcceptKids : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_accept_kids)

        val db = Firebase.firestore

        val uistring = getIntent().getStringExtra("image")
        if(uistring != "hello"){
            Glide.with(this)
                .load(uistring)
                .into(imageView)
        }


        val datee = getIntent().getStringExtra("deadline")

        var ausgabe = ""
        var a = datee?.split("/")!!
        ausgabe = ausgabe + a[2] + " "
        when (a[1]){
            "01" -> ausgabe = ausgabe + "January " + ", ${a[0]}"
            "02" -> ausgabe = ausgabe + "February" + ", ${a[0]}"
            "03" -> ausgabe = ausgabe + "March" + ", ${a[0]}"
            "04" -> ausgabe = ausgabe + "April " + ", ${a[0]}"
            "05" -> ausgabe = ausgabe + "May " + ", ${a[0]}"
            "06" -> ausgabe = ausgabe + "June " + ", ${a[0]}"
            "07" -> ausgabe = ausgabe + "July " + ", ${a[0]}"
            "08" -> ausgabe = ausgabe + "August " + ", ${a[0]}"
            "09" -> ausgabe = ausgabe + "September" + ", ${a[0]}"
            "10" -> ausgabe = ausgabe + "October" + ", ${a[0]}"
            "11" -> ausgabe = ausgabe + "November" + ", ${a[0]}"
            "12" -> ausgabe = ausgabe + "Dezember" + ", ${a[0]}"
        }
        findViewById<TextView>(R.id.detail_deadline).text = ausgabe
        findViewById<TextView>(R.id.detail_punkte).text = getIntent().getStringExtra("punkt")
        findViewById<TextView>(R.id.detail_description).text = getIntent().getStringExtra("description")



        findViewById<Button>(R.id.detail_accept).setOnClickListener{
            if (currenttask != "none"){
                Toast.makeText(this, resources.getString(R.string.alreadyworkingonatask),Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            try {
                isworkedones[getIntent().getIntExtra("position",999)] = true
                users[getIntent().getIntExtra("position",999)] = username
                val task = hashMapOf(
                    "discriptions" to descriptions,
                    "points" to punkte,
                    "pictures" to pictures,
                    "users" to users,
                    "isdone" to indones,
                    "donebads" to isdonebads,
                    "isworkedon" to isworkedones,
                    "kidpicture" to kidpictures,
                    "titles" to titles,
                    "deadlines" to deadlines,
                    "isfinisheds" to isfinisheds

                )
                db.collection("tasks").document(gruppe)
                    .set(task)
                    .addOnSuccessListener {
                        try {
                            auth = Firebase.auth
                            val userID = auth.currentUser?.uid
                            val position =getIntent().getIntExtra("position",999)
                            db.collection("users").document(userID!!)
                                .update("currenttask", titles[position])
                            db.collection("users").document(userID)
                                .update("currentdescritption", pictures[position])
                            currenttask = titles[position]
                            currentdescription = pictures[position]
                            Toast.makeText(this,resources.getString(R.string.successfullyacceptedtask), Toast.LENGTH_SHORT).show()
                            val inte = Intent(this, currentTaskKid::class.java)
                            inte.putExtra("position", position)
                            startActivity(inte)
                            finish()
                        }catch (exeption: Throwable){
                            Toast.makeText(this, resources.getString(R.string.tryagainlater), Toast.LENGTH_SHORT).show()
                        }

                    }
                    .addOnFailureListener{
                        Toast.makeText(this,resources.getString(R.string.tryagainlater), Toast.LENGTH_SHORT).show()
                        val inte = Intent(this, KidMainScreen::class.java)
                        startActivity(inte)
                        finish()
                    }
            }catch (exeption: Throwable){
                Toast.makeText(this, resources.getString(R.string.tryagainlater), Toast.LENGTH_SHORT).show()
            }


            val intent = Intent(this, KidMainScreen::class.java)
            startActivity(intent)
            finish()
        }

    }
}