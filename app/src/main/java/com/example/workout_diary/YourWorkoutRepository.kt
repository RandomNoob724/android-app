package com.example.workout_diary
var yourWorkoutRepository = YourWorkoutRepository().apply {

}
class YourWorkoutRepository {
    var yourWorkouts = mutableListOf<YourWorkout>()

    fun addYourWorkout(yourWorkout: YourWorkout){

        yourWorkouts.add(yourWorkout)
        FirebaseDb.instance.addWorkoutForUser(yourWorkout)
    }

    fun getAllworkoutOnDay(date:String):MutableList<YourWorkout>{
        var returnList = mutableListOf<YourWorkout>()
        for (yourWorkout in yourWorkouts){
            if (yourWorkout.date == date){
                returnList.add(yourWorkout)
            }
        }
        return returnList
    }

}