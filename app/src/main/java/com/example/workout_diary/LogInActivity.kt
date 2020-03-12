package com.example.workout_diary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class LogInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        val mAuth = Authentication.instance.getAuth()
        val inputUsername = findViewById<EditText>(R.id.login_username)
        val inputPassword = findViewById<EditText>(R.id.login_password)
        val loginButton = findViewById<Button>(R.id.login_loginButton)
        val errorText = findViewById<TextView>(R.id.login_errorText)

        loginButton.setOnClickListener{
            mAuth.signInWithEmailAndPassword(inputUsername.text.toString(), inputPassword.text.toString()).addOnCompleteListener(this) { task ->
                if(task.isSuccessful) {
                    val user = mAuth.currentUser
                    FirebaseDb.instance.getUserByAuthUserId(user!!.uid)
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    Toast.makeText(baseContext, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }:
        }:
    }
}
