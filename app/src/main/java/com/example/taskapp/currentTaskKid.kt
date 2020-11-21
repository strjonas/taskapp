package com.example.taskapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.tasksapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_current_task_kid.*
import kotlinx.android.synthetic.main.activity_new_task.*
import kotlinx.android.synthetic.main.activity_task_accept_kids.*
import java.io.ByteArrayOutputStream
import java.util.*

private const val REQUEST_CODE = 43

class currentTaskKid : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var imageUri: Uri
    var kidpicture = "hallo"
    private lateinit var hallo: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_task_kid)
        actionBar?.title = resources.getString(R.string.current_task)

        val db = Firebase.firestore
        auth = Firebase.auth


        val userID = auth.currentUser?.uid

        db.collection("users").document(userID!!)
            .get()
            .addOnSuccessListener {
                hallo =it.getString("currentdescritption")!!
                for(i in 1..pictures.size){
                    if(pictures[i-1] == hallo){
                        val calendar= Calendar.getInstance(TimeZone.getDefault())
                        var year = calendar.get(Calendar.YEAR)
                        var month = calendar.get(Calendar.MONTH)+1
                        var day = calendar.get(Calendar.DAY_OF_MONTH)
                        if(day/10 < 1){
                            if(month/10 < 1){

                                var s = year.toString() + "/" + "0" + month.toString() + "/" +"0"+ day.toString()
                                if(deadlines[i-1].replace("/", "").compareTo(s) <0) {
                                    Toast.makeText(
                                        this,
                                        resources.getString(R.string.deadlineover),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    db.collection("users").document(userID!!)
                                        .update("currenttask", "none")
                                    db.collection("users").document(userID!!)
                                        .update("currentdescritption", "none")
                                    currenttask = "none"
                                    currentdescription = "none"
                                    val intent = Intent(this, KidMainScreen::class.java)
                                    startActivity(intent)
                                    finish()
                                    hallo = "none"
                                    true
                                }
                            }else{

                                var s = year.toString() + "/" + month.toString() + "/" +"0"+ day.toString()
                                if(deadlines[i-1].replace("/", "").compareTo(s) <0){
                                    Toast.makeText(this,resources.getString(R.string.deadlineover),Toast.LENGTH_SHORT).show()
                                    db.collection("users").document(userID!!)
                                        .update("currenttask", "none")
                                    db.collection("users").document(userID!!)
                                        .update("currentdescritption", "none")
                                    currenttask = "none"
                                    currentdescription = "none"
                                    val intent = Intent(this, KidMainScreen::class.java)
                                    startActivity(intent)
                                    finish()
                                    hallo = "none"
                                    true
                                }

                            }

                        }else {
                                if(month/10 < 1){

                                    var s = year.toString() + "/" + "0" + month.toString() + "/"+ day.toString()
                                    if(deadlines[i-1].replace("/", "").compareTo(s) <0) {
                                        Toast.makeText(
                                            this,
                                            resources.getString(R.string.deadlineover),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        db.collection("users").document(userID!!)
                                            .update("currenttask", "none")
                                        db.collection("users").document(userID!!)
                                            .update("currentdescritption", "none")
                                        currenttask = "none"
                                        currentdescription = "none"
                                        val intent = Intent(this, KidMainScreen::class.java)
                                        startActivity(intent)
                                        finish()
                                        hallo = "none"
                                        true
                                    }
                                }else{

                                        var s = year.toString() + "/" + month.toString() + "/" + day.toString()
                                        if(deadlines[i-1].replace("/", "").compareTo(s) <0){
                                            Toast.makeText(this,resources.getString(R.string.deadlineover),Toast.LENGTH_SHORT).show()
                                            db.collection("users").document(userID!!)
                                                .update("currenttask", "none")
                                            db.collection("users").document(userID!!)
                                                .update("currentdescritption", "none")
                                            currenttask = "none"
                                            currentdescription = "none"
                                            val intent = Intent(this, KidMainScreen::class.java)
                                            startActivity(intent)
                                            finish()
                                            hallo = "none"
                                            true
                                        }

                                    }
                        }
                        if(day/10 < 1){
                            var s = year.toString() + "/" + month.toString() +"0"+ day.toString()
                            if(deadlines[i-1].replace("/", "").compareTo(s) <0){
                                Toast.makeText(this,resources.getString(R.string.deadlineover),Toast.LENGTH_SHORT).show()
                                db.collection("users").document(userID!!)
                                    .update("currenttask", "none")
                                db.collection("users").document(userID!!)
                                    .update("currentdescritption", "none")
                                currenttask = "none"
                                currentdescription = "none"
                                val intent = Intent(this, KidMainScreen::class.java)
                                startActivity(intent)
                                finish()
                                hallo = "none"
                                return@addOnSuccessListener
                            }
                        }else{
                            var s = year.toString() + month.toString() + day.toString()
                            if(deadlines[i-1].replace("/", "").compareTo(s) < 0){
                                Toast.makeText(this,resources.getString(R.string.deadlineover),Toast.LENGTH_SHORT).show()
                                db.collection("users").document(userID!!)
                                    .update("currenttask", "none")
                                db.collection("users").document(userID!!)
                                    .update("currentdescritption", "none")
                                currenttask = "none"
                                currentdescription = "none"
                                hallo = "none"
                                return@addOnSuccessListener
                            }
                        }
                        currenttask_description.text = descriptions[i-1]
                        var ausgabe = ""
                        var a = deadlines[i-1].split("/")
                        ausgabe = ausgabe + a[2] + " "
                        when (a[1]){
                            "1" -> ausgabe = ausgabe + "January " + ", ${a[0]}"
                            "2" -> ausgabe = ausgabe + "February" + ", ${a[0]}"
                            "3" -> ausgabe = ausgabe + "March" + ", ${a[0]}"
                            "4" -> ausgabe = ausgabe + "April " + ", ${a[0]}"
                            "5" -> ausgabe = ausgabe + "May " + ", ${a[0]}"
                            "6" -> ausgabe = ausgabe + "June " + ", ${a[0]}"
                            "7" -> ausgabe = ausgabe + "July " + ", ${a[0]}"
                            "8" -> ausgabe = ausgabe + "August " + ", ${a[0]}"
                            "9" -> ausgabe = ausgabe + "September" + ", ${a[0]}"
                            "10" -> ausgabe = ausgabe + "October" + ", ${a[0]}"
                            "11" -> ausgabe = ausgabe + "November" + ", ${a[0]}"
                            "12" -> ausgabe = ausgabe + "Dezember" + ", ${a[0]}"
                        }
                        textView24.text = ausgabe
                    }
                }
                if(hallo != "hello"){
                    Glide.with(this)
                        .load(hallo)
                        .into(currenttask_imgage)
                }
            }


        currenttask_handinimage.setOnClickListener{

            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent, REQUEST_CODE)
            }catch (exeption:Throwable){
                Toast.makeText(this,resources.getString(R.string.tryagainlater), Toast.LENGTH_SHORT).show()
            }
            textView29.visibility = View.INVISIBLE


        }

        currenttask_handinbtn.setOnClickListener{
            if(kidpicture == "hallo"){
                Toast.makeText(this, resources.getString(R.string.allfieldsarerequired),Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            try {
                for(i in 1..pictures.size){
                    if(pictures[i-1] == hallo){
                        indones[i-1] = true
                        isworkedones[i-1] = false
                        kidpictures[i-1] = kidpicture

                    }
                }

                db.collection("users").document(userID!!)
                    .update("currenttask", "none")
                db.collection("users").document(userID!!)
                    .update("currentdescritption", "none")
                currenttask = "none"
                currentdescription = "none"
                hallo = "none"
                db.collection("tasks").document(gruppe)
                    .update("isdone", indones)
                    .addOnSuccessListener {
                        db.collection("tasks").document(gruppe)
                            .update("isworkedon", isworkedones)
                        db.collection("tasks").document(gruppe)
                            .update("kidpicture", kidpictures)
                        Toast.makeText(this,getResources().getString(R.string.successfullyhandedintask), Toast.LENGTH_SHORT).show()
                        val inte = Intent(this, KidMainScreen::class.java)
                        startActivity(inte)
                        finish()
                    }
                    .addOnFailureListener{
                        Toast.makeText(this,getResources().getString(R.string.tryagainlater), Toast.LENGTH_SHORT).show()
                        val inte = Intent(this, KidMainScreen::class.java)
                        startActivity(inte)
                        finish()
                    }
            }catch (exeption: Throwable){
                Toast.makeText(this,getResources().getString(R.string.tryagainlater), Toast.LENGTH_SHORT).show()

            }

        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            val takenimage = data?.extras?.get("data") as Bitmap

            currenttask_handinimage.setImageBitmap(takenimage)
            uploadImageAndSaveUri(takenimage)



        }else{
            super.onActivityResult(requestCode, resultCode, data)
        }

    }
    private fun uploadImageAndSaveUri(bitmap: Bitmap) {
        val currentTimestamp = System.currentTimeMillis().toString()
        val baos = ByteArrayOutputStream()
        val storageRef = FirebaseStorage.getInstance()
            .reference
            .child("pics/${currentTimestamp}")
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val image = baos.toByteArray()

        val upload = storageRef.putBytes(image)

        //progressbar_pic.visibility = View.VISIBLE
        upload.addOnCompleteListener { uploadTask ->
            //progressbar_pic.visibility = View.INVISIBLE

            if (uploadTask.isSuccessful) {
                storageRef.downloadUrl.addOnCompleteListener { urlTask ->
                    urlTask.result?.let {
                        imageUri = it
                        kidpicture = imageUri.toString()


                    }
                }
            }

        }

    }
}