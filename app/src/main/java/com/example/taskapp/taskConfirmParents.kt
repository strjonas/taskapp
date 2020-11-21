package com.example.taskapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.tasksapp.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_task_accept_kids.imageView
import kotlinx.android.synthetic.main.activity_task_confirm_parents.*

class taskConfirmParents : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_confirm_parents)

        val db = Firebase.firestore

        val uistring = getIntent().getStringExtra("kidpicture")
        if(uistring != "hello"){
            Glide.with(this)
                .load(uistring)
                .into(imageView)

        }
        val uistring1 = getIntent().getStringExtra("parentpicture")
        if(uistring1 != "hello"){
            Glide.with(this)
                .load(uistring1)
                .into(imageView5)

        }

        findViewById<Button>(R.id.detail_accept).setOnClickListener{
            try {
                indones[getIntent().getIntExtra("position",999)] = false
                isfinisheds[getIntent().getIntExtra("position",999)] = true
                isworkedones[getIntent().getIntExtra("position",999)] = false
                val user = users[getIntent().getIntExtra("position",999)]

                db.collection("groups").document(gruppe)
                    .get()
                    .addOnSuccessListener {
                        var hasmap = it.get("user1") as HashMap<String, String>
                        if (hasmap.get("name")!! == user) {
                            var points = hasmap.get("points").toString().toInt()
                            points += punkte[getIntent().getIntExtra("position", 999)]
                            hasmap.set("points", points.toString())
                            db.collection("groups").document(gruppe)
                                .update("user1", hasmap)
                            return@addOnSuccessListener
                        }


                        if (it.get("user2") != "notdefined") {
                            hasmap = it.get("user2") as HashMap<String, String>
                            if (hasmap.get("name")!! == user) {
                                var zwischen = hasmap.get("points")!!
                                var points = zwischen.toInt()
                                points += punkte[getIntent().getIntExtra("position", 999)]
                                hasmap.set("points", points.toString())
                                db.collection("groups").document(gruppe)
                                    .update("user2", hasmap)
                                return@addOnSuccessListener
                            }

                        }




                        if (it.get("user3") != "notdefined") {
                            hasmap = it.get("user3") as HashMap<String, String>
                            if (hasmap.get("name")!! == user) {
                                var zwischen = hasmap.get("points")!!
                                var points = zwischen.toInt()
                                points += punkte[getIntent().getIntExtra("position", 999)]
                                hasmap.set("points", points.toString())
                                db.collection("groups").document(gruppe)
                                    .update("user3", hasmap)
                                return@addOnSuccessListener
                            }

                        }

                        if (it.get("user4") != "notdefined") {
                            hasmap = it.get("user4") as HashMap<String, String>
                            if (hasmap.get("name")!! == user) {
                                var zwischen = hasmap.get("points")!!
                                var points = zwischen.toInt()
                                points += punkte[getIntent().getIntExtra("position", 999)]
                                hasmap.set("points", points.toString())
                                db.collection("groups").document(gruppe)
                                    .update("user4", hasmap)
                                return@addOnSuccessListener
                            }

                        }

                        if (it.get("user5") != "notdefined") {
                            hasmap = it.get("user5") as HashMap<String, String>
                            if (hasmap.get("name")!! == user) {
                                var points = hasmap.get("points").toString().toInt()
                                points += punkte[getIntent().getIntExtra("position", 999)]
                                hasmap.set("points", points.toString())
                                db.collection("groups").document(gruppe)
                                    .update("user5", hasmap)
                                return@addOnSuccessListener
                            }

                        }

                        if (it.get("user6") != "notdefined") {
                            hasmap = it.get("user6") as HashMap<String, String>
                            if (hasmap.get("name")!! == user) {
                                var points = hasmap.get("points").toString().toInt()
                                points += punkte[getIntent().getIntExtra("position", 999)]
                                hasmap.set("points", points.toString())
                                db.collection("groups").document(gruppe)
                                    .update("user6", hasmap)
                                return@addOnSuccessListener
                            }

                        }

                        if (it.get("user7") != "notdefined") {
                            hasmap = it.get("user7") as HashMap<String, String>
                            if (hasmap.get("name")!! == user) {
                                var points = hasmap.get("points").toString().toInt()
                                points += punkte[getIntent().getIntExtra("position", 999)]
                                hasmap.set("points", points.toString())
                                db.collection("groups").document(gruppe)
                                    .update("user7", hasmap)
                                return@addOnSuccessListener
                            }

                        }

                        if (it.get("user8") != "notdefined") {
                            hasmap = it.get("user8") as HashMap<String, String>
                            if (hasmap.get("name")!! == user) {
                                var points = hasmap.get("points").toString().toInt()
                                points += punkte[getIntent().getIntExtra("position", 999)]
                                hasmap.set("points", points.toString())
                                db.collection("groups").document(gruppe)
                                    .update("user8", hasmap)
                                return@addOnSuccessListener
                            }

                        }
                    }
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
                        Toast.makeText(this,"Successfully accepted Task!", Toast.LENGTH_SHORT).show()
                        val inte = Intent(this, ParentMainScreen::class.java)
                        startActivity(inte)
                        finish()
                    }
                    .addOnFailureListener{
                        Toast.makeText(this,"Something went wrong! Try again later!", Toast.LENGTH_SHORT).show()
                        val inte = Intent(this, ParentMainScreen::class.java)
                        startActivity(inte)
                        finish()
                    }
            }catch (exeption: Throwable){
                Toast.makeText(this, "Try again later $exeption", Toast.LENGTH_SHORT).show()
            }


            val intent = Intent(this, ParentMainScreen::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<Button>(R.id.detail_reject).setOnClickListener{
            try {
                isdonebads[getIntent().getIntExtra("position",999)] = true
                isworkedones[getIntent().getIntExtra("position",999)] = false
               // users[getIntent().getIntExtra("position",999)] = "$username"
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
                    "deadlines" to deadlines

                )
                db.collection("tasks").document(gruppe)
                    .set(task)
                    .addOnSuccessListener {
                        Toast.makeText(this,"Successfully rejected Task!", Toast.LENGTH_SHORT).show()
                        val inte = Intent(this, ParentMainScreen::class.java)
                        startActivity(inte)
                        finish()
                    }
                    .addOnFailureListener{
                        Toast.makeText(this,"Something went wrong! Try again later!", Toast.LENGTH_SHORT).show()
                        val inte = Intent(this, ParentMainScreen::class.java)
                        startActivity(inte)
                        finish()
                    }
            }catch (exeption: Throwable){
                Toast.makeText(this, "Try again later $exeption", Toast.LENGTH_SHORT).show()
            }


            val intent = Intent(this, ParentMainScreen::class.java)
            startActivity(intent)
            finish()
        }
    }
}