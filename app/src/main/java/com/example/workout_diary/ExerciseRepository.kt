package com.example.workout_diary

val exerciseRepository = ExerciseRepository().apply {

}

class ExerciseRepository {
    var exercises = mutableListOf<Exercise>()

    fun getExerciseByTitle(title: String) =
        exercises.find {
            it.title == title
        }

    fun getExerciseById(id: String) =
        exercises.find {
            it.id == id
        }

}