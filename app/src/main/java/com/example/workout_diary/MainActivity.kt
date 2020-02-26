package com.example.workout_diary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout

class MainActivity : AppCompatActivity() {

    lateinit var drawer: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)

        val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()


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

    override fun onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.END)
        } else {
            super.onBackPressed()
        }
    }
}
