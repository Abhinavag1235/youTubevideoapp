package com.app.toyo.youtubevideosapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context mctx;
    ArrayList<Blog> blogs;
    public ViewGroup parent;
    public int position;


    public MyAdapter(Context ctx,ArrayList<Blog> blo){
        mctx=ctx;
        blogs=blo;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(mctx).inflate(R.layout.blog_row,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.Title.setText(blogs.get(i).getTitle());
        myViewHolder.Desc.setText(blogs.get(i).getDesc());
        myViewHolder.url=blogs.get(i).getLink();
    }

    @Override
    public int getItemCount() {
        return blogs.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Title,Desc;
        String url;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Title=(TextView)itemView.findViewById(R.id.post_Title);
            Desc=(TextView)itemView.findViewById(R.id.post_description);

        }
    }
}
