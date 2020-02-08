package com.example.workout_diary

val workoutRepository = WorkoutRepository().apply {
    addExercise(
        title = "Bench press",
        description = "Just lay down and push the bar up"
    )
    addExercise(
        title = "Push ups",
        description = "Just lay down and push yourself up from the ground"
    )
}

class WorkoutRepository {
    private val exercises = mutableListOf<Exercise>()

    fun addExercise(title: String, description: String): Int {
        val id: Int = when {
            exercises.count() == 0 -> 1
            else -> exercises.last().id+1
        }
        exercises.add(
            Exercise(
                id,
                title,
                description
            )
        )
        return id
    }

    fun getAllExercises() = exercises

    fun getExerciseById(id: Int) =
        exercises.find{
            it.id == id
        }
    fun deleteExerciseById(id: Int) =
        exercises.remove(
            exercises.find {
                it.id == id
            }
        )

    fun updateExerciseById(id: Int, newTitle: String, newDescription: String) =
        getExerciseById(id)?.run {
            title = newTitle
            description = newDescription
        }
}