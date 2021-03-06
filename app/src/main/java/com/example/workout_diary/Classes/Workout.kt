package com.example.workout_diary.Classes

import com.example.workout_diary.Classes.Exercise

data class Workout(
    var category: String? = "Bullshit",
    var exercises: MutableList<Exercise> = mutableListOf(),
    var id: Int = 0
){
    override fun toString(): String = category as String
}