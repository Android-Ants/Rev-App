package com.example.galleryapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.galleryapp.R;
import com.example.galleryapp.classes.ParentFireBase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends Fragment {

    private Context context;
    private List<ParentFireBase> parentFireBases = new ArrayList<>();
    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public SettingsFragment ( Context context )
    {
        this.context = context;
        databaseReference = FirebaseDatabase.getInstance().getReference("Parent");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

                ParentFireBase parentFireBase = snapshot.getValue(ParentFireBase.class);
                parentFireBases.add(parentFireBase);


            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };
        databaseReference.addChildEventListener(childEventListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }
}