package com.example.workout_diary

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

}