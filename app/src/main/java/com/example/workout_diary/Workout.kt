package com.example.workout_diary

data class Workout(
    var category: String? = "",
    var exercises: MutableList<Exercise> = mutableListOf(),
    var id: Int = 0
){
    override fun toString(): String = category + ": " + exercises
}