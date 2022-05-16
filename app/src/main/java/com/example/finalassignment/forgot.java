package com.example.finalassignment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgot extends AppCompatActivity {

    FirebaseAuth auth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        EditText txtEmail = (EditText) findViewById(R.id.edt_forgot_email);
        Button btnGetEmail = (Button) findViewById(R.id.btnGetEmail);
        Button btnGG = (Button) findViewById(R.id.btnGG10);
        Button btnRegister = (Button) findViewById(R.id.btnRGLG2);
        auth = FirebaseAuth.getInstance();
        btnGetEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // todo get email logic
                auth.sendPasswordResetEmail(txtEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            Log.d("Status", "Email sent.");
                        };
                    }
                });

            }
        });

        btnGG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // todo login with gg
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegister();
            }
        });


    }

    public void openRegister() {
        Intent intent = new Intent(this, register.class);
        startActivity(intent);
    }
}
