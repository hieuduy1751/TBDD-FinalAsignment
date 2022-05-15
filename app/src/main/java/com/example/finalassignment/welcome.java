package com.example.finalassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class welcome extends AppCompatActivity {
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button btnLogin = (Button) findViewById(R.id.btn_signin);
        Button btnRegister = (Button) findViewById(R.id.btn_signup);
        Button btnGG = (Button) findViewById(R.id.btnGG);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogin();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegister();
            }
        });

        btnGG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // todo login with gg logic code
            }
        });
    }

    public void openLogin(){
        Intent intent = new Intent(this.context, login.class);
        startActivity(intent);
    }

    public void openRegister(){
        Intent intent = new Intent(this.context, register.class);
        startActivity(intent);
    }
}