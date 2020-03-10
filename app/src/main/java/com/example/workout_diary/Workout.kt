package com.example.workout_diary

data class Workout(
    val id: Int,
    var title: String,
    var description: String,
    var listOfExercises: MutableList<Exercise> = mutableListOf<Exercise>()
){
    override fun toString(): String = title
}