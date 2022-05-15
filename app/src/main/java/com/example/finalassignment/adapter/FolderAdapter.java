package com.example.finalassignment.adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.finalassignment.R;
import com.example.finalassignment.detail;
import com.example.finalassignment.entity.Folder;

import java.util.ArrayList;
import java.util.List;

public class FolderAdapter extends BaseAdapter {
    private Context context;
    private int idLayout;
    private List<Folder> listFolder = new ArrayList<Folder>();
    private int positionSelected = -1;

    public FolderAdapter(Context context, int idLayout, List<Folder> listFolder) {
            this.context = context;
            this.idLayout = idLayout;
            this.listFolder = listFolder;
    }

    @Override
    public int getCount() {
        if (listFolder.size() != 0) {
            return  listFolder.size();
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

        Button btnFolder = (Button) view.findViewById(R.id.btnFolder);
        Folder folder = listFolder.get(i);

        if(!listFolder.isEmpty()) {
            btnFolder.setText(folder.getName());
        }

        btnFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, detail.class);
                intent.putExtra("docId", folder.getDocId());
                intent.putExtra("folderName", folder.getName());
                context.startActivity(intent);
            }
        });
        return view;
    }


}
