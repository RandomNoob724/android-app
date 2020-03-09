package com.example.workout_diary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class LogInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        val inputUsername = findViewById<EditText>(R.id.login_username).toString()
        val inputPassword = findViewById<EditText>(R.id.login_password).toString()
        val loginButton = findViewById<Button>(R.id.login_loginButton)
        val errorText = findViewById<TextView>(R.id.login_errorText)

        loginButton.setOnClickListener {
            FirebaseDb.instance.getUserByUsername(inputUsername)

            if(Authentication.instance.getPassword() != ""){
                if(inputPassword == Authentication.instance.getPassword()){

                }
            }
            else{
                errorText.text = "No account with given username"
            }

        }
    }
}
