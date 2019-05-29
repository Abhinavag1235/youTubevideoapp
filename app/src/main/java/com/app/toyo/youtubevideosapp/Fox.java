package com.app.toyo.youtubevideosapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Vector;

public class Fox extends AppCompatActivity {

    private RecyclerView bBlock;
    private DatabaseReference bdr;
    ArrayList<Blog>  list;
    MyAdapter adapter;

     static Vector<YouTubeVideos> youtubeVideos = new Vector<YouTubeVideos>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fox);
        bdr= FirebaseDatabase.getInstance().getReference().child("Users");
        bdr.keepSynced(true);
        bBlock=(RecyclerView) findViewById(R.id.recyclerView);
        bBlock.setHasFixedSize(true);
        bBlock.setLayoutManager(new LinearLayoutManager(this));


        list=new ArrayList<Blog>();


        bdr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                  Blog blog =ds.getValue(Blog.class);
                    list.add(blog);
                }
                adapter=new MyAdapter(Fox.this,list);
                bBlock.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        /*new code
        blog=new Blog();
        list=new ArrayList<>();
        adapter=new ArrayAdapter<String>(this,R.layout.blog_row,R.id.post_Title,list);
        bdr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds:dataSnapshot.getChildren()){
                        blog=ds.getValue(Blog.class);
                        list.add(blog.getTitle().toString()+ " " + blog.getDesc().toString());
                    }
                    bBlock.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
*/




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item1:
                Intent homeintent=new Intent(Fox.this,Authentication.class);
                startActivity(homeintent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
  /*  @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Blog,Main2Activity.BlogViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Blog, Main2Activity.BlogViewHolder>
                (Blog.class,R.layout.blog_row,Main2Activity.BlogViewHolder.class,bdr) {
            @Override
            protected void populateViewHolder(Main2Activity.BlogViewHolder viewHolder, Blog model, int position) {
                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setVedio(model.getLink());
            }
        };
        bBlock.setAdapter(firebaseRecyclerAdapter);
    }
    public class BlogViewHolder extends RecyclerView.ViewHolder{
        View mview;
        public  BlogViewHolder(View itemView){
            super(itemView);
            mview=itemView;
        }
        public void setTitle(String title){
            TextView post_Title=mview.findViewById(R.id.post_Title);
            post_Title.setText(title);
        }
        public void setDesc(String desc){
            TextView post_Title=mview.findViewById(R.id.post_description);
            post_Title.setText(desc);
        }
        public void setVedio(String link){
            String url=link;
            youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src="+url+" frameborder=\"0\" allowfullscreen></iframe>") );


            VideoAdapter videoAdapter = new VideoAdapter(youtubeVideos);

            bBlock.setAdapter(videoAdapter);
        }
    }
    */
}


