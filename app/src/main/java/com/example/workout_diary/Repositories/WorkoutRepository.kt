package com.example.workout_diary.Repositories

import com.example.workout_diary.FirebaseControllers.Authentication
import com.example.workout_diary.Classes.Workout

val workoutRepository = WorkoutRepository()
    .apply {

}

class WorkoutRepository {
    var workouts = mutableListOf<Workout>()


    fun getWorkoutById(id: Int) =
        workouts.find{
            it.id == id
        }

    fun getWorkoutsFromYourWorkoutsOnDate(currentDate: String) : MutableList<Workout>{
        var workoutList = mutableListOf<Workout>()
        var yourWorkoutList = yourWorkoutRepository.getAllworkoutOnDay(Authentication.instance.getAuth().uid as String, currentDate)
        for (workout in yourWorkoutList){
            workoutList.add(workoutRepository.getWorkoutById(workout.workoutId) as Workout)
        }
        return workoutList
    }
}