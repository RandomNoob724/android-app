package com.example.workout_diary

import android.util.Log
import com.google.firebase.auth.FirebaseAuth


class Authentication {
    companion object{
        val instance = Authentication()
    }
    private var activeUser: User = User()

    fun getUserInfo(): User{
        return activeUser
    }

    fun setActiveUser(user: User?){
        UserRepository.instance.setActiveUser(user!!.email, user.password, user.gender, user.dateOfBirth, activeUser)
        Log.d("activeUser", this.activeUser.toString())
    }

    fun getAuthenticated(): String?{
        return activeUser.authKey
    }

    fun setAuthenticated(key: String?){
        UserRepository.instance.setAuthKey(key, activeUser)
    }

    fun getPassword(): String?{
        return activeUser.password
    }

    fun updateActiveUser(firstName: String, lastName: String, weight: String, goalWeight: String, height: String){
        UserRepository.instance.updateActiveUser(firstName, lastName, weight, goalWeight, height, activeUser)
    }


}