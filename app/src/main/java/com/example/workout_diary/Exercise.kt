package com.example.workout_diary

data class Exercise(
    var title: String? = "",
    var description: String? = "",
    var id: String = "",
    var category: String? = ""
){
    override fun toString() = title.toString()
}