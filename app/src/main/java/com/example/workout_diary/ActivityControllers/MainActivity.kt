package com.example.workout_diary.ActivityControllers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.workout_diary.FirebaseControllers.Authentication
import com.example.workout_diary.FragmentControllers.HomeFragment
import com.example.workout_diary.FragmentControllers.ProfileFragment
import com.example.workout_diary.FragmentControllers.YourDayFragment
import com.example.workout_diary.R
import com.example.workout_diary.Repositories.UserRepository
import com.example.workout_diary.Repositories.yourWorkoutRepository
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    companion object {
        val EXTRA_USERNAME = "USERNAME"
    }

    lateinit var drawer: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)

        val navigationView = this.findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        supportFragmentManager.beginTransaction().replace(
            R.id.fragment_container,
            HomeFragment()
        ).commit()
        navigationView.setCheckedItem(R.id.nav_home)

        val toggle = ActionBarDrawerToggle(this, drawer, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_your_week -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container,
                    YourDayFragment()
                ).commit()
            }
            R.id.nav_home -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container,
                    HomeFragment()
                ).commit()
            }
            R.id.nav_profile -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container,
                    ProfileFragment()
                ).commit()
            }
            R.id.nav_logout -> {
                yourWorkoutRepository.resetYourWorkoutList()
                UserRepository.instance.removeActiveUser(Authentication.instance.getUserInfo())
                Authentication.instance.getAuth().signOut()
                Authentication.instance.getAuth().currentUser?.reload()
                startActivity(Intent(this,
                    SignInActivity::class.java))
                finish()
            }
        }
        return true
    }

    override fun onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.END)
        } else {
            super.onBackPressed()
        }
    }
}
