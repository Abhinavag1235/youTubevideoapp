package com.brainberry.foxdigital

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_authentication.*

class AuthenticationActivity : AppCompatActivity() {
    private var nextActivity: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        // This value is sent from FoxAcivity, which determines post auth, which activity will launch
        nextActivity = intent.getIntExtra("nextActivity", -1)
    }

    fun submit(@Suppress("UNUSED_PARAMETER") view: View) {
        if (editTextUsername.text.toString() == "Brainberry"
            && editTextPassword.text.toString() == "12345") {

            Toast.makeText(this, "Logged In Successfully", Toast.LENGTH_SHORT).show()

            val intent: Intent
            if (nextActivity == FoxActivity.LAUNCH_ADD_VIDEO) {
                intent = Intent(this, AddVideoActivity::class.java)
                startActivity(intent)
            } else if (nextActivity == FoxActivity.LAUNCH_DELETE_VIDEO) {
                intent = Intent(this, DeleteVideoActivity::class.java)
                startActivity(intent)
            }
        } else {
            Toast.makeText(this, "Invalid Username or Password", Toast.LENGTH_SHORT).show()
        }
    }
}
