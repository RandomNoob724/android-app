package com.example.workout_diary.ActivityControllers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import com.example.workout_diary.FirebaseControllers.FirebaseDb
import com.example.workout_diary.R
import com.example.workout_diary.Repositories.exerciseRepository
import com.example.workout_diary.Repositories.workoutRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class FetchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetch)

        val intent = Intent(this, SignInActivity::class.java)
        val fetchLoading = findViewById<ProgressBar>(R.id.fetch_loading)
        fetchLoading.visibility = View.VISIBLE

        /*
        Before the application starts fetch all of the data needed for the app to function from the firestore database.
        Do this async such that the app don't continue without the necessary data
        First get all exercises that the workouts depend on
         */
        GlobalScope.async{
            exerciseRepository.exercises = FirebaseDb.instance.getAllExercises()
            workoutRepository.workouts = FirebaseDb.instance.getAllWorkouts()
            startActivity(intent)
            finish()
        }
    }
}