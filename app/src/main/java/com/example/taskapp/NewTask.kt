package com.example.taskapp

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.tasksapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_new_task.*
import java.io.ByteArrayOutputStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

private const val REQUEST_CODE = 42

class NewTask : AppCompatActivity(),DatePickerDialog.OnDateSetListener
    { var day = 0
    var month: Int = 0
    var year: Int = 0
    var myDay = 0
    var myMonth: Int = 0
    var myYear: Int = 0
        var datee: String = ""
    private lateinit var auth: FirebaseAuth
    private lateinit var imageUri: Uri
    val db = Firebase.firestore
    var picture = "hallo"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_task)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(resources.getString(R.string.new_task))
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val position = getIntent().getIntExtra("position", 9999)
        if(position != 9999){
            newtaskdeadline.text = deadlines[position]
            newtaskdescription.setText(descriptions[position])
            newtaskname.setText(titles[position])
            newtaskpoints.setText(punkte[position].toString())
            picture = pictures[position]
            Glide.with(this)
                .load(pictures[position])
                .into(imageView2)
            try{

                Adapter(ParentMainScreen(), ParentMainScreen().fetchList(), ParentMainScreen()).deletetask(
                    position
                )

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
                    }
                    .addOnFailureListener{

                    }

            }catch (t: Throwable){

                Toast.makeText(
                    this,
                    resources.getString(R.string.tryagainlater),
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
        newtaskdeadline.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            day = calendar.get(Calendar.DAY_OF_MONTH)
            month = calendar.get(Calendar.MONTH)
            year = calendar.get(Calendar.YEAR)
            val datePickerDialog =
                DatePickerDialog(this@NewTask, R.style.DialogTheme, this@NewTask, year, month, day)
            datePickerDialog.show()

        }


        imageView2.setOnClickListener{
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent, REQUEST_CODE)
            }catch (exeption: Throwable){
                Toast.makeText(
                    this,
                    resources.getString(R.string.tryagainlater),
                    Toast.LENGTH_SHORT
                ).show()
            }
            textView26.visibility = View.INVISIBLE
        }
        buttonaddtask.setOnClickListener{
            if(myDay/10 < 1){
                if(myMonth/10 < 1){

                    datee =  myYear.toString() + "/" + "0"+ myMonth.toString() + "/" + "0" + myDay.toString()

                }else{

                    datee =  myYear.toString() + "/" + myMonth.toString() + "/" + "0" + myDay.toString()

                }

            }else {
                if(myMonth/10 < 1){

                    datee =  myYear.toString() + "/" + "0"+ myMonth.toString() + "/" +  myDay.toString()

                }else{

                    datee =  myYear.toString() + "/" + myMonth.toString() + "/" + myDay.toString()

                }
            }

            if(datee != "" && newtaskdescription.text.toString().isNotEmpty() && newtaskname.text.toString().isNotEmpty() && newtaskpoints.text.toString().isNotEmpty() && picture != "hallo"){

                try{

                    Adapter(ParentMainScreen(), ParentMainScreen().fetchList(), ParentMainScreen()).updatetasks(
                        newtaskpoints.text.toString().toInt(),
                        newtaskname.text.toString(),
                        datee,
                        newtaskdescription.text.toString(),
                        picture,
                        "hallo",
                        "hallo",
                        false,
                        false,
                        false,
                        false
                    )

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
                            Toast.makeText(
                                this,
                                resources.getString(R.string.succesfullyaddedtask),
                                Toast.LENGTH_SHORT
                            ).show()
                            val inte = Intent(this, ParentMainScreen::class.java)
                            startActivity(inte)
                            finish()
                        }
                        .addOnFailureListener{
                            Toast.makeText(
                                this,
                                resources.getString(R.string.tryagainlater),
                                Toast.LENGTH_SHORT
                            ).show()
                            val inte = Intent(this, ParentMainScreen::class.java)
                            startActivity(inte)
                            finish()
                        }

                }catch (t: Throwable){
                    Toast.makeText(
                        this,
                        resources.getString(R.string.tryagainlater),
                        Toast.LENGTH_SHORT
                    ).show()
                }


            }else{
                Toast.makeText(
                    this,
                    resources.getString(R.string.allfieldsarerequired),
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
        textView3.setOnClickListener{
            val intent = Intent(this, howdoesitwork::class.java)
            intent.putExtra("from", "newtask")
            startActivity(intent)
        }

    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        myDay = dayOfMonth
        myYear = year
        myMonth = month+1

        if(myDay/10 < 1){
            if(myMonth/10 < 1){
                newtaskdeadline.visibility = View.INVISIBLE
                datee =  myYear.toString() + "/" + "0"+ myMonth.toString() + "/" + "0" + myDay.toString()

            }else{
            newtaskdeadline.visibility = View.INVISIBLE
            datee =  myYear.toString() + "/" + myMonth.toString() + "/" + "0" + myDay.toString()

            }

        }else {
            if(myMonth/10 < 1){
                newtaskdeadline.visibility = View.INVISIBLE
                datee =  myYear.toString() + "/" + "0"+ myMonth.toString() + "/" +  myDay.toString()

            }else{
                newtaskdeadline.visibility = View.INVISIBLE
                datee =  myYear.toString() + "/" + myMonth.toString() + "/" + myDay.toString()

            }
        }
        var ausgabe = ""
        var a = datee.split("/")
        ausgabe = ausgabe + a[2] + " "
        when (a[1]){
            "01" -> ausgabe = ausgabe + "January" + ", ${a[0]}"
            "02" -> ausgabe = ausgabe + "February" + ", ${a[0]}"
            "03" -> ausgabe = ausgabe + "March" + ", ${a[0]}"
            "04" -> ausgabe = ausgabe + "April" + ", ${a[0]}"
            "05" -> ausgabe = ausgabe + "May" + ", ${a[0]}"
            "06" -> ausgabe = ausgabe + "June" + ", ${a[0]}"
            "07" -> ausgabe = ausgabe + "July" + ", ${a[0]}"
            "08" -> ausgabe = ausgabe + "August" + ", ${a[0]}"
            "09" -> ausgabe = ausgabe + "September" + ", ${a[0]}"
            "10" -> ausgabe = ausgabe + "October" + ", ${a[0]}"
            "11" -> ausgabe = ausgabe + "November" + ", ${a[0]}"
            "12" -> ausgabe = ausgabe + "December" + ", ${a[0]}"
        }
        textView11.text = ausgabe

        newtaskdeadline.visibility = View.INVISIBLE
        textView11.bringToFront()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            val takenimage = data?.extras?.get("data") as Bitmap
            imageView2.setImageBitmap(takenimage)
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
                        picture = imageUri.toString()


                    }
                }
            }

        }

    }

        override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {

            R.id.home -> {
                Toast.makeText(
                    this,
                    "Do you really want to go back? The task wont be saved!Please click BACK again to exit",
                    Toast.LENGTH_SHORT
                ).show()
                this.finish()

                true
            }

            else -> {
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.

                super.onOptionsItemSelected(item)
            }
        }
}
