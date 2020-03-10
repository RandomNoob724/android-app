package com.example.workout_diary

data class Workout(
    val id: Int = 0,
    var category: String? = "",
    var exercises: MutableList<Exercise> = mutableListOf<Exercise>()
){
    override fun toString(): String = category + ": " + exercises
}