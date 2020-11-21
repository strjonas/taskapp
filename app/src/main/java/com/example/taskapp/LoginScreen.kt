package com.example.taskapp

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.tasksapp.MainActivity
import com.example.tasksapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import kotlinx.android.synthetic.main.activity_login_screen.*
import kotlinx.android.synthetic.main.forgotpassword.*

class LoginScreen : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)

        supportActionBar?.hide()

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        auth = Firebase.auth


        loginbutton.setOnClickListener{

            if(auth.currentUser != null){
                return@setOnClickListener
            }

            val useremail = inputemail.text.toString()
            val userpassword = inputpassword.text.toString()

            if(useremail.isEmpty()){
                inputemail.setError(resources.getString(R.string.emailisrequired))
                return@setOnClickListener

            }
            if(userpassword.isEmpty()){
                inputpassword.setError(resources.getString(R.string.passwordisrequired))
                return@setOnClickListener
            }
            if(userpassword.length < 6){
                inputpassword.setError(resources.getString(R.string.passwordistoshort))
                return@setOnClickListener
            }
            progressBar2.visibility = View.VISIBLE
            val currentUser = auth.currentUser
            auth.signInWithEmailAndPassword(useremail, userpassword)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val userID = auth.currentUser?.uid
                        val db = Firebase.firestore
                        db.collection("users").document(userID!!)
                            .get()
                            .addOnSuccessListener { result ->
                                val art = result.getString("memberart")
                                gruppe = result.getString("group")!!
                                //Toast.makeText(this, gruppe, Toast.LENGTH_LONG).show()

                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                finish()

                            }

                            .addOnFailureListener { exception ->
                                if(progressBar2 != null){
                                    progressBar2.visibility = View.INVISIBLE
                                }
                                if(currentUser != null){
                                    Firebase.auth.signOut()
                                }
                                Toast.makeText(baseContext, resources.getString(R.string.tryagainlater),
                                    Toast.LENGTH_SHORT).show()
                                return@addOnFailureListener
                            }

                    } else {
                        if(progressBar2 != null){
                            progressBar2.visibility = View.INVISIBLE
                        }
                        Toast.makeText(baseContext, resources.getString(R.string.tryagainlater),
                            Toast.LENGTH_SHORT).show()
                        return@addOnCompleteListener
                    }

                }
        }
        val tv_click_me = findViewById(R.id.loginregisterbutton) as TextView

        tv_click_me.setOnClickListener {
            val intente = Intent(this,RegisterScreen::class.java)
            startActivity(intente)
            finish()
        }

        forgotpassword.setOnClickListener{
            val builder:AlertDialog.Builder = AlertDialog.Builder(this,R.style.DialogTheme)
            builder.setTitle(resources.getString(R.string.forgotpassword))
            val view = layoutInflater.inflate(R.layout.forgotpassword, null)
            val username = view.findViewById<EditText>(R.id.resetemail)
            builder.setView(view)
            builder.setPositiveButton("Reset",DialogInterface.OnClickListener{ _, _ ->
                forgotPasswordSendReset(username)
            })
            builder.setNegativeButton("Close",DialogInterface.OnClickListener{ _, _ ->

            })
            builder.show()
        }
    }
    private fun forgotPasswordSendReset(username:EditText){
        if(username.text.toString().isEmpty()){
            username.error = resources.getString(R.string.emailisrequired)
            return
        }
        Firebase.auth.sendPasswordResetEmail(username.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this,resources.getString(R.string.emailsend),Toast.LENGTH_SHORT).show()
                }
            }
    }
}