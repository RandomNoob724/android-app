package com.example.workout_diary

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment

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

        todaysActivities.adapter = ArrayAdapter<Workout>(
            view.context,
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            workoutRepository.getAllWorkouts()
        )

        todaysActivities.onItemClickListener = AdapterView.OnItemClickListener{ parent, view, position, id ->
            val exerciseItem = todaysActivities.adapter.getItem(position) as Workout
            val intent = Intent(context, ViewExercise::class.java)
            intent.putExtra(ViewExercise.EXERCISE_ID, exerciseItem.id)
            startActivity(intent)
        }
        return view
    }
}