package com.example.workout_diary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class LogInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        val inputUsername = findViewById<EditText>(R.id.login_username).text.toString()
        val inputPassword = findViewById<EditText>(R.id.login_password).text.toString()
        val loginButton = findViewById<Button>(R.id.login_loginButton)
        val errorText = findViewById<TextView>(R.id.login_errorText)

        loginButton.setOnClickListener {
            FirebaseDb.instance.getUserByUsername(inputUsername)

            if(Authentication.instance.getPassword() != ""){
                if(inputPassword == Authentication.instance.getPassword()){
                    Authentication.instance.setAuthKey(inputUsername)
                    intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                else{
                    errorText.text = "Incorrect password"
                }
            }
            else{
                errorText.text = "No account with given username"
            }

        }
    }
}
