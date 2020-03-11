package com.example.workout_diary

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import io.grpc.Context
import java.io.File


class Authentication {
    companion object{
        val instance = Authentication()
    }
    private var activeUser: User = User()
    private lateinit var auth: FirebaseAuth

    var context: Context? = null

    fun getUserInfo(): User{
        return activeUser
    }

    fun setActiveUser(user: User?){
        UserRepository.instance.setActiveUser(user!!.email, user.password, user.gender, user.dateOfBirth, user.firstName, user.lastName, user.weight, user.goalWeight, user.height, activeUser)
        Log.d("activeUser", this.activeUser.toString())
    }


    fun setAuthKey(key: String?){
        UserRepository.instance.setAuthKey(key, activeUser)
    }

    fun getPassword(): String?{
        return activeUser.password
    }

    fun updateActiveUser(firstName: String, lastName: String, weight: String, goalWeight: String, height: String){
        UserRepository.instance.updateActiveUser(firstName, lastName, weight, goalWeight, height, activeUser)
    }

    fun setAuth(auth: FirebaseAuth){
        this.auth = auth
    }

    fun getAuth(): FirebaseAuth{
        return this.auth
    }

}