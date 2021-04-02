package com.example.workout_diary.ActivityControllers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.workout_diary.FirebaseControllers.Authentication
import com.example.workout_diary.FirebaseControllers.FirebaseDb
import com.example.workout_diary.R

class ProfileSettingsActivity : AppCompatActivity() {

    private var firstNameValidationChecker = true
    private var lastNameValidationChecker = true
    private var weightValidationChecker = true
    private var goalWeightValidationChecker = true
    private var heightValidationChecker = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_settings)

        val userInfo = Authentication.instance.getUserInfo()

        val inputFirstName = findViewById<EditText>(R.id.settings_firstName)
        val inputLastName = findViewById<EditText>(R.id.settings_lastName)
        val inputWeight = findViewById<EditText>(R.id.settings_weight)
        val inputGoalWeight = findViewById<EditText>(R.id.settings_goalWeight)
        val inputHeight = findViewById<EditText>(R.id.settings_height)
        val saveButton = findViewById<Button>(R.id.settings_saveButton)
        val errorText = findViewById<TextView>(R.id.settings_errorText)

        inputFirstName.text = Editable.Factory.getInstance().newEditable(userInfo.firstName)
        inputLastName.text = Editable.Factory.getInstance().newEditable(userInfo.lastName)
        inputWeight.text = Editable.Factory.getInstance().newEditable(userInfo.weight)
        inputGoalWeight.text = Editable.Factory.getInstance().newEditable(userInfo.goalWeight)
        inputHeight.text = Editable.Factory.getInstance().newEditable(userInfo.height)

        inputFirstName.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if(!containsSymbolsOrDigits(inputFirstName.text.toString())){
                    firstNameValidationChecker = false
                    checkAllValidators(saveButton, errorText, resources.getString(R.string.nameOnlyCharacters))
                }
                else if(inputFirstName.text.toString().length > 16) {
                    firstNameValidationChecker = false
                    checkAllValidators(saveButton, errorText, resources.getString(R.string.nameLong))
                }
                else{
                    firstNameValidationChecker = true
                    checkAllValidators(saveButton, errorText, "")
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        inputLastName.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if(!containsSymbolsOrDigits(inputLastName.text.toString())){
                    lastNameValidationChecker = false
                    checkAllValidators(saveButton, errorText, resources.getString(R.string.nameOnlyCharacters))
                }
                else if(inputLastName.text.toString().length > 16){
                    lastNameValidationChecker = false
                    checkAllValidators(saveButton, errorText, resources.getString(R.string.nameLong))
                }
                else{
                    lastNameValidationChecker = true
                    checkAllValidators(saveButton, errorText, "")
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        inputWeight.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if(!stringsIsNumber(inputWeight.text.toString())){
                    weightValidationChecker = false
                    checkAllValidators(saveButton, errorText, resources.getString(R.string.weightOnlyDigits))
                }
                else{
                    weightValidationChecker = true
                    checkAllValidators(saveButton, errorText, "")
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        inputGoalWeight.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if(!stringsIsNumber(inputGoalWeight.text.toString())){
                    goalWeightValidationChecker = false
                    checkAllValidators(saveButton, errorText, resources.getString(R.string.goalWeightDigits))
                }
                else{
                    goalWeightValidationChecker = true
                    checkAllValidators(saveButton, errorText, "")
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        inputHeight.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if(!stringsIsNumber(inputHeight.text.toString())){
                    heightValidationChecker = false
                    checkAllValidators(saveButton, errorText, resources.getString(R.string.heightDigits))
                }
                else{
                    heightValidationChecker = true
                    checkAllValidators(saveButton, errorText, "")
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        saveButton.setOnClickListener {
            Authentication.instance.updateActiveUser(inputFirstName.text.toString(), inputLastName.text.toString(), inputWeight.text.toString(), inputGoalWeight.text.toString(), inputHeight.text.toString())
            val updatedUser = Authentication.instance.getUserInfo()
            FirebaseDb.instance.updateUser(updatedUser)
            finish()
        }
    }

    fun checkAllValidators(saveButton: Button, errorText: TextView, validationMessage: String){
        if(firstNameValidationChecker && lastNameValidationChecker && weightValidationChecker && goalWeightValidationChecker && heightValidationChecker){
            saveButton.isClickable = true
            saveButton.isEnabled = true
            errorText.text = validationMessage
        }
        else{
            saveButton.isClickable = false
            saveButton.isEnabled = false
            errorText.text = validationMessage
        }
    }

    fun containsSymbolsOrDigits(text: String): Boolean{
        val regex = Regex("^[a-zA-Z]*$")
        return text.matches(regex)
    }

    fun stringsIsNumber(text: String): Boolean{
        val regex = Regex("^[0-9]*\$")
        return text.matches(regex)
    }
}
