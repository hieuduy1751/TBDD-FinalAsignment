package com.example.finalassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class editFolder extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    Context context;
    String folderName;
    String docId;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_folder);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.getString("docId") != null) {
                docId = extras.getString("docId");
            }

            if (extras.getString("folderName") != null) {
                folderName = extras.getString("folderName");
            }
        }

        TextView txtName = (TextView) findViewById(R.id.txtName3);
        Button btnSave = (Button) findViewById(R.id.btnSaveFolder);
        Button btnDel = (Button) findViewById(R.id.btnDelFolder);
        ImageButton btnLogout = (ImageButton) findViewById(R.id.btnLogoutL3);
        ImageButton btnHome = (ImageButton) findViewById(R.id.btnHome2);
        EditText edtName = (EditText) findViewById(R.id.txtFolderName111);

        if (folderName != null) {
            edtName.setText(folderName);
        }

        if(mAuth.getCurrentUser() != null) {
            user = mAuth.getCurrentUser();
            txtName.setText(user.getDisplayName());
        } else {
            openWelcome();
        }

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                openWelcome();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("folders")
                    .document(docId)
                    .update("name", edtName.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                folderName = edtName.getText().toString();
                                openDetailFolder();
                                Log.d("update folder", "DocumentSnapshot successfully updated!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("update folder", "Error updating document", e);
                            }
                        });

            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("folders")
                        .document(docId)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                openTodo();
                                Log.d("update folder", "DocumentSnapshot successfully updated!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("update folder", "Error updating document", e);
                                openTodo();
                            }
                        });
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTodo();
            }
        });

    }

    public void openWelcome() {
        Intent intent = new Intent(this, welcome.class);
        startActivity(intent);
    }

    public void openDetailFolder() {
        Intent intent = new Intent(this, detail.class);
        intent.putExtra("docId", docId);
        intent.putExtra("folderName", folderName);
        startActivity(intent);
    }

    public void openTodo() {
        Intent intent = new Intent(this, todo.class);
        startActivity(intent);
    }
}