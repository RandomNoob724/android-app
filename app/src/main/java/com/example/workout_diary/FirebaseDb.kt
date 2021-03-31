package com.example.workout_diary

import android.util.Log
import com.google.firebase.Timestamp
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.FirebaseFirestoreSettings
import java.text.SimpleDateFormat
import java.util.*
import kotlin.reflect.typeOf

class FirebaseDb {
    companion object{
        val instance = FirebaseDb()
    }
    private val db = FirebaseFirestore.getInstance()

    fun addUser(user: User){
        val userRef = db.collection("users").document(user.authUserId.toString())
        userRef.set(user)
        Authentication.instance.setActiveUser(user)
    }

    fun getUserByAuthUserId(){
        val userRef = db.collection("users").document(Authentication.instance.getAuth().uid.toString()).get()
        userRef.addOnCompleteListener{task ->
            if(task.isSuccessful) {
                Authentication.instance.setActiveUser(task.result?.toObject(User::class.java))
            }
            else{
                Log.d("Error: ", task.exception.toString())
            }
        }
    }

    fun updateUser(user: User){
        val userRef = db.collection("users").document(user.authUserId.toString())
        userRef.update(
            "firstName", user.firstName,
            "lastName", user.lastName,
            "weight", user.weight,
            "goalWeight", user.goalWeight,
            "height", user.height
        )
        Authentication.instance.setActiveUser(user)
    }

    suspend fun getAllExercises(): MutableList<Exercise> {
        val list = db.collection("exercises").get().await()
        val exerciseList = list.toObjects(Exercise::class.java)
        return exerciseList
    }

    suspend fun getAllWorkouts(): MutableList<Workout>{
        var workoutList: MutableList<Workout> = mutableListOf()
        var list = db.collection("allWorkouts").get().await()
        for (obj in list){
            var workout = Workout()
            workout.category = obj["category"]?.toString()
            workout.id = obj["id"].toString().toInt()
            var exerciseList: MutableList<Exercise> = mutableListOf()
            for (ex in obj["exercises"] as MutableList<DocumentReference>){
                Log.d("hasse",ex.id)

                exerciseList.add(exerciseRepository.getExerciseById(ex.id) as Exercise)
            }
            workout.exercises = exerciseList
            workoutList.add(workout)
        }
        return workoutList
    }

    suspend fun getAllWorkoutsFromUser(userId: String): MutableList<YourWorkout> {
        var list = db.collection("userWorkout")
            .whereEqualTo("userId",userId)
            .get()
            .await()

        val yourWorkoutList: MutableList<YourWorkout> = list.toObjects(YourWorkout::class.java)
        Log.d("UsersWorkout", yourWorkoutRepository.yourWorkouts.toString())
        return yourWorkoutList
    }

    fun addWorkoutForUser(workout:YourWorkout){
        val dbRef = db.collection("userWorkout").document()
            .set(workout)
    }
     fun getAllworkoutsFromUserOnDay(userId: String,day: String): MutableList<YourWorkout>{
         Log.d("UsersWorkout", userId)
         Log.d("UsersWorkout", yourWorkoutRepository.getAllworkoutOnDay(userId ,day).toString())
        return yourWorkoutRepository.getAllworkoutOnDay(userId, day)
     }

    fun deleteUserByAuthUserId(authUserId: String){
        val userRef = db.collection("users").document(authUserId)
        val userWorkoutRef = db.collection("userWorkout").whereEqualTo("userId", authUserId).get()

        userRef.delete().addOnCompleteListener{ deleteTask ->
            if(deleteTask.isSuccessful){
                Log.d("Successfully removed: ", deleteTask.result.toString())
            }
            else{
                Log.d("Failed to remove: ", deleteTask.exception.toString())
            }
        }

        userWorkoutRef.addOnCompleteListener{ workoutRef ->
            if(workoutRef.isSuccessful) {
                val documents = workoutRef.result!!.documents
                for (doc in documents) {
                    db.collection("userWorkout").document(doc.id).delete()
                }
            }
            else{
                Log.d("Error: ", workoutRef.exception.toString())
            }
        }

        FirebaseAuth.getInstance().currentUser?.delete()?.addOnCompleteListener{task ->
            if(task.isSuccessful){
                Log.d("Successfully removed: ", task.result.toString())
            }
            else{
                Log.d("Errors: ", task.exception.toString())
            }
        }

    }
}



