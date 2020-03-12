package com.example.workout_diary

val exerciseRepository = ExerciseRepository().apply {
    addExercise(
        title = "Bench press",
        description = "Just lay down and push the bar up",
        category = "arm"
    )
}

class ExerciseRepository {
    var exercises = mutableListOf<Exercise>()

    fun addExercise(title: String, description: String,category:String): String {

        exercises.add(
            Exercise(
                title,
                description,
                category
            )
        )
        return title
    }

    fun getAllExercises() = exercises

    fun getExerciseByTitle(title: String) =
        exercises.find{
            it.title == title
        }


    fun deleteExerciseByTitle(title: String) =
        exercises.remove(
            exercises.find {
                it.title == title
            }
        )
}