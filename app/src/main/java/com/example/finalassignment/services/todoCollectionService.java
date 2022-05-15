package com.example.finalassignment.services;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class todoCollectionService {
    private FirebaseFirestore db;

    public todoCollectionService() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
    }

    public void getListFolder(String userId) {
        db.collection("users/" + userId + "/todoFolder")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Load data", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w("Load data", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void createNewFolder(String userId, String folder) {
        db.collection("users/" + userId + "/todoFolder")
                .add(folder)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("Them data", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Them data", "Error adding document", e);
                    }
                });
    }

    public void editFolder(String userId, String folder, String newFolder) {

    }
}
