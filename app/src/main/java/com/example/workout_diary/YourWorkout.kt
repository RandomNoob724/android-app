package com.example.workout_diary

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

data class YourWorkout(
    var date: Timestamp = Timestamp.now(),
    var userId: String = "",
    var workoutId: Int = 0){
    override fun toString() = "userId: " + userId + " workoutId: " + workoutId.toString() + " date: " + date.toString()
}