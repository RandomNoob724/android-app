package com.example.workout_diary.Repositories

import com.example.workout_diary.FirebaseControllers.FirebaseDb
import com.example.workout_diary.Classes.YourWorkout

var yourWorkoutRepository = YourWorkoutRepository()
    .apply {

}
class YourWorkoutRepository {
    var yourWorkouts = mutableListOf<YourWorkout>()

    fun addYourWorkout(yourWorkout: YourWorkout){
        yourWorkouts.add(yourWorkout)
        FirebaseDb.instance.addWorkoutForUser(yourWorkout)
    }

    fun getAllworkoutOnDay(userId: String, date:String):MutableList<YourWorkout>{
        var returnList = mutableListOf<YourWorkout>()
        for (yourWorkout in yourWorkouts){
            if (yourWorkout.date == date && yourWorkout.userId == userId){
                returnList.add(yourWorkout)
            }
        }
        return returnList
    }

    fun resetYourWorkoutList(){
        yourWorkouts = mutableListOf<YourWorkout>()
    }
}