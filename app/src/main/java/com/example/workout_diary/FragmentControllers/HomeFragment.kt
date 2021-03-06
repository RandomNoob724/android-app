package com.example.workout_diary.FragmentControllers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.workout_diary.*
import com.example.workout_diary.ActivityControllers.AddWorkoutActivity
import com.example.workout_diary.ActivityControllers.ViewWorkoutActivity
import com.example.workout_diary.Classes.Workout
import com.example.workout_diary.Repositories.workoutRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment: Fragment() {

    private var currentDate : String = ""
    private val formatter = SimpleDateFormat("yyyy/MM/dd")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container,false)
        val thisWeeksActivities = view.findViewById<ListView>(R.id.main_list_view)
        val calenderView = view.findViewById<CalendarView>(R.id.main_calendar_view)

        currentDate = formatter.format(calenderView.date).toString()
        reloadTodaysWorkoutList(thisWeeksActivities, calenderView.context)

        calenderView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            //In order to get the current date you for some reason need to take the year - 1900
            currentDate = formatter.format(Date(year-1900, month, dayOfMonth)).toString()

            reloadTodaysWorkoutList(thisWeeksActivities, view.context)

            //Change the size of the listview depending on the numbers of elements inside of it.
            setListViewHeightBasedOnItems(thisWeeksActivities)
        }

        thisWeeksActivities.onItemClickListener = AdapterView.OnItemClickListener{ parent, view, position, id ->
            val workoutItem = thisWeeksActivities.adapter.getItem(position) as Workout
            val intent = Intent(context, ViewWorkoutActivity::class.java)
            intent.putExtra(ViewWorkoutActivity.WORKOUT_ID, workoutItem.id)
            startActivity(intent)
        }

        val addWorkoutButton = view.findViewById<FloatingActionButton>(R.id.main_add_workout_button)
        addWorkoutButton.setOnClickListener {
            val intent = Intent(view.context,
                AddWorkoutActivity::class.java)
            intent.putExtra(AddWorkoutActivity.DATE_FROM_HOME,currentDate)
            startActivity(intent)
        }

        return view
    }

    //Function for setting the size of the list depending on the number of items in the list
    private fun setListViewHeightBasedOnItems(listView: ListView): Boolean {
        val listAdapter = listView.adapter
        return if (listAdapter != null) {
            val numberOfItems = listAdapter.count

            // Get total height of all items.
            var totalItemsHeight = 0
            for (itemPos in 0 until numberOfItems) {
                val item = listAdapter.getView(itemPos, null, listView)
                val px = 500 * listView.resources.displayMetrics.density
                item.measure(
                    View.MeasureSpec.makeMeasureSpec(
                        px.toInt(),
                        View.MeasureSpec.AT_MOST
                    ),
                    View.MeasureSpec.makeMeasureSpec(
                        0,
                        View.MeasureSpec.UNSPECIFIED
                    )
                )
                totalItemsHeight += item.measuredHeight
            }

            // Get total height of all item dividers.
            val totalDividersHeight = listView.dividerHeight *
                    (numberOfItems - 1)
            // Get padding
            val totalPadding = listView.paddingTop + listView.paddingBottom

            // Set list height.
            val params = listView.layoutParams
            params.height = totalItemsHeight + totalDividersHeight + totalPadding
            listView.layoutParams = params
            listView.requestLayout()
            true
        } else {
            false
        }
    }

    private fun reloadTodaysWorkoutList(listview: ListView, context: Context){
        var workoutList = mutableListOf<Workout>()
        workoutList.clear()
        workoutList = workoutRepository.getWorkoutsFromYourWorkoutsOnDate(currentDate)
        listview.adapter = ArrayAdapter<Workout>(
            context,
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            workoutList
        )
    }
}