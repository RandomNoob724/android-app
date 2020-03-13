package com.example.workout_diary

val workoutRepository = WorkoutRepository().apply {

}

class WorkoutRepository {
    var workouts = mutableListOf<Workout>()

    fun addWorkout(id: Int,category : String, exercises: MutableList<Exercise>): Int {
        workouts.add(
            Workout(
                category,
                exercises,
                id
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
}