package com.example.workout_diary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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

        val userInfo = Authentication.instance.getUserInfo() as User

        val username = view.findViewById<TextView>(R.id.profile_username)
        val birthdate = view.findViewById<TextView>(R.id.profile_birthdate)
        val weight = view.findViewById<TextView>(R.id.profile_weight)
        val goalWeight = view.findViewById<TextView>(R.id.profile_goalweight)

        if(userInfo != null){
            username.text = userInfo.username
            birthdate.text = userInfo.dateOfBirth
            weight.text = userInfo.weight
            goalWeight.text = userInfo.goalWeight
        }

        return view
    }

}
