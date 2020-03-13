package com.example.workout_diary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_profile.*
import org.w3c.dom.Text

class ProfileFragment : Fragment() {

    companion object{
        val EXTRA_USERNAME = "EXTRA_USERNAME"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val userInfo = Authentication.instance.getUserInfo()

        val username = view.findViewById<TextView>(R.id.profile_username)
        val birthdate = view.findViewById<TextView>(R.id.profile_birthdate)
        val weight = view.findViewById<TextView>(R.id.profile_weight)
        val goalWeight = view.findViewById<TextView>(R.id.profile_goalweight)
        val settingsButton = view.findViewById<Button>(R.id.profile_settingsButton)
        val deleteButton = view.findViewById<Button>(R.id.profile_deleteButton)

        if(userInfo != null){
            username.text = userInfo.username
            birthdate.text = userInfo.dateOfBirth
            weight.text = userInfo.gender
            goalWeight.text = userInfo.goalWeight
        }

        settingsButton.setOnClickListener {
            startActivity(Intent(view.context, ProfileSettingsActivity::class.java))
        }

        deleteButton.setOnClickListener {
            AlertDialog.Builder(view.context)
                .setTitle(R.string.deleteAccount).setMessage(R.string.areYouSureDeleteAccount)
                .setPositiveButton(R.string.yes){
                    dialog, which ->
                    FirebaseDb.instance.deleteUserByAuthUserId(Authentication.instance.getAuthUserId())
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
