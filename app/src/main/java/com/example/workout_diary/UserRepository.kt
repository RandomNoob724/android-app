package com.example.workout_diary

class UserRepository {

    companion object{
        val instance = UserRepository()
    }

    fun setActiveUserAtCreation(newEmail: String?, newPassword: String?, newGender: String?, newDateOfBirth: String?, newAuthKey: String?, user: User){
        user.email = newEmail
        user.password = newPassword
        user.gender = newGender
        user.dateOfBirth = newDateOfBirth
        user.authKey = newAuthKey
    }

}