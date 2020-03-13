package com.example.workout_diary

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.*

class YourWeekFragment : Fragment() {

    companion object {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_your_week, container,false)
        val todaysActivities = view.findViewById<ListView>(R.id.main_list_view)

        val datefake = Date()
        val formatter = SimpleDateFormat("yyyy/MM/dd")
        var date = formatter.format(datefake).toString()

        Log.d("hejsan datum",date)
        FirebaseDb.instance.getAllworkoutsFromUserOnDay(Authentication.instance.getAuth().uid as String, date) {
            it.onSuccess {
                var exerciseList : MutableList<Exercise> = mutableListOf<Exercise>()
                for (yourWorkout in it) {
                    for (exercise in workoutRepository.getWorkoutById(yourWorkout.workoutId)!!.exercises) {
                        exerciseList.add(exercise)
                    }
                }
                todaysActivities.adapter = ArrayAdapter<Exercise>(
                    view.context,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    exerciseList
                )
            }
            it.onFailure{
                Log.d("Error", it.toString())
            }
        }


        todaysActivities.onItemClickListener = AdapterView.OnItemClickListener{ parent, view, position, id ->
            val exerciseItem = todaysActivities.adapter.getItem(position) as Workout
            val intent = Intent(context, ViewExercise::class.java)
            intent.putExtra(ViewExercise.EXERCISE_ID, exerciseItem.id)
            startActivity(intent)
        }
        return view
    }
}
