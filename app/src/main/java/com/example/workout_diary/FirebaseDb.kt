package com.example.workout_diary

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import java.lang.reflect.Array
import java.util.*
import android.os.Handler


class FirebaseDb {
    companion object{
        val instance = FirebaseDb()
    }
    private val db = FirebaseFirestore.getInstance()

    fun addUser(user: User){
        val dbRef = db.collection("users").document(user.username.toString())
        dbRef.set(user)
        this.getUserByUsername(dbRef.id)
    }

    fun getUserByUsername(username: String){
        val userRef = db.collection("users").document(username).get()
        userRef.addOnCompleteListener{task ->
            if(task.isSuccessful) {
                Authentication.instance.setActiveUserAtCreation(task.result?.toObject(User::class.java))
            }
            else{
                Log.d("Error: ", task.exception.toString())
            }
        }
    }

    fun getAllExercises(){
        val list = db.collection("exercises").get()
        list.addOnCompleteListener{
            if(it.isSuccessful){
                exerciseRepository.exercises = it.result!!.toObjects(Exercise::class.java)
                Log.d("getAllExercises", exerciseRepository.exercises.toString())
            } else {
                Log.d("error",it.exception.toString())
            }
        }
    }

    fun getAllWorkouts(){
        var list = db.collection("allWorkouts").get()
            list.addOnSuccessListener{ documents ->
                var workoutList: MutableList<Workout> = mutableListOf()
                for (obj in documents){
                    var workout: Workout = Workout()
                    workout.category = obj["category"]?.toString()
                    workout.id = obj["id"].toString().toInt()
                    var exerciseList: MutableList<Exercise> = mutableListOf()
                    for (ex in obj["exercises"] as MutableList<DocumentReference>){
                        Log.d("ExerciseItem", ex.id.toString())
                        Handler().postDelayed({
                            exerciseList.add(exerciseRepository.getExerciseByTitle(ex.id) as Exercise)
                        },20)
                    }
                    workout.exercises = exerciseList
                    workoutList.add(workout)
                }
                Log.d("Allexercises", exerciseRepository.exercises.toString())
                workoutRepository.workouts = workoutList
                Log.d("getAllWorkouts", workoutRepository.workouts.toString())
            }
            .addOnFailureListener {
                Log.d("failure",it.message)
            }
    }

    fun getAllWorkoutsFromUser(userId: String) : MutableList<YourWorkout>{
        var userWorkout: MutableList<YourWorkout> = mutableListOf()
        var list = db.collection("userWorkout")
            .whereEqualTo("userId",userId)
            .get()
        list.addOnCompleteListener {
            if (it.isSuccessful){
                userWorkout = it.result!!.toObjects(YourWorkout::class.java)
                Log.d("NajsBajsMajs111111",userWorkout.size.toString())
            } else {
                Log.d("Error: ",it.exception.toString())
            }
        }
        return userWorkout
    }

    fun addWorkoutForUser(workout:YourWorkout){
        val dbRef = db.collection("userWorkout").document()
            .set(workout)
    }
     fun getAllworkoutsFromUserOnDay(userId: String,day: Timestamp): MutableList<YourWorkout>{
         var userWorkout: MutableList<YourWorkout> = mutableListOf()
         var list = db.collection("userWorkout")
             .whereEqualTo("userId",userId)
             .whereEqualTo("date",day)
             .get()
         list.addOnCompleteListener {
             if (it.isSuccessful){
                 userWorkout = it.result!!.toObjects(YourWorkout::class.java)
                 Log.d("NajsBajsMajs2222222",userWorkout.size.toString())
             } else {
                 Log.d("Error: ",it.exception.toString())
             }
         }
         return userWorkout
     }
}




