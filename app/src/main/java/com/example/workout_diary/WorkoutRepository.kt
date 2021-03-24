package com.example.workout_diary

val workoutRepository = WorkoutRepository().apply {

}

class WorkoutRepository {
    var workouts = mutableListOf<Workout>()


    fun getWorkoutById(id: Int) =
        workouts.find{
            it.id == id
        }
}