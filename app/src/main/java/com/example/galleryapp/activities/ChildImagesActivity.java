package com.example.galleryapp.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.galleryapp.Randomize;
import com.example.galleryapp.adapters.ImagesRvAdapter;
import com.example.galleryapp.classes.FireBaseCount;
import com.example.galleryapp.classes.ParentFireBase;
import com.example.galleryapp.databinding.ActivityChildImagesBinding;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class ChildImagesActivity extends AppCompatActivity {

    private ParentFireBase folder;
    private ImagesRvAdapter adapter;
    private List<FireBaseCount> files = new ArrayList<>();
    private ArrayList<FireBaseCount> data = new ArrayList<>();
    ActivityChildImagesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChildImagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String folderId = getIntent().getStringExtra("FolderId");
        folder = Paper.book("Folders").read(folderId);
        files = folder.getChilds();

        Randomize obj = new Randomize();
        data = obj.getRandomized((ArrayList<FireBaseCount>) files);
        adapter = new ImagesRvAdapter(this, data);
        binding.folderName.setText(folder.getName());
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.imageRecycler.setLayoutManager(new LinearLayoutManager(ChildImagesActivity.this));
        binding.imageRecycler.setAdapter(adapter);
    }

}