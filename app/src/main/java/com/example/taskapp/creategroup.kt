package com.example.taskapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.tasksapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_creategroup.*
import kotlinx.android.synthetic.main.activity_joingroup.*
import kotlinx.android.synthetic.main.activity_login_screen.*
import kotlinx.coroutines.awaitAll

class creategroup : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creategroup)

        auth = Firebase.auth

        val db = Firebase.firestore

        val userID = auth.currentUser?.uid




        textView2233.setOnClickListener{
            val intent = Intent(this, whydoineedagroup::class.java)
            intent.putExtra("from", "creategroup")
            startActivity(intent)
            finish()
        }

        createbutton.setOnClickListener{
            val groupname = newgroupname.text.toString()
            if(groupname.isEmpty()){
                Toast.makeText(this,getResources().getString(R.string.groupnameisrequired), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            db.collection("groups").document(groupname)
                .get()
                .addOnSuccessListener {
                    val doc = it.getString("user8")
                    //Log.d("user8", doc)

                    if(doc != null){
                        Toast.makeText(this,getResources().getString(R.string.groupnameexists), Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, makeorjoingroup::class.java)
                        startActivity(intent)
                        finish()
                        return@addOnSuccessListener

                    }
                    db.collection("users").document(userID!!)
                        .get()
                        .addOnSuccessListener { result ->

                            val username = result.getString("name")!!
                            /*if(username.isEmpty()){
                                Toast.makeText(this, getResources().getString(R.string.clickagaintoconfirm), Toast.LENGTH_SHORT).show()
                                return@setOnClickListener
                            }*/
                            progressBar3.visibility = View.VISIBLE
                            db.collection("users").document(userID)
                                .update("group", groupname)
                                .addOnSuccessListener {
                                    //Toast.makeText(this,groupname, Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener { exception ->
                                    Toast.makeText(this,getResources().getString(R.string.tryagainlater), Toast.LENGTH_SHORT).show()
                                    progressBar3.visibility = View.INVISIBLE
                                    return@addOnFailureListener
                                }


                            var userinfos = hashMapOf(
                                "name" to username,
                                "role" to "parent",
                                "points" to "0",
                                "id" to userID,
                                "currenttask" to "none"
                            )
                            val useringroup = hashMapOf(
                                "user1" to userinfos,
                                "user2" to "notdefined",
                                "user3" to "notdefined",
                                "user4" to "notdefined",
                                "user5" to "notdefined",
                                "user6" to "notdefined",
                                "user7" to "notdefined",
                                "user8" to "notdefined"
                            )
                            gruppe = groupname
                            db.collection("groups").document(groupname)
                                .set(useringroup)
                                .addOnSuccessListener {
                                    val task = hashMapOf(
                                        "discriptions" to descriptions1,
                                        "points" to punkte1,
                                        "deadlines" to deadlines1,
                                        "titles" to titles1,
                                        "pictures" to pictures1,
                                        "users" to users1,
                                        "kidpicture" to kidpictures1,
                                        "isworkedon" to isworkedones1,
                                        "isdone" to indones1,
                                        "donebads" to isdonebads1,
                                        "isfinisheds" to isfinisheds1


                                    )
                                    try {
                                        db.collection("tasks").document(groupname)
                                            .set(task)
                                            .addOnSuccessListener {
                                                Toast.makeText(this,getResources().getString(R.string.successfullyaddedandjoined), Toast.LENGTH_SHORT).show()
                                            }
                                            .addOnFailureListener{

                                                Toast.makeText(this,getResources().getString(R.string.tryagainlater), Toast.LENGTH_SHORT).show()
                                            }
                                    }
                                    catch (exeption: Throwable){
                                        Toast.makeText(this,getResources().getString(R.string.tryagainlater), Toast.LENGTH_SHORT).show()
                                    }


                                }
                                .addOnFailureListener{
                                    Toast.makeText(this,getResources().getString(R.string.tryagainlater), Toast.LENGTH_SHORT).show()
                                    progressBar3.visibility = View.INVISIBLE
                                    return@addOnFailureListener
                                }
                            val intent4 = Intent(this, ParentMainScreen::class.java)
                            startActivity(intent4)
                            finish()
                        }
                        .addOnFailureListener { exception ->
                            Toast.makeText(this,"Could't fetch username!", Toast.LENGTH_SHORT).show()
                            return@addOnFailureListener
                        }

                }
        }




    }
}