package com.example.workout_diary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.view.View
import android.widget.*

class LogInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        val auth = Authentication.instance.getAuth()
        val progressbar = findViewById<ProgressBar>(R.id.login_progress)
        val inputUsername = findViewById<EditText>(R.id.login_username)
        val inputPassword = findViewById<EditText>(R.id.login_password)
        val loginButton = findViewById<Button>(R.id.login_loginButton)
        progressbar.setVisibility(View.GONE)

        loginButton.setOnClickListener{
            auth.signInWithEmailAndPassword(inputUsername.text.toString(), inputPassword.text.toString()).addOnCompleteListener(this) {task ->
                if(task.isSuccessful) {
                    val user = auth.currentUser
                    FirebaseDb.instance.getUserByAuthUserId()
                    progressbar.setVisibility(View.VISIBLE)
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(baseContext, resources.getString(R.string.authFailed), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
