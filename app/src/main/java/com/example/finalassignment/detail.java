package com.example.finalassignment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalassignment.adapter.FolderAdapter;
import com.example.finalassignment.adapter.TaskAdapter;
import com.example.finalassignment.entity.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class detail extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseUser user;
    Context context;
    List<Task> listTask = new ArrayList<Task>();
    private ListView listTaskView;
    private String tag;
    private String docId;
    private String folderName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_detail);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.getString("docId") != null) {
                docId = extras.getString("docId");
            }

            if (extras.getString("folderName") != null) {
                folderName = extras.getString("folderName");
            }

            if (extras.getString("tag") != null) {
                tag = extras.getString("tag");
            }
        }

        Button btnAddTask = (Button) findViewById(R.id.btnAddTask);
        ImageButton btnLogout = (ImageButton) findViewById(R.id.btnLogoutL2);
        ImageButton btnEditFolder = (ImageButton) findViewById(R.id.btnEditFolderr);
        ImageButton btnHome = (ImageButton) findViewById(R.id.btnHome);
        TextView txtName = (TextView) findViewById(R.id.txtName2);
        TextView txtFolder = (TextView) findViewById(R.id.txtFolderName);
        listTaskView = (ListView) findViewById(R.id.listTaskView);

        if (folderName != null) {
            txtFolder.setText(folderName);
        }

        if(mAuth.getCurrentUser() != null) {
            user = mAuth.getCurrentUser();
            txtName.setText(user.getDisplayName());
            if (tag != null) {
                if (tag.equalsIgnoreCase("All")) {
                    getAllTasks();
                } else {
                    getTasks(tag);
                }
            } else {
                getTasks();
            }
            TaskAdapter adapter = new TaskAdapter(this, R.layout.task_button, listTask);
            listTaskView.setAdapter(adapter);
        } else {
            openWelcome();
        }

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddTask();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                openWelcome();
            }
        });

        btnEditFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditFolder();
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

    public void openAddTask() {
        Intent intent = new Intent(this, addNewTask.class);
        intent.putExtra("folderDocId", docId);
        intent.putExtra("folderName", folderName);
        startActivity(intent);
    }

    public void openEditFolder() {
        Intent intent = new Intent(this, editFolder.class);
        intent.putExtra("docId", docId);
        intent.putExtra("folderName", folderName);
        startActivity(intent);
    }

    public void openTodo() {
        Intent intent = new Intent(this, todo.class);
        startActivity(intent);
    }

    public void getTasks() {
        if (user != null) {
            db.collection("tasks")
                .whereEqualTo("folderId", docId)
                .orderBy("status", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.w("Load data", "Listen failed.", error);
                            return;
                        }

                        List<Task> listTaskLoad = new ArrayList<Task>();
                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.get("name") != null) {
                                listTaskLoad.add(new Task(doc.getId(), doc.getString("name"), doc.getString("detail"), doc.getString("tag"), doc.getBoolean("status"),doc.getLong("createdAt"), doc.getLong("updatedAt"), doc.getString("folderId"), doc.getString("ownerId")));
                            }
                        }
                        Collections.sort(listTaskLoad, new TaskComparator());
                        listTask = listTaskLoad;

                        Log.d("loadtask", "Thanh cong");
                        TaskAdapter adapter = new TaskAdapter(context, R.layout.task_button, listTask);
                        listTaskView.setAdapter(adapter);
                    }
                });
        }
    }

    public void getTasks(String tag) {
        if (user != null) {
            db.collection("tasks")
                    .whereEqualTo("ownerId", user.getUid())
                    .whereEqualTo("tag", tag)
                    .orderBy("status", Query.Direction.ASCENDING)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (error != null) {
                                Log.w("Load data", "Listen failed.", error);
                                return;
                            }

                            List<Task> listTaskLoad = new ArrayList<Task>();
                            for (QueryDocumentSnapshot doc : value) {
                                if (doc.get("name") != null) {
                                    listTaskLoad.add(new Task(doc.getId(), doc.getString("name"), doc.getString("detail"), doc.getString("tag"), doc.getBoolean("status"),doc.getLong("createdAt"), doc.getLong("updatedAt"), doc.getString("folderId"), doc.getString("ownerId")));
                                }
                            }
                            Collections.sort(listTaskLoad, new TaskComparator());
                            listTask = listTaskLoad;

                            Log.d("loadtask", "Thanh cong");
                            TaskAdapter adapter = new TaskAdapter(context, R.layout.task_button, listTask);
                            listTaskView.setAdapter(adapter);
                        }
                    });
        }
    };

    public void getAllTasks() {
            if (user != null) {
                db.collection("tasks")
                        .whereEqualTo("ownerId", user.getUid())
                        .orderBy("status", Query.Direction.ASCENDING)
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                if (error != null) {
                                    Log.w("Load data", "Listen failed.", error);
                                    return;
                                }

                                List<Task> listTaskLoad = new ArrayList<Task>();
                                for (QueryDocumentSnapshot doc : value) {
                                    if (doc.get("name") != null) {
                                        listTaskLoad.add(new Task(doc.getId(), doc.getString("name"), doc.getString("detail"), doc.getString("tag"), doc.getBoolean("status"),doc.getLong("createdAt"), doc.getLong("updatedAt"), doc.getString("folderId"), doc.getString("ownerId")));
                                    }
                                }
                                Collections.sort(listTaskLoad, new TaskComparator());
                                listTask = listTaskLoad;

                                Log.d("loadtask", "Thanh cong");
                                TaskAdapter adapter = new TaskAdapter(context, R.layout.task_button, listTask);
                                listTaskView.setAdapter(adapter);
                            }
                        });
            }
        }
}
