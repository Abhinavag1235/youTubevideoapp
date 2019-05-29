package com.app.toyo.youtubevideosapp;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Main2Activity extends AppCompatActivity {
    private RecyclerView bBlock;
    private DatabaseReference bdr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        bdr=FirebaseDatabase.getInstance().getReference().child("Users");
        bdr.keepSynced(true);

        bBlock=findViewById(R.id.recyclerView);
        bBlock.setHasFixedSize(true);
        bBlock.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Blog,BlogViewHolder>firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Blog, BlogViewHolder>
                (Blog.class,R.layout.blog_row,BlogViewHolder.class,bdr) {
            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, Blog model, int position) {
                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setVedio(model.getLink());
            }
        };
        bBlock.setAdapter(firebaseRecyclerAdapter);
    }
    public  static  class BlogViewHolder extends RecyclerView.ViewHolder{
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
        public void setVedio( String link){
            String url=link;
        }
    }
}
