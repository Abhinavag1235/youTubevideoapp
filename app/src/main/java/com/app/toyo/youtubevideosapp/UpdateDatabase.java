package com.app.toyo.youtubevideosapp;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateDatabase extends AppCompatActivity {
    FirebaseAuth auth;
FirebaseDatabase db;
DatabaseReference users;
EditText title,desc,link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_database);
        db=FirebaseDatabase.getInstance();
        users=db.getReference("Users");

        title=(EditText)findViewById(R.id.vid_title);
        desc=(EditText)findViewById(R.id.vid_desc);
        link=(EditText)findViewById(R.id.vid_link);


    }
    private boolean isValidUrl(String url)
    {

        if (url==null)
        {
            return  false;

        }
            if (url.contains("www.youtube.com"))
            {
                Toast.makeText(this,"Valid Url",Toast.LENGTH_SHORT).show();
                return true;
            }
            else
            {
                Toast.makeText(this,"Invalid Url",Toast.LENGTH_SHORT).show();
                return false;
            }

    }

    public void Update(View view) {
        User user = new User();
        user.setTitle(title.getText().toString());
        user.setDesc(desc.getText().toString());
        String url = link.getText().toString();
        Boolean abc = isValidUrl(url);
        user.setLink(link.getText().toString());
        if (abc) {
            users.child("Users").push().setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(UpdateDatabase.this, "Data Added", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UpdateDatabase.this, "Data Not Added", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
