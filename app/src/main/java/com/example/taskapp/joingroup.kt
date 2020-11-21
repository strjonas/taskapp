package com.example.taskapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.tasksapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_creategroup.*
import kotlinx.android.synthetic.main.activity_joingroup.*

class joingroup : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_joingroup)


        auth = Firebase.auth

        val db = Firebase.firestore

        val userID = auth.currentUser?.uid
        var username = ""
        var userrole = ""

        textView223.setOnClickListener{
            val intent = Intent(this, whydoineedagroup::class.java)
            intent.putExtra("from", "joingroup")
            startActivity(intent)
            finish()
        }

        joinggroupbutton.setOnClickListener{
            val groupname = existinggroupname.text.toString()
            if(groupname.isEmpty()){
                Toast.makeText(this, getResources().getString(R.string.groupnameisrequired),Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            db.collection("groups").document(groupname)
                .get()
                .addOnSuccessListener {
                    if(it.data !=null){

                        progressBar4.visibility = View.VISIBLE
                        db.collection("users").document(userID!!)
                            .get()
                            .addOnSuccessListener { result ->

                                username = result.getString("name")!!
                                userrole = result.getString("memberart")!!

                                if(username.isEmpty()){
                                    Toast.makeText(this, getResources().getString(R.string.clickagaintoconfirm), Toast.LENGTH_SHORT).show()
                                    return@addOnSuccessListener
                                }
                                var userinfos = hashMapOf(
                                    "name" to username,
                                    "role" to userrole,
                                    "points" to "0",
                                    "id" to userID,
                                    "currenttask" to "none"
                                )
                                gruppe = groupname
                                db.collection("groups").document(groupname)
                                    .get()
                                    .addOnSuccessListener {
                                        if(it.get("user2").toString() == "notdefined"){

                                            db.collection("groups").document(groupname)
                                                .update("user2",userinfos)

                                        }else if(it.get("user3").toString() == "notdefined"){

                                            db.collection("groups").document(groupname)
                                                .update("user3",userinfos)
                                        }else if(it.get("user4").toString() == "notdefined"){

                                            db.collection("groups").document(groupname)
                                                .update("user4",userinfos)
                                        }else if(it.get("user5").toString() == "notdefined"){

                                            db.collection("groups").document(groupname)
                                                .update("user5",userinfos)
                                        }else if(it.get("user6").toString() == "notdefined"){

                                            db.collection("groups").document(groupname)
                                                .update("user6",userinfos)
                                        }else if(it.get("user7").toString() == "notdefined"){

                                            db.collection("groups").document(groupname)
                                                .update("user7",userinfos)
                                        }else if(it.get("user8").toString() == "notdefined"){


                                            db.collection("groups").document(groupname)
                                                .update("user8",userinfos)
                                        }else{
                                            Toast.makeText(this,getResources().getString(R.string.groupyouaretryingtojoinisfull), Toast.LENGTH_SHORT).show()
                                            return@addOnSuccessListener
                                        }
                                    }
                                db.collection("users").document(userID)
                                    .update("group", groupname)
                                    .addOnSuccessListener {
                                        Toast.makeText(this,getString(R.string.successfullyjoined)+groupname, Toast.LENGTH_SHORT).show()
                                        progressBar4.visibility = View.INVISIBLE

                                    }
                                    .addOnFailureListener { exception ->
                                        Toast.makeText(this,getString(R.string.tryagainlater), Toast.LENGTH_SHORT).show()
                                        progressBar3.visibility = View.INVISIBLE
                                        return@addOnFailureListener
                                    }
                                //val intent6 = Intent(this, editGroupScreen::class.java)
                                //startActivity(intent6)
                                if(userrole == "parent"){
                                    val intent6 = Intent(this, ParentMainScreen::class.java)
                                    startActivity(intent6)
                                    finish()
                                }else{
                                    val intent6 = Intent(this, KidMainScreen::class.java)
                                    startActivity(intent6)
                                    finish()
                                }
                            }

                    }else{
                        Toast.makeText(this, getResources().getString(R.string.groupdidntexist),Toast.LENGTH_SHORT).show()
                        val intent3 = Intent(this, joingroup::class.java)
                        startActivity(intent3)
                        finish()
                        return@addOnSuccessListener
                    }
                }
                .addOnFailureListener{
                    Toast.makeText(this, getResources().getString(R.string.tryagainlater),Toast.LENGTH_SHORT).show()
                }


        }

    }
}