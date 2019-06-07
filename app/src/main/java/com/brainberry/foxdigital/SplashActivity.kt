package com.brainberry.foxdigital

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.Handler


/**
 * This activity will display the splash screen on launch of the app.
 * The splash screen contains the logo.
 * created by Shayak Banerjee on 6th June, 2019
 */
class SplashActivity : AppCompatActivity() {

    companion object{
        /**
         * timeout of splash screen
         */
        const val SPLASH_TIMEOUT:Long = 3000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        Handler().postDelayed({
            val intent = Intent(this@SplashActivity, FoxActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_TIMEOUT)
    }
}
