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
        val dbRef = db.collection("users").document(user.username.toString())
        dbRef.set(user)
        this.getUserByUsername(dbRef.id)
    }

    fun getUserByUsername(username: String){
        val userRef = db.collection("users").document(username).get()
        userRef.addOnCompleteListener{task ->
            if(task.isSuccessful) {
                Authentication.instance.setActiveUser(task.result?.toObject(User::class.java))
                Authentication.instance.setAuthKey(username)
            }
            else{
                Log.d("Error: ", task.exception.toString())
            }
        }
    }

    fun updateUser(user: User){
        val userRef = db.collection("users").document(user.authKey.toString())
        userRef.update(
            "firstName", user.firstName,
            "lastName", user.lastName,
            "weight", user.weight,
            "goalWeight", user.goalWeight,
            "height", user.height
        )
    }

    fun getUserFromCache(){
        val userRef = db.collection("users")
        val source = Source.CACHE

        userRef.get(source).addOnCompleteListener{task ->
            if(task.isSuccessful){
                var list = task.result?.toObjects(User::class.java)
                Log.d("length users in cache",list!!.toString())
                Authentication.instance.setActiveUser(list!![0])
            }
            else{
                Log.d("Error: ", task.exception.toString())
            }
        }
    }

}


