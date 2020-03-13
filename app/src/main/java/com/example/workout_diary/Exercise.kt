package com.example.workout_diary

data class Exercise(
    var title: String? = "",
    var description: String? = "",
    var category: String? = ""
){
    override fun toString() = title.toString()
}