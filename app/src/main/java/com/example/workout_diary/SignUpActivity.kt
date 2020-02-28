package com.example.workout_diary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.*
import android.widget.Button
import org.w3c.dom.Text

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val createButton = findViewById<Button>(R.id.signup_createAccount)
        createButton.isClickable = false
        createButton.isEnabled = false

        val radioGroup = findViewById<RadioGroup>(R.id.signup_gender)
        val datePicker = findViewById<DatePicker>(R.id.signup_datePicker)
        val inputUsername = findViewById<EditText>(R.id.signup_username)
        val inputEmail = findViewById<EditText>(R.id.signup_email)
        val inputPassword = findViewById<EditText>(R.id.signup_password)
        val inputConfirmPassword = findViewById<EditText>(R.id.signup_passwordConfirm)
        val validationText = findViewById<TextView>(R.id.signup_validation)

        var usernameValidationChecker = false
        var emailValidationChecker = false
        var passwordValidationChecker = false

        inputUsername.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable){}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int){}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int){
                if(inputUsername.text.trim().length < 2){
                    validationText.text = "Username to short"
                    usernameValidationChecker = false
                    checkAllTextWatchers(usernameValidationChecker, emailValidationChecker, passwordValidationChecker, createButton,validationText)
                }
                else if(inputUsername.text.trim().length > 12){
                    validationText.text = "Username to long"
                    usernameValidationChecker = false
                    checkAllTextWatchers(usernameValidationChecker, emailValidationChecker, passwordValidationChecker, createButton,validationText)
                }
                else{
                    usernameValidationChecker = true
                    checkAllTextWatchers(usernameValidationChecker, emailValidationChecker, passwordValidationChecker, createButton,validationText)
                }
            }
        })

        inputEmail.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(inputEmail.text).matches()){
                    validationText.text = "Invalid email"
                    emailValidationChecker = false
                    checkAllTextWatchers(usernameValidationChecker, emailValidationChecker, passwordValidationChecker, createButton,validationText)
                }
                else{
                    emailValidationChecker = true
                    checkAllTextWatchers(usernameValidationChecker, emailValidationChecker, passwordValidationChecker, createButton,validationText)
                }
            }
        })

        inputPassword.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(inputPassword.text.length < 6){
                    validationText.text = "Password too short"
                    passwordValidationChecker = false
                    checkAllTextWatchers(usernameValidationChecker, emailValidationChecker, passwordValidationChecker, createButton,validationText)
                }
                else if(inputPassword.text.toString() != inputConfirmPassword.text.toString()){
                    validationText.text = "Passwords not matching"
                    passwordValidationChecker = false
                    checkAllTextWatchers(usernameValidationChecker, emailValidationChecker, passwordValidationChecker, createButton,validationText)
                }
                else{
                    passwordValidationChecker = true
                    checkAllTextWatchers(usernameValidationChecker, emailValidationChecker, passwordValidationChecker, createButton,validationText)
                }
            }
        })

        inputConfirmPassword.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(inputPassword.text.toString() != inputConfirmPassword.text.toString()){
                    validationText.text = "Passwords not matching"
                    passwordValidationChecker = false
                    checkAllTextWatchers(usernameValidationChecker, emailValidationChecker, passwordValidationChecker, createButton,validationText)
                }
                else{
                    passwordValidationChecker = true
                    checkAllTextWatchers(usernameValidationChecker, emailValidationChecker, passwordValidationChecker, createButton,validationText)
                }
            }
        })

        radioGroup.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = findViewById(checkedId)
            }
        )

        createButton.setOnClickListener {

            val id: Int = radioGroup.checkedRadioButtonId
            val radio: RadioButton = findViewById(id)

            val day = datePicker.dayOfMonth
            val month = datePicker.month + 1
            val year = datePicker.year

            val selectedDate = "$day/$month/$year"

            val user = hashMapOf(
                "username" to inputUsername.text.toString().trim(),
                "email" to inputEmail.text.toString(),
                "password" to inputPassword.text.toString(),
                "gender" to radio.text,
                "dateOfBirth" to selectedDate
            )
            FirebaseDb.instance.addUser(user)
        }
    }

    fun checkAllTextWatchers(usernameValidator: Boolean, emailValidator: Boolean, passwordValidator: Boolean, createButton: Button,validationText: TextView){
        Log.d("username",usernameValidator.toString() + " email " + emailValidator.toString() + " password " + passwordValidator.toString())
        if(usernameValidator && emailValidator && passwordValidator){
            createButton.isEnabled = true
            createButton.isClickable = true
            validationText.text = ""
        }
        else{
            createButton.isEnabled = false
            createButton.isClickable = false
        }

    }
}


