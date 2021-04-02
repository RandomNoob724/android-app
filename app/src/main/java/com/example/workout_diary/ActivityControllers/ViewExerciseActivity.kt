package com.example.workout_diary.ActivityControllers

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.workout_diary.R
import com.example.workout_diary.Repositories.exerciseRepository

class ViewExerciseActivity : AppCompatActivity(){
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