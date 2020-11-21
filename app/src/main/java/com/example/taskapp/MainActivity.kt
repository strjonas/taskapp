
package com.example.tasksapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.taskapp.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()


        auth = Firebase.auth




        val currentUser = auth.currentUser

        if (currentUser != null) {
            val userID = auth.currentUser?.uid
            val db = Firebase.firestore
            db.collection("users").document(userID!!)
                .get()
                .addOnSuccessListener { result ->

                    val art = result.getString("memberart")
                    username = result.getString("name")!!
                    gruppe = result.getString("group")!!


                    currenttask = result.getString("currenttask")!!
                    if(art == "child"){
                        currentdescription = result.getString("currentdescription")!!
                    }
                    val groupright = result.getString("group")!!


                    if(groupright != "emptygroup") {
                        db.collection("tasks").document(groupright)
                            .get()
                            .addOnSuccessListener {
                                deadlines = it.get("deadlines") as MutableList<String>
                                descriptions = it.get("discriptions") as MutableList<String>
                                punkte = it.get("points") as MutableList<Int>
                                pictures = it.get("pictures") as MutableList<String>
                                users = it.get("users") as MutableList<String>
                                indones = it.get("isdone") as MutableList<Boolean>
                                isdonebads = it.get("donebads") as MutableList<Boolean>
                                isworkedones = it.get("isworkedon") as MutableList<Boolean>
                                kidpictures = it.get("kidpicture") as MutableList<String>
                                titles = it.get("titles") as MutableList<String>
                                isfinisheds = it.get("isfinisheds") as MutableList<Boolean>

                            }
                        if (art == "parent") {
                            rolle = "parent"
                            val intent = Intent(this, ParentMainScreen::class.java)
                            startActivity(intent)
                            finish()
                        }
                        if (art == "child") {
                            rolle = "child"
                            val intent1 = Intent(this, KidMainScreen::class.java)
                            startActivity(intent1)
                            finish()
                        }
                    }
                    else{
                            if (art == "parent") {
                                val intent = Intent(this, makeorjoingroup::class.java)
                                startActivity(intent)
                                finish()
                            }
                            if (art == "child") {
                                val intent1 = Intent(this, joingroup::class.java)
                                startActivity(intent1)
                                finish()
                            }
                        }
                }
            KidMainScreen().getkidpoints()
            //R.id.mypoints
        }else {
            val intent = Intent(this, LoginScreen::class.java)
            startActivity(intent)
            finish()
        }


    }
}

