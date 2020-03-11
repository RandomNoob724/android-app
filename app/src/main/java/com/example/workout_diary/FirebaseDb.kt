package com.example.workout_diary

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import kotlinx.coroutines.tasks.await

class FirebaseDb{
    companion object{
        val instance = FirebaseDb()
    }
    private val db = FirebaseFirestore.getInstance()

    fun addUser(user: User){
        val userRef = db.collection("users").document(user.authUserId.toString())
        userRef.set(user)
        Authentication.instance.setActiveUser(user)
    }

    fun getUserByAuthUserId(authUserId: String?){
        val userRef = db.collection("users").document(authUserId.toString()).get()
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
    }

    fun getUserFromCache(authUserId: String?){
        val userRef = db.collection("users").document(authUserId!!)

        userRef.get().addOnCompleteListener{task ->
            if(task.isSuccessful){
                val user = task.result?.toObject(User::class.java)
                Log.d("length users in cache",user!!.toString())
                Authentication.instance.setActiveUser(user)
            }
            else{
                Log.d("Error: ", task.exception.toString())
            }
        }
    }


}


