package com.example.workout_diary.FragmentControllers

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.workout_diary.*
import com.example.workout_diary.ActivityControllers.ViewExerciseActivity
import com.example.workout_diary.Classes.Exercise
import com.example.workout_diary.FirebaseControllers.Authentication
import com.example.workout_diary.Repositories.workoutRepository
import com.example.workout_diary.Repositories.yourWorkoutRepository
import java.text.SimpleDateFormat
import java.util.*

class YourDayFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_your_day, container,false)
        val listOfExercises = view.findViewById<ListView>(R.id.main_list_view)

        val datefake = Date()
        val formatter = SimpleDateFormat("yyyy/MM/dd")
        var date = formatter.format(datefake).toString()

        var exerciseList = mutableListOf<Exercise>()
        var workouts = yourWorkoutRepository.getAllworkoutOnDay(
            Authentication.instance.getAuth().uid.toString(), date)

        for (workout in workouts){
            val tempList = workoutRepository.getWorkoutById(workout.workoutId)?.exercises as MutableList<Exercise>
            for (exercise in  tempList){
                exerciseList.add(exercise)
            }
        }
        listOfExercises.adapter = ArrayAdapter<Exercise>(
            view.context,
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            exerciseList
        )


        listOfExercises.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val exerciseItem = listOfExercises.adapter.getItem(position) as Exercise
            val intent = Intent(view.context, ViewExerciseActivity::class.java)
            intent.putExtra(ViewExerciseActivity.EXERCISE_ID, exerciseItem.title)
            startActivity(intent)
        }


        return view
    }
}
