package com.example.workout_diary

import com.google.firebase.firestore.FirebaseFirestore

class FirebaseDb{
    companion object{
        val instance = FirebaseDb()
    }
    private val db = FirebaseFirestore.getInstance()

    fun addUser(user: HashMap<String, CharSequence>){
        db.collection("users").add(user)
    }


}


