package com.example.workout_diary

val exerciseRepository = ExerciseRepository().apply {

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

    fun getExerciseById(id: String) =
        exercises.find{
            it.id == id
        }


    fun deleteExerciseByTitle(title: String) =
        exercises.remove(
            exercises.find {
                it.title == title
            }
        )
}