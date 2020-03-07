package com.example.workout_diary

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class ViewWorkout : AppCompatActivity(){
    companion object {
        const val WORKOUT_ID = "Workout_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_workout)
        val listOfExercises = this.findViewById<ListView>(R.id.todays_exercises_list)

        val workoutId = intent.getIntExtra(WORKOUT_ID, 28349290)
        Log.d("logged", workoutId.toString())
        val workout = workoutRepository.getWorkoutById(workoutId)
        if(workout != null){
            listOfExercises.adapter = ArrayAdapter<Exercise>(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                workout.listOfExercises
            )

            listOfExercises.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                val exerciseItem = listOfExercises.adapter.getItem(position) as Exercise
                val intent = Intent(view.context, ViewExercise::class.java)
                intent.putExtra(ViewExercise.EXERCISE_ID, exerciseItem.id)
                startActivity(intent)
            }
        }
    }
}