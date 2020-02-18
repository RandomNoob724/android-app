package com.example.workout_diary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainCalendarView = this.findViewById<CalendarView>(R.id.main_calendar_view)
        val todaysActivities = this.findViewById<ListView>(R.id.main_list_view)

        todaysActivities.adapter = ArrayAdapter<Exercise>(
            this,
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            workoutRepository.getAllExercises()
        )

        todaysActivities.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val exerciseItem = todaysActivities.adapter.getItem(position) as Exercise
            val intent = Intent(this, ViewExercise::class.java)
            intent.putExtra(ViewExercise.EXERCISE_ID, exerciseItem.id)
            startActivity(intent)
        }
    }
}
