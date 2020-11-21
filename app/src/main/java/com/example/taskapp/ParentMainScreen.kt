package com.example.taskapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tasksapp.MainActivity
import com.example.tasksapp.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ParentMainScreen : AppCompatActivity(), CellClickListenerNew {

    val db = Firebase.firestore
    private val CHANNEL_ID = "testchannel_01"
    private  val notificationId = 101


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent_main_screen)
        setSupportActionBar(findViewById(R.id.toolbar1))

        rolle = "parent"


        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = Adapter(this, fetchList(), this)



        val actionBar = supportActionBar
        actionBar!!.title = getResources().getString(R.string.tasks)

        //
        if(!alrexe){
            val intento = Intent(this, MainActivity::class.java)
            startActivity(intento)
            finish()
            alrexe = true
        }



        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->

            val intent = Intent(this,NewTask::class.java)
            startActivity(intent)


        }
    }

    fun fetchList(): ArrayList<Model> {
        val list = arrayListOf<Model>()

        for (i in 1..punkte.size) {
            val model = Model(punkte[i-1],titles[i-1],deadlines[i-1],descriptions[i-1],pictures[i-1],users[i-1],kidpictures[i-1],isworkedones[i-1],indones[i-1],
                isdonebads[i-1], isfinisheds[i-1])
            list.add(model)
        }
        return list
    }

    override fun onCellClickListenerNew(data: Model,position: Int) {
        if(data.isdonebad && data.isfinished && data.isworkedon && data.isdone){
           Toast.makeText(this,resources.getString(R.string.thisisanexample),Toast.LENGTH_SHORT).show()
            return
        }
        else if(data.isfinished){
            Toast.makeText(this,getResources().getString(R.string.alreadydoneby) + data.user + getResources().getString(R.string.andacceptedbyaparent), Toast.LENGTH_SHORT).show()
            return
        }
        else if(data.isworkedon == true){
            Toast.makeText(this,data.user + getResources().getString(R.string.didnthandinyet), Toast.LENGTH_SHORT).show()
            return
        }

        else if(data.isdone){
            val intent = Intent(this, taskConfirmParents::class.java)
            intent.putExtra("image", data.picture)
            intent.putExtra("title", data.title)
            intent.putExtra("punkt",data.punkt.toString())
            intent.putExtra("description", data.description)
            intent.putExtra("deadline", data.deadline)
            intent.putExtra("kidpicture", data.kidpicture)
            intent.putExtra("parentpicture", data.picture)
            intent.putExtra("position", position)
            startActivity(intent)
        }
        else{
            val intent = Intent(this, deletetask::class.java)
            intent.putExtra("position", position)
            startActivity(intent)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {

        R.id.updatetasks -> {
            val intento = Intent(this, MainActivity::class.java)
            startActivity(intento)
            finish()

            true
        }
        R.id.logoutparentmain -> {
            Firebase.auth.signOut()
            val intent = Intent(this, LoginScreen::class.java)
            startActivity(intent)
            finish()
            true
        }
        R.id.viewmyprofileparent ->{
            val intent = Intent(this, viewProfile::class.java)
            startActivity(intent)

            true
        }
        R.id.edidgroupparentmain -> {


            val intent = Intent(this, group_leaderboard::class.java)
            startActivity(intent)

            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

}

