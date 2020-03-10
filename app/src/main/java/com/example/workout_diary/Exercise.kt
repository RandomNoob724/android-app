package com.example.workout_diary

data class Exercise(
    val id: Int,
    var title: String,
    var description: String
){
    override fun toString() = title
}