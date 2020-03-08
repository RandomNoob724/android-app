package com.example.workout_diary

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

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

}


