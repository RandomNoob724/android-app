package com.example.workout_diary

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CalendarView
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment: Fragment() {

    var currentDate : String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container,false)
        val thisWeeksActivities = view.findViewById<ListView>(R.id.main_list_view)
        val calanderView = view.findViewById<CalendarView>(R.id.main_calendar_view)

        val datefake = Date()
        Log.d("hasse dagendatum",datefake.toString())
        val formatter = SimpleDateFormat("yyyy/MM/dd")
        currentDate = formatter.format(datefake).toString()
        Log.d("hasse dagensdatum igen",currentDate)

        var workoutList = mutableListOf<Workout>()
        var workoutYourList = FirebaseDb.instance.getAllworkoutsFromUserOnDay(Authentication.instance.getAuth().uid as String,currentDate)
        for (workoutYour in workoutYourList){
            workoutList.add(workoutRepository.getWorkoutById(workoutYour.workoutId) as Workout)
        }
        Log.d("dagen workoutlist",workoutList.toString())
        thisWeeksActivities.adapter = ArrayAdapter<Workout>(
            view.context as Context,
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            workoutList
        )

        calanderView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            var datefake = Date(year -1900,month,dayOfMonth)
            currentDate = formatter.format(datefake).toString()

            var workoutList = mutableListOf<Workout>()
            var workoutYourList = FirebaseDb.instance.getAllworkoutsFromUserOnDay(Authentication.instance.getAuth().uid as String,currentDate)
            for (workoutYour in workoutYourList){
                workoutList.add(workoutRepository.getWorkoutById(workoutYour.workoutId) as Workout)
            }
            thisWeeksActivities.adapter = ArrayAdapter<Workout>(
                view?.context as Context,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                workoutList
            )
        }

        thisWeeksActivities.onItemClickListener = AdapterView.OnItemClickListener{ parent, view, position, id ->
            val workoutItem = thisWeeksActivities.adapter.getItem(position) as Workout
            val intent = Intent(context, ViewWorkout::class.java)
            intent.putExtra(ViewWorkout.WORKOUT_ID, workoutItem.id)
            startActivity(intent)
        }

        val addWorkoutButton = view.findViewById<FloatingActionButton>(R.id.main_add_workout_button)
        addWorkoutButton.setOnClickListener {
            val intent = Intent(view.context,AddWorkoutActivity::class.java)
            intent.putExtra(AddWorkoutActivity.DATE_FROM_HOME,currentDate)
            startActivity(intent)
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        val thisWeeksActivities = view?.findViewById<ListView>(R.id.main_list_view)


        var workoutList = mutableListOf<Workout>()
        var workoutYourList = FirebaseDb.instance.getAllworkoutsFromUserOnDay(Authentication.instance.getAuth().uid as String,currentDate)
        for (workoutYour in workoutYourList){
            workoutList.add(workoutRepository.getWorkoutById(workoutYour.workoutId) as Workout)
        }
        thisWeeksActivities?.adapter = ArrayAdapter<Workout>(
            view?.context as Context,
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            workoutList
        )

    }
}