package com.example.taskapp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tasksapp.R
import kotlinx.android.synthetic.main.activity_task_accept_kids.*
import java.util.*
import kotlin.collections.ArrayList

class Adapter(private val context: Context,
              private val list: ArrayList<Model>,
              private val cellClickListener: CellClickListenerNew) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    val empty = ""


    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val titletext: TextView = view.findViewById(R.id.titleTV)
        val pictureshown: ImageView = view.findViewById(R.id.imageView4)
        val pictureshownoverr: ImageView = view.findViewById(R.id.imageView6)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cell_view2,parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    fun updatetasks(punkt: Int,title: String,deadline: String, description: String,picture: String,user: String,kidpicture: String, isworkedon: Boolean,isdone: Boolean,isdonebad: Boolean, isfinished: Boolean){

        punkte.add(0, punkt)
        titles.add(0, title)
        deadlines.add(0, deadline)
        descriptions.add(0, description)
        pictures.add(0, picture)
        users.add(0, user)
        isworkedones.add(0, isworkedon)
        indones.add(0, isdone)
        isdonebads.add(0, isdonebad)
        kidpictures.add(0,kidpicture)
        isfinisheds.add(0, isfinished)
        Adapter(ParentMainScreen(), ParentMainScreen().fetchList(), ParentMainScreen()).notifyDataSetChanged()

    }
    fun deletetask(positionn:Int){

        punkte.removeAt(positionn)
        titles.removeAt(positionn)
        deadlines.removeAt(positionn)
        descriptions.removeAt(positionn)
        pictures.removeAt(positionn)
        users.removeAt(positionn)
        isworkedones.removeAt(positionn)
        indones.removeAt(positionn)
        isdonebads.removeAt(positionn)
        kidpictures.removeAt(positionn)
        isfinisheds.removeAt(positionn)
        Adapter(ParentMainScreen(), ParentMainScreen().fetchList(), ParentMainScreen()).notifyDataSetChanged()

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.titletext.text = data.title

            if(data.picture != "hello"){
                Glide.with(context)
                    .load(data.picture)
                    .into(holder.pictureshown)
            }else{
                Glide.with(context)
                    .load("https://firebasestorage.googleapis.com/v0/b/taskapp-28a22.appspot.com/o/pics%2F1603744814976?alt=media&token=c731dfb1-9b98-42c5-a559-987b5399646b")
                    .into(holder.pictureshown)
            }
        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListenerNew(data,position)
        }

        if(data.isdone){

            holder.titletext.text = holder.itemView.resources.getString(R.string.alreadydoneby) + data.user + holder.itemView.resources.getString(R.string.gemacht)

        }
        if(data.isworkedon){
            holder.titletext.text = data.user + holder.itemView.resources.getString(R.string.isalreadyworkingonthistask)

        }

        if(data.isdonebad){
            holder.titletext.text = holder.itemView.resources.getString(R.string.notproperlydoneby) + data.user

        }
        if(data.isdonebad && data.isfinished && data.isworkedon && data.isdone){
            holder.titletext.text = holder.itemView.resources.getString(R.string.thisisanexample)

        }
        val calendar= Calendar.getInstance(TimeZone.getDefault())
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)+1
        var day = calendar.get(Calendar.DAY_OF_MONTH)
        if(day/10 < 1){
            var s = year.toString() + month.toString() +"0"+ day.toString()
            if(data.deadline.replace("/", "").compareTo(s) <0){
                holder.titletext.text = holder.itemView.resources.getString(R.string.deadlineover)
            }
        }else{
            var s = year.toString() + month.toString() + day.toString()
            if(data.deadline.replace("/", "").compareTo(s) < 0){
                holder.titletext.text = holder.itemView.resources.getString(R.string.deadlineover)
            }
        }

        if(data.isfinished){
            holder.pictureshownoverr.visibility = View.VISIBLE
            holder.titletext.text = holder.itemView.resources.getString(R.string.alreadydoneby) + data.user + holder.itemView.resources.getString(R.string.andacceptedbyaparent)

        }


    }
}
interface CellClickListenerNew {
    fun onCellClickListenerNew(data: Model,position: Int)
}