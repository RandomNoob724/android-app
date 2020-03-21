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
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.concurrent.timerTask

open class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val signInButton = this.findViewById<Button>(R.id.start_signup)
        val logInButton = this.findViewById<Button>(R.id.start_login)
        val skipLoginButton = this.findViewById<Button>(R.id.start_skipLogin)

        val auth = FirebaseAuth.getInstance()
        Authentication.instance.setAuth(auth)
        FirebaseDb.instance.getAllExercises()

        if (auth.currentUser != null) {
            FirebaseDb.instance.getUserByAuthUserId()
            Log.d("userinfo", Authentication.instance.getAuth().currentUser!!.email.toString())
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

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
    }
}

