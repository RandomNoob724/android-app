package com.example.workout_diary.Repositories

import com.example.workout_diary.Classes.User

class UserRepository {

    companion object{
        val instance = UserRepository()
    }

    fun setActiveUser(newUsername: String?, newEmail: String?, newPassword: String?, newGender: String?, newDateOfBirth: String?, newFirstName: String?, newLastName: String?, newWeight: String?, newGoalWeight: String?, newHeight: String?, newAuthUserId: String?, user: User){
        user.username = newUsername
        user.email = newEmail
        user.password = newPassword
        user.gender = newGender
        user.dateOfBirth = newDateOfBirth
        user.firstName = newFirstName
        user.lastName = newLastName
        user.weight = newWeight
        user.goalWeight = newGoalWeight
        user.height = newHeight
        user.authUserId = newAuthUserId
    }


    fun updateActiveUser(newFirstName: String, newLastName: String, newWeight: String, newGoalWeight: String, newHeight: String, user: User){
        user.firstName = newFirstName
        user.lastName = newLastName
        user.weight = newWeight
        user.goalWeight = newGoalWeight
        user.height = newHeight
    }

    fun removeActiveUser(user: User){
        user.username = null
        user.email = null
        user.password = null
        user.gender = null
        user.dateOfBirth = null
        user.firstName = null
        user.lastName = null
        user.weight = null
        user.goalWeight = null
        user.height = null
        user.authUserId = null
    }
}