package com.example.taskapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.taskapp.deletetask
import com.example.tasksapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_deletetask.*
import kotlinx.android.synthetic.main.activity_new_task.*
import kotlin.Result.Companion.success

class deletetask : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    val db = Firebase.firestore
    var picture = "hallo"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deletetask)

        closescreen.setOnClickListener{
            //zu einem Screen wo man die Aufgabe bearbeiten kann, aber nur deadline und so maybe
            val position = getIntent().getIntExtra("position", 999)
            val intent = Intent(this, NewTask::class.java)
            intent.putExtra("position", position)
            startActivity(intent)
            finish()

        }

        deletetask.setOnClickListener{
            val position = getIntent().getIntExtra("position", 999)
            try{

                Adapter(ParentMainScreen(), ParentMainScreen().fetchList(), ParentMainScreen()).deletetask(position)

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
                        Toast.makeText(this,resources.getString(R.string.success), Toast.LENGTH_SHORT).show()
                        val inte = Intent(this, ParentMainScreen::class.java)
                        startActivity(inte)
                        finish()
                    }
                    .addOnFailureListener{
                        Toast.makeText(this,resources.getString(R.string.tryagainlater), Toast.LENGTH_SHORT).show()
                        val inte = Intent(this, ParentMainScreen::class.java)
                        startActivity(inte)
                        finish()
                    }

            }catch(t: Throwable){

                Toast.makeText(this,resources.getString(R.string.tryagainlater), Toast.LENGTH_SHORT).show()
            }
        }
    }
}