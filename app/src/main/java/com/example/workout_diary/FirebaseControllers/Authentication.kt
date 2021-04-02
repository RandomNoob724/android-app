package com.example.workout_diary.FirebaseControllers

import android.content.Context
import com.example.workout_diary.Classes.User
import com.example.workout_diary.Repositories.UserRepository
import com.google.firebase.auth.FirebaseAuth


class Authentication {
    companion object{
        val instance =
            Authentication()
    }

    private var activeUser: User =
        User()
    private lateinit var auth: FirebaseAuth

    var context: Context? = null

    fun getUserInfo(): User {
        return activeUser
    }

    fun setActiveUser(user: User?){
        UserRepository.instance.setActiveUser(user!!.username, user.email, user.password, user.gender, user.dateOfBirth, user.firstName, user.lastName, user.weight, user.goalWeight, user.height, user.authUserId, activeUser)
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

    fun getAuthUserId(): String?{
        return activeUser.authUserId
    }

}