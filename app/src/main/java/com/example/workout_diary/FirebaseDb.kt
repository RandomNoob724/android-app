package com.example.workout_diary

import android.util.Log
import com.google.firebase.Timestamp
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.FirebaseFirestoreSettings
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

    fun getAllExercises(){
        val list = db.collection("exercises").get()
        list.addOnSuccessListener{
            exerciseRepository.exercises = it.toObjects(Exercise::class.java)

            getAllWorkouts()
        }
        list.addOnFailureListener {
            Log.d("failure",it.message as String)
        }
    }

    fun getAllWorkouts(){
        var list = db.collection("allWorkouts").get()
        list.addOnSuccessListener{ documents ->

            var workoutList: MutableList<Workout> = mutableListOf()
            for (obj in documents){
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
            workoutRepository.workouts = workoutList
            if (Authentication.instance.getAuth().uid != null) {
                getAllWorkoutsFromUser(Authentication.instance.getAuth().uid as String)
            }
        }

        .addOnFailureListener {
            Log.d("failure",it.message)
        }
    }

    fun getAllWorkoutsFromUser(userId: String){
        var list = db.collection("userWorkout")
            .whereEqualTo("userId",userId)
            .get()
        list.addOnSuccessListener {
            yourWorkoutRepository.yourWorkouts = it.toObjects(YourWorkout::class.java)
        }
        Log.d("UsersWorkout", yourWorkoutRepository.yourWorkouts.toString())
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

    fun deleteUserByAuthUserId(authUserId: String?){
        val userRef = db.collection("users").document(authUserId!!)
        val userWorkoutRef = db.collection("userWorkout").whereEqualTo("userId", authUserId).get()

        FirebaseAuth.getInstance().currentUser?.delete()?.addOnCompleteListener{task ->
            if(task.isSuccessful){
               Log.d("Successfully removed: ", task.result.toString())
            }
            else{
                Log.d("Errors: ", task.exception.toString())
            }
        }
        userRef.delete().addOnCompleteListener{ deleteTask ->
            if(deleteTask.isSuccessful){
                Log.d("Successfully removed: ", deleteTask.result.toString())
            }
            else{
                Log.d("Failed to remove: ", deleteTask.exception.toString())
            }
        }

        userWorkoutRef.addOnCompleteListener{
            if(it.isSuccessful) {
                val documents = it.result!!.documents
                for (doc in documents) {
                    db.collection("userWorkout").document(doc.id).delete()
                }
            }
            else{
                Log.d("Error: ", it.exception.toString())
            }
        }

    }
}



