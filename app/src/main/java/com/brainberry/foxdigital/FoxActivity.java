package com.brainberry.foxdigital;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.firebase.database.*;

import java.util.ArrayList;

/**
 * This is the first activity that is launched on opening the app.<br>
 * The activity has a <code>RecyclerView</code> which lists all the video data fetching it from Firebase.<br><br>
 *
 * The two option menus can be used to add video data or delete video data, after an authentication.
 *
 * @author Shayak Banerjee
 * @version 0.4
 * @since 8th June, 2019
 */
public class FoxActivity extends AppCompatActivity {

    @BindView(R.id.recyclerViewFeed)
    RecyclerView recyclerViewFeed;

    private YoutubeRecyclerAdapter mRecyclerAdapter;
    // LayoutManager for mRecyclerAdapter
    private RecyclerView.LayoutManager mLayoutManager;

    /**Used by onMenuItemClicked() to determine which Activity to be launched next*/
    public static final int LAUNCH_ADD_VIDEO = 0;
    /**Used by onMenuItemClicked() to determine which Activity to be launched next*/
    public static final int LAUNCH_DELETE_VIDEO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fox);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("videos");
        // for realtime updation of the video data
        databaseReference.keepSynced(true);

        /* will contain the list of YoutubeVideo objects that contain information about videos. The data is fetched
        from the Firebase database*/
        final ArrayList<YoutubeVideo> mYoutubeVideo = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            // When data in the Firebase database changes
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {
                // clearing the list of videos before it is repopulated
                mYoutubeVideo.clear();

                // setup of the mRecyclerAdapter
                mRecyclerAdapter = new YoutubeRecyclerAdapter(mYoutubeVideo, FoxActivity.this);
                mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerViewFeed.setLayoutManager(mLayoutManager);
                recyclerViewFeed.setItemAnimator(new DefaultItemAnimator());
                recyclerViewFeed.setAdapter(mRecyclerAdapter);

                for (DataSnapshot dataSnapshot : ds.getChildren()) {
                    // detais of the video are fetched from firebase
                    String description = dataSnapshot.child("description").getValue().toString();
                    String link1 = dataSnapshot.child("link1").getValue().toString();
                    String link2 = dataSnapshot.child("link2").getValue().toString();
                    String offerLink = dataSnapshot.child("offerLink").getValue().toString();
                    String thumbnailLink = dataSnapshot.child("thumbnailLink").getValue().toString();
                    String title = dataSnapshot.child("title").getValue().toString();
                    String videoId = dataSnapshot.child("videoId").getValue().toString();

                    // the video
                    YoutubeVideo video = new YoutubeVideo(title, videoId, thumbnailLink, description, offerLink, link1, link2);

                    mYoutubeVideo.add(video);
                    Log.d("TAG", video.getTitle());
                }

                // reloading the recycler view as the data has changed
                mRecyclerAdapter.notifyDataSetChanged();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // do nothing
                Log.d("TAG", "The read failed :" + databaseError);
            }
        });

        ButterKnife.bind(this);
    }

    /**
     * invoked on creation of the menu
     *
     * @param menu options menu
     * @return true on creation of the optionsMenu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * invoked on clicking a menu item
     *
     * @param item menu item from the options menu
     * @return <code>true</code> if the next activity is launched
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent homeIntent;
        switch (id) {
            case R.id.menuItemAddVideo:
                homeIntent = new Intent(FoxActivity.this, AuthenticationActivity.class);
                homeIntent.putExtra("nextActivity", LAUNCH_ADD_VIDEO);
                startActivity(homeIntent);
                return true;

            case R.id.menuItemDeleteVideo:
                homeIntent = new Intent(FoxActivity.this, AuthenticationActivity.class);
                homeIntent.putExtra("nextActivity", LAUNCH_DELETE_VIDEO);
                startActivity(homeIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
