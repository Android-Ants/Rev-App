package com.example.galleryapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.google.firebase.database.FirebaseDatabase;

public class ImagesViewModel extends AndroidViewModel {

    FirebaseDatabase database;

    public ImagesViewModel(@NonNull Application application) {
        super(application);

        database = FirebaseDatabase.getInstance();

    }
}
