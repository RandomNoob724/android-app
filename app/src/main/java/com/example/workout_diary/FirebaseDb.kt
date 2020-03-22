package com.example.workout_diary

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.coroutines.delay
import com.google.firebase.firestore.Source
import kotlinx.coroutines.tasks.await
import android.os.Handler
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
    }

    fun addWorkoutForUser(workout:YourWorkout){
        val dbRef = db.collection("userWorkout").document()
            .set(workout)
    }
     fun getAllworkoutsFromUserOnDay(userId: String,day: String): MutableList<YourWorkout>{
        return yourWorkoutRepository.getAllworkoutOnDay(day)
     }

    fun deleteUserByAuthUserId(authUserId: String?){
        val userRef = db.collection("users").document(authUserId!!)
        val userWorkoutRef = db.collection("userWorkout").whereEqualTo("userId", authUserId).get()

        userRef.delete().addOnCompleteListener{task ->
            if(task.isSuccessful){
                userWorkoutRef.addOnCompleteListener{
                    if(it.isSuccessful){
                        val documents = it.result!!.documents
                        for(doc in documents){
                            db.collection("userWorkout").document(doc.id).delete()
                        }
                    }
                    else{
                        Log.d("Error: ", it.exception.toString())
                    }
                }
            }
            else{
                Log.d("Errors: ", task.exception.toString())
            }
        }
    }
}



