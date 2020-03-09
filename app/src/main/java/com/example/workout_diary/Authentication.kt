package com.example.workout_diary

import android.util.Log
import com.google.firebase.auth.FirebaseAuth


class Authentication {
    companion object{
        val instance = Authentication()
    }
    private var activeUser: User = User()

    fun getUserInfo(): User?{
        return activeUser
    }

    fun setActiveUserAtCreation(user: User?){
        UserRepository.instance.setActiveUserAtCreation(user!!.email, user.password, user.gender, user.dateOfBirth, activeUser)
        Log.d("activeUser", this.activeUser.toString())
    }

    fun getAuthenticated(): String?{
        return activeUser.authKey
    }

    fun setAuthenticated(key: String?){
        
    }

    fun getPassword(): String?{
        return activeUser.password
    }


}