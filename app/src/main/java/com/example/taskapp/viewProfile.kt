package com.example.taskapp

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.tasksapp.R
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_view_profile.*

class viewProfile : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    var name = ""
    var email = ""
    var group = ""
    var role = ""
    var pwd = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_profile)
        setSupportActionBar(findViewById(R.id.toolbarprofile))
        val actionBar = supportActionBar
        actionBar!!.title = getResources().getString(R.string.profile)

        auth = Firebase.auth
        val db = Firebase.firestore

        val ownuserid = auth.currentUser?.uid

        db.collection("users").document(ownuserid!!)
            .get()
            .addOnSuccessListener { result ->
                name = result.getString("name")!!
                email = result.getString("useremail")!!
                group = result.getString("group")!!
                role = result.getString("memberart")!!
                editTextName.setText(name)

                editTextEmail.setText(email)
                editTextGruppe.setText(group)
                editTextRolle.setText(role)
            }
        SaveChangeButton.setOnClickListener{
            changeThings()
        }
        profileCloseButton.setOnClickListener{
            if(role == "parent"){
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
    fun changeThings(){

        progressBar5.visibility = View.VISIBLE

        if(email != editTextEmail.text.toString()){
            editTextEmail.setError("You can't change your email!")
        }
        if(name != editTextName.text.toString()){
            if(group != "emptygroup"){
                editTextName.setError("You can only change youre name, while youre not in a group!")
            }else{
                changeName()
            }
        }
        if(group != editTextGruppe.text.toString()){
            editTextGruppe.setError("You can't change the group here!")
        }
        if(role != editTextRolle.text.toString()){
            editTextRolle.setError("You can't change youre role!")
        }
        progressBar5.visibility = View.INVISIBLE
    }
    private fun changeName(){
        val db = Firebase.firestore
        val ownuserid = auth.currentUser?.uid
        name = editTextName.text.toString()

        db.collection("users").document(ownuserid!!)
            .update("name", name)
        Toast.makeText(this,"Succesfully changed name!", Toast.LENGTH_SHORT).show()
        editTextName.setText(name)
    }

}