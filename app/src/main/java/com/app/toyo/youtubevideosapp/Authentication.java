package com.app.toyo.youtubevideosapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Authentication extends AppCompatActivity {
EditText user,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        user=(EditText)findViewById(R.id.username);
        pass=(EditText)findViewById(R.id.password);

    }

    public void Submit(View view) {
        String users=user.getText().toString();
        String passs=pass.getText().toString();
        if (users.equals("Brainberry") && passs.equals("12345"))
        {

            Toast.makeText(this,"Logged In Successfully",Toast.LENGTH_SHORT).show();
            Intent i=new Intent(this,UpdateDatabase.class);
            startActivity(i);
        }
        else
        {
            Toast.makeText(this,"Invalid Username or Password",Toast.LENGTH_SHORT).show();
        }
        

    }
}
