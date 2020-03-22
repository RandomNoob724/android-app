package com.example.workout_diary

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.annotation.RequiresApi
import com.google.firebase.Timestamp
import kotlinx.coroutines.delay
import java.sql.Date
import java.util.*
import android.util.Log
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

import java.io.File
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.concurrent.timerTask



open class SignInActivity : AppCompatActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    var GOOGLE_SIGN = 123
    @RequiresApi(Build.VERSION_CODES.O)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        val signInButton = this.findViewById<Button>(R.id.start_signup)
        val logInButton = this.findViewById<Button>(R.id.start_login)
        val skipLoginButton = this.findViewById<Button>(R.id.start_skipLogin)
        val signInWithGoogle = this.findViewById<Button>(R.id.signin_google)

        val auth = FirebaseAuth.getInstance()
        Authentication.instance.setAuth(auth)
        FirebaseDb.instance.getAllExercises()


        if (auth.currentUser != null) {
            FirebaseDb.instance.getUserByAuthUserId()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        signInButton.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        logInButton.setOnClickListener {
            startActivity(Intent(this, LogInActivity::class.java))
        }

        skipLoginButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        signInWithGoogle.setOnClickListener{
            signInWithGoogle()
        }

    }

    override fun onStart() {
        super.onStart()
        auth = FirebaseAuth.getInstance()
        Authentication.instance.setAuth(auth)
        if(auth.currentUser != null){
            FirebaseDb.instance.getUserByAuthUserId()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    fun signInWithGoogle(){
        val intent = googleSignInClient.signInIntent
        startActivityForResult(intent, GOOGLE_SIGN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data!!)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    firebaseAuthWithGoogle(account)
                }
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("Google sign fail", "Google sign in failed", e)
                // ...
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        Log.d("TAG", "firebaseAuthWithGoogle" + account.id)
        val user = Authentication.instance.getUserInfo()
        Log.d("this user", user.toString())
        val credential : AuthCredential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    startActivity(Intent(this, SignUpActivity::class.java))

                } else {
                    Toast.makeText(this, resources.getString(R.string.signInFailed), Toast.LENGTH_SHORT).show()
                }
            }
    }
}

