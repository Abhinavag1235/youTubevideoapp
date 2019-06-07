package com.brainberry.foxdigital

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import android.widget.Toast

class AuthenticationActivity : AppCompatActivity() {
    lateinit var user: EditText
    lateinit var pass: EditText
    private var nextActivity: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        user = findViewById(R.id.username)
        pass = findViewById(R.id.password)
        nextActivity = intent.getIntExtra("nextActivity", -1)

    }

    fun Submit(@Suppress("UNUSED_PARAMETER") view: View) {
        val users = user.text.toString()
        val passs = pass.text.toString()
        if (users == "Brainberry" && passs == "12345") {

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
