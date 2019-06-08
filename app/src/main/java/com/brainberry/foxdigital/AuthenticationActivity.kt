package com.brainberry.foxdigital

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_authentication.*

class AuthenticationActivity : AppCompatActivity() {
    private var nextActivity: Int? = null

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        // This value is sent from FoxAcivity, which determines post auth, which activity will launch
        nextActivity = intent.getIntExtra("nextActivity", -1)

        editTextUsername.setText(R.string.foxdigital_email)

        mAuth = FirebaseAuth.getInstance()

        editTextPassword.setOnKeyListener { v, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_ENTER){
                authenticateUser()
                return@setOnKeyListener true
            } else return@setOnKeyListener false
        }

        progressBar.visibility = View.INVISIBLE

    }

    public override fun onStart() {
        super.onStart()

        val currentUser = mAuth.currentUser
        if (currentUser != null)
            launchNextActivity()

    }

    /**
     * flipVisibility(false) will hide all widgets and make the progress bar visible
     * flipVisibility(true) will hide the progress bar and make everything else visible
     */
    private fun flipVisiblity(visiblility: Boolean){
        var viewVisibility = View.INVISIBLE
        var progressBarVisibility = View.VISIBLE
        if(visiblility){
            viewVisibility = View.VISIBLE
            progressBarVisibility = View.INVISIBLE
        }
        editTextUsername.visibility = viewVisibility
        editTextPassword.visibility = viewVisibility
        buttonSubmit.visibility = viewVisibility
        imageView.visibility = viewVisibility
        progressBar.visibility = progressBarVisibility

    }

    private fun authenticateUser(){

        flipVisiblity(false)

        mAuth.signInWithEmailAndPassword(editTextUsername.text.toString(), editTextPassword.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Authentication successful", Toast.LENGTH_SHORT).show()
                    launchNextActivity()
                } else {
                    flipVisiblity(true)
                    Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show()
                }
            }


    }

    fun buttonSubmitOnClick(@Suppress("UNUSED_PARAMETER") view: View) {
        authenticateUser()
    }

    private fun launchNextActivity() {
        val intent: Intent
        if (nextActivity == FoxActivity.LAUNCH_ADD_VIDEO) {
            intent = Intent(this, AddVideoActivity::class.java)
            startActivity(intent)
            finish()
        } else if (nextActivity == FoxActivity.LAUNCH_DELETE_VIDEO) {
            intent = Intent(this, DeleteVideoActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
