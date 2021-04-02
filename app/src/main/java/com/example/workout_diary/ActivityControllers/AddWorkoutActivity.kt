package com.example.workout_diary.ActivityControllers

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.workout_diary.FirebaseControllers.Authentication
import com.example.workout_diary.R
import com.example.workout_diary.Repositories.workoutRepository
import com.example.workout_diary.Repositories.yourWorkoutRepository
import com.example.workout_diary.Classes.Workout
import com.example.workout_diary.Classes.YourWorkout
import java.text.SimpleDateFormat
import java.util.*

class AddWorkoutActivity : AppCompatActivity() {

    companion object {
        val DATE_FROM_HOME = "DATE_FROM_HOME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_workout)

        val workoutsListView = this.findViewById<ListView>(R.id.choose_workout)
        val defaultDate = SimpleDateFormat("yyyy/MM/dd").format(Date()).toString()
        val date = intent.extras?.getString(DATE_FROM_HOME, defaultDate)

        workoutsListView.adapter = ArrayAdapter<Workout>(
            this as Context,
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            workoutRepository.workouts
        )


        workoutsListView.setOnItemClickListener { parent, view, position, id ->
            val workoutItem = workoutsListView.adapter.getItem(position) as Workout
            yourWorkoutRepository.addYourWorkout(
                YourWorkout(
                    date as String,
                    Authentication.instance.getAuth().uid as String,
                    workoutItem.id
                )
            )
            finish()
        }
    }
}
