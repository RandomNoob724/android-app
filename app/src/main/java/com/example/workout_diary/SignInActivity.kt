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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.concurrent.timerTask



open class SignInActivity : AppCompatActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    var GOOGLE_SIGN = 123

    @RequiresApi(Build.VERSION_CODES.O)

    override fun onStart() {
        super.onStart()
        //Get the current instance of authentication and set it in the singleton
        auth = FirebaseAuth.getInstance()
        Authentication.instance.setAuth(auth)

        if(auth.currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            GlobalScope.async{
                yourWorkoutRepository.yourWorkouts = FirebaseDb.instance.getAllWorkoutsFromUser(auth.uid as String)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val signInButton = this.findViewById<Button>(R.id.start_signup)
        val logInButton = this.findViewById<Button>(R.id.start_login)
        val signInWithGoogleButton = this.findViewById<Button>(R.id.signin_google)

        createRequest()


        signInButton.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        logInButton.setOnClickListener {
            startActivity(Intent(this, LogInActivity::class.java))
        }


        signInWithGoogleButton.setOnClickListener{
            signInWithGoogle()
        }
    }

    fun signInWithGoogle(){
        val intent = googleSignInClient.signInIntent
        startActivityForResult(intent, GOOGLE_SIGN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == GOOGLE_SIGN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("GoogleAuth", "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("GoogleAuth", "signInWithCredential:success")
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("GoogleAuth", "signInWithCredential:failure", task.exception)
                }
            }
    }

    fun createRequest(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }
}

