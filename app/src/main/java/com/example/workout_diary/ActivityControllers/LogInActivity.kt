package com.example.workout_diary.ActivityControllers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.view.View
import android.widget.*
import com.example.workout_diary.FirebaseControllers.Authentication
import com.example.workout_diary.FirebaseControllers.FirebaseDb
import com.example.workout_diary.R

class LogInActivity : AppCompatActivity() {

    private var usernameValidationChecker = false
    private var passwordValidationChecker = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        val auth = Authentication.instance.getAuth()
        val progressbar = findViewById<ProgressBar>(R.id.login_progress)
        val inputUsername = findViewById<EditText>(R.id.login_username)
        val inputPassword = findViewById<EditText>(R.id.login_password)
        val loginButton = findViewById<Button>(R.id.login_loginButton)
        loginButton.isClickable = false
        loginButton.isEnabled = false
        val errorText = findViewById<TextView>(R.id.login_errorText)
        progressbar.setVisibility(View.GONE)

        inputUsername.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if(inputUsername.text.toString().length <= 0){
                    usernameValidationChecker = false
                    checkAllValidators(loginButton, errorText, resources.getString(
                        R.string.enterEmail
                    ))
                }
                else{
                    usernameValidationChecker = true
                    checkAllValidators(loginButton, errorText, "")
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        inputPassword.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if(inputPassword.text.toString().length <= 0){
                    passwordValidationChecker = false
                    checkAllValidators(loginButton, errorText, resources.getString(
                        R.string.enterPassword
                    ))
                }
                else{
                    passwordValidationChecker = true
                    checkAllValidators(loginButton, errorText, "")
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

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

    fun checkAllValidators (loginButton: Button, errorText: TextView, validationMessage: String){
        if(usernameValidationChecker && passwordValidationChecker){
            loginButton.isEnabled = true
            loginButton.isClickable = true
            errorText.text = validationMessage
        }
        else{
            loginButton.isClickable = false
            loginButton.isEnabled = false
            errorText.text = validationMessage
        }
    }
}
