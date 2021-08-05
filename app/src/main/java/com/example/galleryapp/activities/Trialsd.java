package com.example.galleryapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.galleryapp.R;

public class Trialsd extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trialsd);

        imageView = findViewById(R.id.image);

        Glide.with(imageView)
                .asBitmap()
                .load("https://drive.google.com/uc?id=1E6NLCqnrgLT2fFxSlcIK-iPKjBRpzxs-&export=download")
                .into(imageView);
    }
}