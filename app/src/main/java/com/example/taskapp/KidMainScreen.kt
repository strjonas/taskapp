package com.example.taskapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tasksapp.MainActivity
import com.example.tasksapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*


class KidMainScreen : AppCompatActivity(), CellClickListenerNew {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kid_main_screen)
        setSupportActionBar(findViewById(R.id.toolbar3))

        rolle = "child"

        getkidpoints()

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = Adapter(this, fetchList(), this)

        val actionBar = supportActionBar
        actionBar!!.title = getResources().getString(R.string.tasks)

        if(!alrexek){
            val intento = Intent(this, MainActivity::class.java)
            startActivity(intento)
            finish()
            alrexek = true
        }


    }
    private fun fetchList(): ArrayList<Model> {
        val list = arrayListOf<Model>()

        for (i in 1..punkte.size) {
            val model = Model(
                punkte[i - 1],
                titles[i - 1],
                deadlines[i - 1],
                descriptions[i - 1],
                pictures[i - 1],
                users[i - 1],
                kidpictures[i - 1],
                isworkedones[i - 1],
                indones[i - 1],
                isdonebads[i - 1],
                isfinisheds[i - 1]
            )
            list.add(model)
        }
        return list
    }

    override fun onCellClickListenerNew(data: Model, position: Int) {
        if(data.isdonebad && data.isfinished && data.isworkedon && data.isdone){
            Toast.makeText(this, resources.getString(R.string.thisisanexample), Toast.LENGTH_SHORT).show()
            return
        }
        if(data.isdone){
            Toast.makeText(
                this,
                getResources().getString(R.string.alreadydoneby) + data.user + getResources().getString(
                    R.string.gemacht
                ),
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        if(data.isfinished){
            Toast.makeText(
                this,
                getResources().getString(R.string.alreadydoneby) + data.user + getResources().getString(
                    R.string.andacceptedbyaparent
                ),
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        if(data.isworkedon){
            Toast.makeText(
                this,
                data.user + getResources().getString(R.string.isalreadyworkingonthistask),
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        if(data.isdonebad){
            Toast.makeText(
                this,
                getResources().getString(R.string.notproperlydoneby) + data.user,
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        val calendar= Calendar.getInstance(TimeZone.getDefault())
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)+1
        var day = calendar.get(Calendar.DAY_OF_MONTH)
        if(day/10 < 1){
            var s = year.toString() + month.toString() +"0"+ day.toString()
            if(data.deadline.replace("/", "").compareTo(s) <0){
                Toast.makeText(this, "lol", Toast.LENGTH_SHORT).show()
                return
            }
        }else{
            var s = year.toString() + month.toString() + day.toString()
            if(data.deadline.replace("/", "").compareTo(s) < 0){
                Toast.makeText(this, resources.getString(R.string.deadlineover), Toast.LENGTH_SHORT).show()
                return
            }
        }

        if(rolle == "child"){
            val intent = Intent(this, taskAcceptKids::class.java)
            intent.putExtra("image", data.picture)
            intent.putExtra("title", data.title)
            intent.putExtra("punkt", data.punkt.toString())
            intent.putExtra("description", data.description)
            intent.putExtra("deadline", data.deadline)
            intent.putExtra("position", position)
            startActivity(intent)
        }else{
            val intent = Intent(this, taskConfirmParents::class.java)
            intent.putExtra("image", data.picture)
            intent.putExtra("title", data.title)
            intent.putExtra("punkt", data.punkt.toString())
            intent.putExtra("description", data.description)
            intent.putExtra("deadline", data.deadline)
            intent.putExtra("position", position)
            startActivity(intent)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main3, menu)
        val item = menu.findItem(R.id.mypoints)
        getkidpoints()
        var childpoints = kidpoints
        item.setTitle(resources.getString(R.string.childpoints, childpoints.toString()))
        return true
    }
    fun getkidpoints() {

        auth = Firebase.auth
        val currentUser = auth.currentUser
        val userID = auth.currentUser?.uid
        val db = Firebase.firestore


        db.collection("users").document(userID!!)
            .get()
            .addOnSuccessListener {
                val user = it.getString("name")
                db.collection("groups").document(it.getString("group").toString())
                    .get()
                    .addOnSuccessListener {
                        if (it.get("user1") != "notdefined" && it.get("user1") != null) {
                            var hasmap = it.get("user1") as HashMap<String, String>
                            if (hasmap.get("name")!! == user) {
                                kidpoints = hasmap.get("points").toString().toInt()

                                return@addOnSuccessListener
                            }
                        }


                        if (it.get("user2") != "notdefined" && it.get("user1") != null) {
                            var hasmap = it.get("user2") as HashMap<String, String>
                            if (hasmap.get("name")!! == user) {
                                kidpoints = hasmap.get("points").toString().toInt()

                                return@addOnSuccessListener
                            }

                        }
                        if (it.get("user3") != "notdefined" && it.get("user1") != null) {
                            var hasmap = it.get("user3") as HashMap<String, String>
                            if (hasmap.get("name")!! == user) {
                                kidpoints = hasmap.get("points").toString().toInt()

                                return@addOnSuccessListener
                            }

                        }

                        if (it.get("user4") != "notdefined" && it.get("user1") != null) {
                            var hasmap = it.get("user4") as HashMap<String, String>
                            if (hasmap.get("name")!! == user) {
                                kidpoints = hasmap.get("points").toString().toInt()

                                return@addOnSuccessListener
                            }

                        }

                        if (it.get("user5") != "notdefined" && it.get("user1") != null) {
                            var hasmap = it.get("user5") as HashMap<String, String>
                            if (hasmap.get("name")!! == user) {
                                kidpoints = hasmap.get("points").toString().toInt()

                                return@addOnSuccessListener
                            }

                        }

                        if (it.get("user6") != "notdefined" && it.get("user1") != null) {
                            var hasmap = it.get("user6") as HashMap<String, String>
                            if (hasmap.get("name")!! == user) {
                                kidpoints = hasmap.get("points").toString().toInt()

                                return@addOnSuccessListener
                            }

                        }

                        if (it.get("user7") != "notdefined" && it.get("user1") != null) {
                            var hasmap = it.get("user7") as HashMap<String, String>
                            if (hasmap.get("name")!! == user) {
                                kidpoints = hasmap.get("points").toString().toInt()
                                return@addOnSuccessListener

                            }

                        }

                        if (it.get("user8") != "notdefined" && it.get("user1") != null) {
                            var hasmap = it.get("user8") as HashMap<String, String>
                            if (hasmap.get("name")!! == user) {
                                kidpoints = hasmap.get("points").toString().toInt()

                                return@addOnSuccessListener
                            }

                        }

                    }
            }

    }
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {

        R.id.logoutkidmain -> {
            Firebase.auth.signOut()
            val inte = Intent(this, LoginScreen::class.java)
            startActivity(inte)
            finish()
            true
        }
        R.id.updatetasks -> {
            val intento = Intent(this, MainActivity::class.java)
            startActivity(intento)
            finish()
            true
        }
        R.id.showgroupkidmain -> {

            val inte2 = Intent(this, group_leaderboard::class.java)
            startActivity(inte2)

            true
        }
        R.id.viewmyprofile -> {
            val intent = Intent(this, viewProfile::class.java)
            startActivity(intent)

            true
        }
        R.id.mypoints ->{
            true
        }
        R.id.currenttask_screen -> {
            if (currenttask == "none") {
                val intent = Intent(this, NocurrentTaskKid::class.java)

                startActivity(intent)
            } else {
                val intent = Intent(this, currentTaskKid::class.java)
                startActivity(intent)
            }
            true
        }

        else -> {

            super.onOptionsItemSelected(item)
        }
    }
}