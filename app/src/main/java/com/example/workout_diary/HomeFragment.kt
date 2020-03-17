package com.example.workout_diary

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CalendarView
import android.widget.ListView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.Timestamp
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.log

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
        val formatter = SimpleDateFormat("yyyy/MM/dd")
        currentDate = formatter.format(datefake).toString()
        updatelist(thisWeeksActivities as ListView,currentDate as String)



        calanderView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            var datefake = Date(year -1900,month,dayOfMonth)
            currentDate = formatter.format(datefake).toString()
            updatelist(thisWeeksActivities as ListView,currentDate)
        }

        thisWeeksActivities.onItemClickListener = AdapterView.OnItemClickListener{ parent, view, position, id ->
            val workoutItem = thisWeeksActivities.adapter.getItem(position) as Workout
            val intent = Intent(context, ViewWorkout::class.java)
            intent.putExtra(ViewWorkout.WORKOUT_ID, workoutItem.id)
            startActivity(intent)
        }

        val addWorkoutButton = view.findViewById<FloatingActionButton>(R.id.main_add_workout_button)
        addWorkoutButton.setOnClickListener {
            val intent = Intent(view.context,activity_add_workout::class.java)
            intent.putExtra(activity_add_workout.DATE_FROM_HOME,currentDate)
            startActivity(intent)
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        val thisWeeksActivities = view?.findViewById<ListView>(R.id.main_list_view)
        updatelist(thisWeeksActivities as ListView,currentDate)

    }
    fun updatelist(thisWeeksActivities: ListView,currentDate:String){

        Log.d("onstart currentdate",currentDate)
        FirebaseDb.instance.getAllworkoutsFromUserOnDay(Authentication.instance.getAuth().uid as String,currentDate) { result ->
            result.onSuccess{
                var workoutList : MutableList<Workout> = mutableListOf<Workout>()
                for (userWorkout in it){
                    workoutList.add(workoutRepository.getWorkoutById(userWorkout.workoutId) as Workout)
                }
                thisWeeksActivities?.adapter = ArrayAdapter<Workout>(
                    view?.context as Context,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    workoutList
                )
            }
            result.onFailure{
                Log.d("onFailure",it.toString())
            }
        }
    }
}