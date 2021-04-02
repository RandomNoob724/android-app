package com.example.workout_diary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class FetchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetch)

        val intent = Intent(this, SignInActivity::class.java)
        val fetchLoading = findViewById<ProgressBar>(R.id.fetch_loading)
        fetchLoading.setVisibility(View.VISIBLE)

        GlobalScope.async{
            exerciseRepository.exercises = FirebaseDb.instance.getAllExercises()
            workoutRepository.workouts = FirebaseDb.instance.getAllWorkouts()
            startActivity(intent)
            finish()
        }
    }
}