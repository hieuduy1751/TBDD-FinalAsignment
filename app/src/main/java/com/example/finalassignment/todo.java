package com.example.finalassignment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class todo extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_todo);
    }

    public void openDetail() {
        Intent intent = new Intent(this, detail.class);
        startActivity(intent);
    }
}
