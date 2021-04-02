package com.example.workout_diary.ActivityControllers

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.workout_diary.Classes.Exercise
import com.example.workout_diary.R
import com.example.workout_diary.Repositories.workoutRepository

class ViewWorkoutActivity : AppCompatActivity(){
    companion object {
        const val WORKOUT_ID = "Workout_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_workout)
        val listOfExercises = this.findViewById<ListView>(R.id.todays_exercises_list)

        val workoutId = intent.getIntExtra(WORKOUT_ID, 28349290)
        val workout = workoutRepository.getWorkoutById(workoutId)
        if(workout != null){
            listOfExercises.adapter = ArrayAdapter<Exercise>(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                workout.exercises
            )

            listOfExercises.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                val exerciseItem = listOfExercises.adapter.getItem(position) as Exercise
                val intent = Intent(view.context, ViewExerciseActivity::class.java)
                intent.putExtra(ViewExerciseActivity.EXERCISE_ID, exerciseItem.title)
                startActivity(intent)
            }
        }
    }
}