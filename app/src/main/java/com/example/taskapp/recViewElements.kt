package com.example.taskapp

import com.example.tasksapp.R

data class Model(
    val punkt: Int,
    val title: String,
    val deadline: String,
    val description: String,
    val picture: String,
    val user: String,
    val kidpicture: String,
    val isworkedon: Boolean,
    val isdone: Boolean,
    val isdonebad: Boolean,
    val isfinished: Boolean
)

var kidpoints: Int = 0

var punkte = mutableListOf(3)
var titles = mutableListOf("exampletask")
var deadlines = mutableListOf("hello")
var descriptions = mutableListOf("hello")
var pictures = mutableListOf("https://firebasestorage.googleapis.com/v0/b/taskapp-28a22.appspot.com/o/pics%2F1603744814976?alt=media&token=c731dfb1-9b98-42c5-a559-987b5399646b")
var users = mutableListOf("hello")
var isworkedones = mutableListOf(false)
var indones = mutableListOf(false)
var isdonebads = mutableListOf(false)
var kidpictures = mutableListOf("hallo")
var isfinisheds = mutableListOf(false)

var currenttask = "none"
var currentdescription = "none"

var punkte1 = mutableListOf(3)
var titles1 = mutableListOf("exampletask")
var deadlines1 = mutableListOf("hello")
var descriptions1 = mutableListOf("hello")
var pictures1 = mutableListOf("https://firebasestorage.googleapis.com/v0/b/taskapp-28a22.appspot.com/o/pics%2F1603744814976?alt=media&token=c731dfb1-9b98-42c5-a559-987b5399646b")
var users1 = mutableListOf("hello")
var isworkedones1 = mutableListOf(true)
var indones1 = mutableListOf(true)
var isdonebads1 = mutableListOf(true)
var kidpictures1 = mutableListOf("hallo")
var isfinisheds1 = mutableListOf(true)

