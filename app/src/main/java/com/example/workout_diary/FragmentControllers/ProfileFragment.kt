package com.example.workout_diary.FragmentControllers

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.workout_diary.*
import com.example.workout_diary.ActivityControllers.ProfileSettingsActivity
import com.example.workout_diary.ActivityControllers.SignInActivity
import com.example.workout_diary.FirebaseControllers.Authentication
import com.example.workout_diary.FirebaseControllers.FirebaseDb

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val userInfo = Authentication.instance.getUserInfo()

        val username = view.findViewById<TextView>(R.id.profile_username)
        val birthdate = view.findViewById<TextView>(R.id.profile_dateOfBirth)
        val weight = view.findViewById<TextView>(R.id.profile_weight)
        val goalWeight = view.findViewById<TextView>(R.id.profile_goalWeight)
        val name = view.findViewById<TextView>(R.id.profile_name)
        val height = view.findViewById<TextView>(R.id.profile_height)
        val gender = view.findViewById<TextView>(R.id.profile_gender)
        val settingsButton = view.findViewById<Button>(R.id.profile_settingsButton)
        val deleteButton = view.findViewById<Button>(R.id.profile_deleteButton)

        if(userInfo != null){
            username.text = userInfo.username
            birthdate.text = resources.getString(R.string.dateOfBirth)+ ": " + userInfo.dateOfBirth
            weight.text = resources.getString(R.string.weight) + ": " + userInfo.weight + " kg"
            goalWeight.text = resources.getString(R.string.goalWeight) + ": " + userInfo.goalWeight + " kg"
            name.text = resources.getString(R.string.name) + ": " + userInfo.firstName + " " + userInfo.lastName
            height.text = resources.getString(R.string.height) + ": " + userInfo.height + " cm"
            gender.text = resources.getString(R.string.gender) + ": " + userInfo.gender
        }

        settingsButton.setOnClickListener {
            startActivity(Intent(view.context, ProfileSettingsActivity::class.java))
        }

        deleteButton.setOnClickListener {
            AlertDialog.Builder(view.context)
                .setTitle(R.string.deleteAccount).setMessage(
                    R.string.areYouSureDeleteAccount
                )
                .setPositiveButton(R.string.yes){
                    dialog, which ->

                    FirebaseDb.instance.deleteUserByAuthUserId(
                        Authentication.instance.getAuth().uid as String)
                    Authentication.instance.getAuth().signOut()
                    Authentication.instance.getAuth().currentUser?.reload()
                    startActivity(Intent(view.context, SignInActivity::class.java))
                }
                .setNegativeButton(R.string.no){
                    dialog, which ->
                }
                .show()
        }

        return view
    }
}
