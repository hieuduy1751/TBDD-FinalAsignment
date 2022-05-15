package com.example.finalassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.finalassignment.entity.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class addNewTask extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    Context context;
    String taskName;
    String taskDetail;
    String docId;
    String folderDocId;
    String folderName;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.getString("docId") != null) {
                docId = extras.getString("docId");
            }

            if (extras.getString("taskName") != null) {
                taskName = extras.getString("taskName");
            }

            if (extras.getString("taskDetail") != null) {
                taskDetail = extras.getString("taskDetail");
            }

            if (extras.getString("folderDocId") != null) {
                folderDocId = extras.getString("folderDocId");
            }

            if (extras.getString("folderName") != null) {
                folderName = extras.getString("folderName");
            }
        }

        TextView txtName = (TextView) findViewById(R.id.txtName4);
        EditText txtNameTask = (EditText) findViewById(R.id.txtNameTask);
        EditText txtDetail = (EditText) findViewById(R.id.txtDetail);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.gAddto);
        ImageButton btnHome = (ImageButton) findViewById(R.id.btnHome3);
        ImageButton btnLogout = (ImageButton) findViewById(R.id.btnLogoutL4);
        Button btnAdd = (Button) findViewById(R.id.btnSaveFolder2);
        Button btnCancel = (Button) findViewById(R.id.btnDelFolder2);

        if (docId != null) {
            btnAdd.setText("Update");
            btnCancel.setText("Delete");
            txtNameTask.setText(taskName);
            txtDetail.setText(taskDetail);
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

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTodo();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (docId == null) {
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    RadioButton rd = (RadioButton) findViewById(selectedId);
                    if (rd != null) {
                        db.collection("tasks")
                                .add(new Task(txtNameTask.getText().toString(), txtDetail.getText().toString(), rd.getText().toString(), folderDocId, user.getUid()));
                        openDetail();
                    } else {
                        db.collection("tasks")
                                .add(new Task(txtNameTask.getText().toString(), txtDetail.getText().toString(), folderDocId, user.getUid()));
                        openDetail();
                    }
                } else {
                    db.collection("tasks")
                            .document(docId)
                            .update("name", txtNameTask.getText().toString());
                    db.collection("tasks")
                            .document(docId)
                            .update("detail", txtDetail.getText().toString());
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    RadioButton rd = (RadioButton) findViewById(selectedId);
                    if (rd != null) {
                        db.collection("tasks")
                                .document(docId)
                                .update("tag", rd.getText().toString());
                    }
                    openDetail();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (docId != null)  {
                    db.collection("tasks")
                            .document(docId)
                            .delete();
                    openDetail();
                } else {
                    openDetail();
                }
            }
        });
    }

    public void openWelcome() {
        Intent intent = new Intent(this, welcome.class);
        startActivity(intent);
    }

    public void openTodo() {
        Intent intent = new Intent(this, todo.class);
        startActivity(intent);
    }

    public void openDetail() {
        Intent intent = new Intent(this, detail.class);
        intent.putExtra("docId", folderDocId);
        intent.putExtra("folderName", folderName);
        startActivity(intent);
    }
}