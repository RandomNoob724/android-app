package com.example.workout_diary

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.lang.reflect.Array

class FirebaseDb{
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
            } else {
                Log.d("error",it.exception.toString())
            }
        }
    }

    fun getAllWorkouts(){
        var list = db.collection("allWorkouts").get()
        list.addOnCompleteListener{
            if (it.isSuccessful){
               workoutRepository.workouts = it.result!!.toObjects(Workout::class.java)
            } else {
                Log.d("error",it.exception.toString())
            }
        }
    }
}




