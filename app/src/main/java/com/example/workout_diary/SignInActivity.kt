package com.example.workout_diary

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.annotation.RequiresApi
import com.google.firebase.Timestamp
import kotlinx.coroutines.delay
import java.sql.Date
import java.util.*
import android.util.Log
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

import java.io.File

open class SignInActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val signInButton = this.findViewById<Button>(R.id.start_signup)
        val logInButton = this.findViewById<Button>(R.id.start_login)
        val skipLoginButton = this.findViewById<Button>(R.id.start_skipLogin)

        FirebaseDb.instance.getAllExercises()
        FirebaseDb.instance.getAllWorkouts()


        //FirebaseDb.instance.addWorkoutForUser(YourWorkout(Timestamp(Date(Date().year,3,12)),"I0W3KeedqsctKRFn42hleK4G0dk2",2))
        //FirebaseDb.instance.getAllWorkoutsFromUser("I0W3KeedqsctKRFn42hleK4G0dk2")
        //FirebaseDb.instance.getAllworkoutsFromUserOnDay("I0W3KeedqsctKRFn42hleK4G0dk2",Timestamp(Date(120,3,12)))

        signInButton.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        logInButton.setOnClickListener {
            startActivity(Intent(this, LogInActivity::class.java))
        }

        skipLoginButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

    override fun onStart() {
        super.onStart()
        val auth = FirebaseAuth.getInstance()
        Authentication.instance.setAuth(auth)
        if(auth.currentUser != null){
            FirebaseDb.instance.getUserByAuthUserId(auth.uid)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}

