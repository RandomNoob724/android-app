package com.example.workout_diary

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.Text

class ViewExercise : AppCompatActivity(){
    companion object {
        const val EXERCISE_ID = "Exercise_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_exercise_activity)
        val exerciseTitle = this.findViewById<TextView>(R.id.view_exercise_title)
        val exerciseDesc = this.findViewById<TextView>(R.id.view_exercise_description)

        val exerciseTitlefromIntent = intent.getStringExtra(EXERCISE_ID)

        val exerciseItem = exerciseRepository.getExerciseByTitle(exerciseTitlefromIntent!!)
        if(exerciseItem != null){
            exerciseTitle.text = exerciseItem.title
            exerciseDesc.text = exerciseItem.description
        }
    }
}