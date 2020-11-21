package com.example.taskapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.tasksapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login_screen.*
import kotlinx.android.synthetic.main.activity_register_screen.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RegisterScreen : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_screen)

        supportActionBar?.hide()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        auth = Firebase.auth



        val currentUser = auth.currentUser
        if(currentUser != null){
            val intent = Intent(this, KidMainScreen::class.java)
            startActivity(intent)
            finish()
        }


        registerbutton.setOnClickListener {
            val useremail = inputemailregister.text.toString()
            val userpassword = inputpasswordregister.text.toString()
            val fullname = inputnameregister.text.toString()

            if(fullname.isEmpty()){
                inputnameregister.setError(resources.getString(R.string.nameisrequired))
                return@setOnClickListener
            }
            if(useremail.isEmpty()){
                inputemailregister.setError(resources.getString(R.string.emailisrequired))
                return@setOnClickListener
            }
            if(fullname.length > 10){
                inputnameregister.setError(resources.getString(R.string.usernamecantbelonger))
                return@setOnClickListener
            }
            if(userpassword.isEmpty()){
                inputpasswordregister.setError(resources.getString(R.string.passwordisrequired))
                return@setOnClickListener
            }
            if(userpassword.length < 6){
                inputpasswordregister.setError(resources.getString(R.string.passwordistoshort))
                return@setOnClickListener
            }

            if(checkBox3.isChecked != true && checkBox2.isChecked != true) {

                Toast.makeText(this, resources.getString(R.string.chooseoption), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            progressBar.visibility = View.VISIBLE

            auth.createUserWithEmailAndPassword(useremail, userpassword)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        if(checkBox3.isChecked){
                            val currentDateTime = LocalDateTime.now()

                            val time = currentDateTime.format(DateTimeFormatter.BASIC_ISO_DATE)
                            val memberis = "parent"
                            val userID = auth.currentUser?.uid
                            val db = Firebase.firestore
                            val group = "emptygroup"

                            val user = hashMapOf(
                                "name" to fullname,
                                "memberart" to memberis,
                                "userid" to userID,
                                "useremail" to useremail,
                                "group" to group,
                                "changetime" to time,
                                "currenttask" to "none",
                                "currentdescritption" to "none"
                            )
                            db.collection("users").document(userID!!)
                                .set(user)
                                .addOnSuccessListener { documentReference ->
                                    val intente = Intent(this, makeorjoingroup::class.java)
                                    startActivity(intente)
                                    finish()
                                }
                                .addOnFailureListener { e ->
                                    progressBar.visibility = View.INVISIBLE
                                    //Toast.makeText(baseContext, resources.getString(R.string.tryagainlater),
                                        //Toast.LENGTH_SHORT).show()
                                }

                        }else{
                            val memberis = "child"
                            val userID = auth.currentUser?.uid
                            val db = Firebase.firestore
                            val currentDateTime = LocalDateTime.now()
                            val time = currentDateTime.format(DateTimeFormatter.BASIC_ISO_DATE)
                            val group = "emptygroup"

                            val user = hashMapOf(
                                "name" to fullname,
                                "memberart" to memberis,
                                "userid" to userID,
                                "useremail" to useremail,
                                "group" to group,
                                "changetime" to time,
                                "currenttask" to "none",
                                "currentdescription" to "none"
                            )
                            db.collection("users").document(userID!!)
                                .set(user)
                                .addOnSuccessListener { documentReference ->
                                    val intente = Intent(this, joingroup::class.java)
                                    startActivity(intente)
                                    finish()
                                }
                                .addOnFailureListener { e ->
                                    if(currentUser != null){
                                        Firebase.auth.signOut()
                                    }
                                    progressBar.visibility = View.INVISIBLE
                                    //Toast.makeText(baseContext,  resources.getString(R.string.tryagainlater),
                                        //Toast.LENGTH_SHORT).show()
                                }
                        }

                    } else {
                        if(currentUser != null){
                            Firebase.auth.signOut()
                        }
                        progressBar.visibility = View.INVISIBLE

                    }

                }
        }




        val tv_click_me = findViewById(R.id.loginregisterbutton1) as TextView

        tv_click_me.setOnClickListener {
            val intente = Intent(this, LoginScreen::class.java)
            startActivity(intente)
            finish()
        }
    }
}