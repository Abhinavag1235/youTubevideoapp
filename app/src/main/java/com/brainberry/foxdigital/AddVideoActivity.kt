package com.brainberry.foxdigital

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_update_database.*

import java.util.HashMap

/**
 * Launched when menuItemAddVideo is clicked<br>
 *
 * Takes data from user and adds it to the Firebase database
 *
 * @author Shayak Banerjee
 * @version 0.4
 * @since 6th June, 2019
 */
class AddVideoActivity : AppCompatActivity() {

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_database)

        mAuth = FirebaseAuth.getInstance()

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("videos")
    }

    // URL validity check
    private fun isValidUrl(url: String): Boolean {

        if (!url.contains("www.youtube.com")) {
            Toast.makeText(this, "Invalid Url", Toast.LENGTH_SHORT).show()
            return false
        }
        // else
        return true

    }

    fun buttonUpdateOnClick(@Suppress("UNUSED_PARAMETER") view: View) {

        var videoId = editTextVideoLink.text.toString()

        if (isValidUrl(videoId)) {

            // if the given link is not a url and is the youtube video ID instead
            if (videoId.contains("?")) {
                videoId = videoId.substring(videoId.indexOf('=') + 1)
            }

            // thumbnail link determination using videoId
            val thumbnailLink = "https://i.ytimg.com/vi/$videoId/maxresdefault.jpg"

            val youtubeVideo = YoutubeVideo(editTextTitle.text.toString(),
                videoId.trim { it <= ' ' },
                thumbnailLink,
                editTextDescription.text.toString().trim { it <= ' ' },
                editTextOfferLink.text.toString().trim { it <= ' ' },
                editTextLink1.text.toString().trim { it <= ' ' },
                editTextLink2.text.toString().trim { it <= ' ' })

            // if any required data is missing, i.e., if there is an empty field
            if (youtubeVideo.emptyFieldExists())
                Toast.makeText(this, "please fill all fields", Toast.LENGTH_SHORT).show()
            else {

                val dataUpdateWasSuccessful = booleanArrayOf(true)

                val taskMap = HashMap<String, Any>()
                taskMap["title"] = youtubeVideo.title!!
                taskMap["videoId"] = youtubeVideo.videoId!!
                taskMap["thumbnailLink"] = youtubeVideo.imageUrl!!
                taskMap["description"] = youtubeVideo.description!!
                taskMap["offerLink"] = youtubeVideo.offerLink!!
                taskMap["link1"] = youtubeVideo.link1!!
                taskMap["link2"] = youtubeVideo.link2!!
                databaseReference.child(videoId).updateChildren(taskMap)
                    .addOnFailureListener { dataUpdateWasSuccessful[0] = false }

                if (dataUpdateWasSuccessful[0])
                    Toast.makeText(this, "data added", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(this, "data could not be added", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_add_delete_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item!!.itemId){
            R.id.menuItemLogout -> {
                mAuth.signOut()
                finish()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
