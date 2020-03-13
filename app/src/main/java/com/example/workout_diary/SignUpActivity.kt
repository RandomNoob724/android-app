package com.example.workout_diary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text
import java.io.File
import java.io.ObjectInputValidation

class SignUpActivity : AppCompatActivity(){
    private lateinit var fAuth: FirebaseAuth

    private var usernameValidationChecker = false
    private var emailValidationChecker = false
    private var passwordValidationChecker = false
    private var genderValidationChecker = false

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
        val signUpLoadingBar = findViewById<ProgressBar>(R.id.signup_loading)

        signUpLoadingBar.setVisibility(View.GONE)
        val errorText = findViewById<TextView>(R.id.signup_validation)

        inputUsername.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable){
                if(inputUsername.text.trim().length < 2){
                    usernameValidationChecker = false
                    checkAllValidators(createButton, errorText, "Username too short")
                }
                else if(inputUsername.text.trim().length > 12){
                    usernameValidationChecker = false
                    checkAllValidators(createButton, errorText, "Username too long")
                }
                else{
                    usernameValidationChecker = true
                    checkAllValidators(createButton, errorText, "")
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int){
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int){
            }
        })

        inputEmail.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(inputEmail.text).matches()) {
                    emailValidationChecker = false
                    checkAllValidators(createButton, errorText, "Invalid Email")
                }
                else {
                    emailValidationChecker = true
                    checkAllValidators(createButton, errorText, "")

                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        inputPassword.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if(inputPassword.text.toString().length < 5){
                    passwordValidationChecker = false
                    checkAllValidators(createButton, errorText, "Password too short")
                }
                else if(inputPassword.text.toString() != inputConfirmPassword.text.toString()){
                    passwordValidationChecker = false
                    checkAllValidators(createButton, errorText, "Passwords not matching")
                }
                else{
                    passwordValidationChecker = true
                    checkAllValidators(createButton, errorText, "")
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        inputConfirmPassword.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if(inputPassword.text.toString() != inputConfirmPassword.text.toString()){
                    passwordValidationChecker = false
                    checkAllValidators(createButton, errorText, "Passwords not matching")
                }
                else{
                    passwordValidationChecker = true
                    checkAllValidators(createButton, errorText, "")
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        radioGroup.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = findViewById(checkedId)

                if(radioGroup.checkedRadioButtonId == -1){
                    genderValidationChecker = false
                    checkAllValidators (createButton, errorText, "Select a gender")
                }
                else{
                    genderValidationChecker = true
                    checkAllValidators(createButton, errorText, "")
                }
            }

        )

        createButton.setOnClickListener {
            fAuth = Authentication.instance.getAuth()

            val id: Int = radioGroup.checkedRadioButtonId
            val radio: RadioButton = findViewById(id)

            val day = datePicker.dayOfMonth
            val month = datePicker.month + 1
            val year = datePicker.year

            val selectedDate = "$day/$month/$year"

            fAuth.createUserWithEmailAndPassword(inputEmail.text.toString(), inputPassword.text.toString()).addOnCompleteListener(this){
                signUpLoadingBar.setVisibility(View.VISIBLE)
                if(it.isSuccessful){
                    val user = User(inputUsername.text.toString(), inputEmail.text.toString(), inputPassword.text.toString(), radio.text.toString(), selectedDate, fAuth.currentUser!!.uid)
                    FirebaseDb.instance.addUser(user)

                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra(MainActivity.EXTRA_USERNAME, inputUsername.toString())
                    startActivity(intent)
                    finish()
                }
                else{
                    signUpLoadingBar.setVisibility(View.GONE)
                    Log.d("Error: ", it.exception.toString())
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
    }

    fun checkAllValidators(createButton: Button, errorText: TextView, validationMessage: String){
         if(usernameValidationChecker && emailValidationChecker && passwordValidationChecker && genderValidationChecker){
             createButton.isEnabled = true
             createButton.isClickable = true
             errorText.text = validationMessage
         }
        else{
             createButton.isEnabled = false
             createButton.isClickable = false
             errorText.text = validationMessage
         }

    }

}




