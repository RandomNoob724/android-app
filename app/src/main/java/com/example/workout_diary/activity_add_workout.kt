package com.example.workout_diary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import java.text.SimpleDateFormat
import java.util.*

class activity_add_workout : AppCompatActivity() {

    companion object {
        val DATE_FROM_HOME = "DATE_FROM_HOME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_workout)

        val workoutsList = this.findViewById<ListView>(R.id.choose_workout)
        val deafultDate = SimpleDateFormat("yyyy/MM/dd").format(Date()).toString()
        val date = intent.extras?.getString(DATE_FROM_HOME, deafultDate)
        workoutsList.adapter = ArrayAdapter<Workout>(
            this,
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            workoutRepository.workouts
        )

        workoutsList.setOnItemClickListener { parent, view, position, id ->
            val workoutItem = workoutsList.adapter.getItem(position) as Workout
            FirebaseDb.instance.addWorkoutForUser(YourWorkout(date as String,Authentication.instance.getAuth().uid as String,workoutItem.id))
            FirebaseDb.instance.getAllWorkouts()
            finish()
        }
    }
}
