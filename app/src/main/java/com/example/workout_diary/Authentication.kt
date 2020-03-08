package com.example.workout_diary

import android.util.Log
import com.google.firebase.auth.FirebaseAuth


class Authentication {
    companion object{
        val instance = Authentication()
    }
    private var activeUser: User = User()
    private var isAuthenticated: String? = null

    fun getUserInfo(): User?{
        return activeUser
    }

    fun setActiveUserAtCreation(user: User?){
        UserRepository.instance.setActiveUserAtCreation(user!!.email, user.password, user.gender, user.dateOfBirth, user.authKey, activeUser)
        Log.d("activeUser", this.activeUser.toString())
    }

    fun setAuthenticated(key: String){
        this.isAuthenticated = key
    }




}