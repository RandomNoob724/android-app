package com.example.workout_diary.Classes

data class User (
    var username: String? = "",
    var email: String? = "",
    var password: String? = "",
    var gender: String? = "",
    var dateOfBirth: String? = "",
    var authUserId: String? = "",
    var firstName: String? = "",
    var lastName: String? = "",
    var height: String? = "",
    var weight: String? = "",
    var goalWeight: String? = ""){

    override fun toString() = email.toString()
}




