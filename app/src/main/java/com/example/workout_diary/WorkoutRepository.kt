package com.example.workout_diary

val workoutRepository = WorkoutRepository().apply {
    addWorkout(
        title = "push",
        description = "pushworkout",
        listOfExercises = exerciseRepository.getAllExercises()
    )
    addWorkout(
        title = "pull",
        description = "pullworkout",
        listOfExercises = exerciseRepository.getAllExercises()
    )
}

class WorkoutRepository {
    private val workouts = mutableListOf<Workout>()

    fun addWorkout(title: String, description: String, listOfExercises: MutableList<Exercise>): Int {
        val id: Int = when {
            workouts.count() == 0 -> 1
            else -> workouts.last().id+1
        }
        workouts.add(
            Workout(
                id,
                title,
                description,
                listOfExercises
            )
        )
        return id
    }

    fun getAllWorkouts() = workouts

    fun getWorkoutById(id: Int) =
        workouts.find{
            it.id == id
        }

    fun deleteWorkoutById(id: Int) =
        workouts.remove(
            workouts.find {
                it.id == id
            }
        )

    fun updateWorkoutById(id: Int, newTitle: String, newDescription: String, listOfExercises: MutableList<Exercise>) =
        getWorkoutById(id)?.run {
            title = newTitle
            description = newDescription
        }
}