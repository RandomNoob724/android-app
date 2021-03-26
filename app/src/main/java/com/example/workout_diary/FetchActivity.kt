package com.example.workout_diary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class FetchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetch)

        val intent = Intent(this, SignInActivity::class.java)

        GlobalScope.async{
            exerciseRepository.exercises = FirebaseDb.instance.getAllExercises()
            workoutRepository.workouts = FirebaseDb.instance.getAllWorkouts()
            startActivity(intent)
            finish()
        }
    }
}