package com.example.workout_diary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_profile_settings.*
import org.w3c.dom.Text

class ProfileSettingsActivity : AppCompatActivity() {

    private var firstNameValidationChecker = true
    private var lastNameValidationChecker = true
    private var weightValidationChecker = true
    private var goalWeightValidationChecker = true
    private var heightValidationChecker = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_settings)

        val inputFirstName = findViewById<EditText>(R.id.settings_firstName)
        val inputLastName = findViewById<EditText>(R.id.settings_lastName)
        val inputWeight = findViewById<EditText>(R.id.settings_weight)
        val inputGoalWeight = findViewById<EditText>(R.id.settings_goalWeight)
        val inputHeight = findViewById<EditText>(R.id.settings_height)
        val saveButton = findViewById<Button>(R.id.settings_saveButton)
        val errorText = findViewById<TextView>(R.id.settings_errorText)

        inputFirstName.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if(containsDigit(inputFirstName.text.toString())){
                    firstNameValidationChecker = false
                    checkAllValidators(saveButton, errorText, "Names can not contain digits")
                }
                else if(inputFirstName.text.toString().length > 16) {
                    firstNameValidationChecker = false
                    checkAllValidators(saveButton, errorText, "Name too long")
                }
                else{
                    firstNameValidationChecker = true
                    checkAllValidators(saveButton, errorText, "")
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(inputFirstName.text.toString() == ""){
                    firstNameValidationChecker = true
                    checkAllValidators(saveButton, errorText, "")
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        inputLastName.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if(containsDigit(inputLastName.text.toString())){
                    lastNameValidationChecker = false
                    checkAllValidators(saveButton, errorText, "Names can not contain digits")
                }
                else if(inputLastName.text.toString().length > 16){
                    lastNameValidationChecker = false
                    checkAllValidators(saveButton, errorText, "Name too long")
                }
                else{
                    lastNameValidationChecker = true
                    checkAllValidators(saveButton, errorText, "")
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(inputLastName.text.toString() == ""){
                    lastNameValidationChecker = true
                    checkAllValidators(saveButton, errorText, "")
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        inputWeight.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if(!stringsIsNumber(inputWeight.text.toString())){
                    weightValidationChecker = false
                    checkAllValidators(saveButton, errorText, "Weight can only be written in digits")
                }
                else{
                    weightValidationChecker = true
                    checkAllValidators(saveButton, errorText, "")
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(inputWeight.text.toString() == ""){
                    weightValidationChecker = true
                    checkAllValidators(saveButton, errorText, "")
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        inputGoalWeight.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if(!stringsIsNumber(inputGoalWeight.text.toString())){
                    goalWeightValidationChecker = false
                    checkAllValidators(saveButton, errorText, "Goal weight can only be written in digits")
                }
                else{
                    goalWeightValidationChecker = true
                    checkAllValidators(saveButton, errorText, "")
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(inputGoalWeight.text.toString() == ""){
                    goalWeightValidationChecker = true
                    checkAllValidators(saveButton, errorText, "")
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        inputHeight.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if(!stringsIsNumber(inputHeight.text.toString())){
                    heightValidationChecker = false
                    checkAllValidators(saveButton, errorText, "Goal weight can only be written in digits")
                }
                else{
                    heightValidationChecker = true
                    checkAllValidators(saveButton, errorText, "")
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(inputHeight.text.toString() == ""){
                    heightValidationChecker = true
                    checkAllValidators(saveButton, errorText, "")
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        saveButton.setOnClickListener {
            Authentication.instance.updateActiveUser(inputFirstName.text.toString(), inputLastName.text.toString(), inputWeight.text.toString(), inputWeight.text.toString(), inputHeight.text.toString())
            val updatedUser = Authentication.instance.getUserInfo()
            FirebaseDb.instance.updateUser(updatedUser)
            finish()
        }
    }

    fun checkAllValidators(saveButton: Button, errorText: TextView, validationMessage: String){
        if(firstNameValidationChecker && lastNameValidationChecker && weightValidationChecker && goalWeightValidationChecker && heightValidationChecker){
            saveButton.isClickable = true
            saveButton.isEnabled = true
            errorText.text = ""
        }
        else{
            saveButton.isClickable = false
            saveButton.isEnabled = false
            errorText.text = validationMessage
        }
    }

    fun containsDigit(text: String): Boolean{
        val regex = Regex("^[a-zA-Z]*$")
        return text.matches(regex)
    }

    fun stringsIsNumber(text: String): Boolean{
        val regex = Regex("^[0-9]*\$")
        return text.matches(regex)
    }
}
