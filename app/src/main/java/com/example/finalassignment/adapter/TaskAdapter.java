package com.example.finalassignment.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.finalassignment.R;
import com.example.finalassignment.entity.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import com.example.finalassignment.addNewTask;

public class TaskAdapter extends BaseAdapter {
    private Context context;
    private int idLayout;
    private List<Task> listTask = new ArrayList<Task>();
    private int positionSelected = -1;
    private FirebaseFirestore db;

    public TaskAdapter(Context context, int idLayout, List<Task> listTask) {
        db = FirebaseFirestore.getInstance();
        this.context = context;
        this.idLayout = idLayout;
        this.listTask = listTask;
    }

    @Override
    public int getCount() {
        if (listTask.size() != 0) {
            return listTask.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(idLayout, viewGroup, false);
        }

        CheckBox cbTask = (CheckBox) view.findViewById(R.id.cbTask);
        Button btnTask = (Button) view.findViewById(R.id.btnTaskDetail);
        Task task = listTask.get(i);

        btnTask.setText(task.getName());

        if (!listTask.isEmpty()) {
            cbTask.setChecked(task.isStatus());
        }

        cbTask.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    db.collection("tasks").document(task.getDocId())
                            .update("status", true);
                }
                else {
                    db.collection("tasks").document(task.getDocId())
                            .update("status", false);
                }

            }
        });

        btnTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, addNewTask.class);
                intent.putExtra("docId", task.getDocId());
                intent.putExtra("taskName", task.getName());
                intent.putExtra("taskDetail", task.getDetail());
                intent.putExtra("folderDocId", task.getFolderId());
                context.startActivity(intent);
            }
        });

        return view;
    }
}
