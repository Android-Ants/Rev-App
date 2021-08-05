package com.example.galleryapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.galleryapp.classes.File;
import com.example.galleryapp.databinding.RecyclerFileListBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FileRecyclerAdapter extends RecyclerView.Adapter<FileRecyclerAdapter.FileViewHolder> {

    private List<File> files = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private RecyclerFileListBinding binding;

    public FileRecyclerAdapter ( Context context , List<File> files )
    {
        layoutInflater = LayoutInflater.from(context);
        this.files = files;
    }

    @NonNull
    @NotNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        binding = RecyclerFileListBinding.inflate(layoutInflater);
        return new FileViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FileRecyclerAdapter.FileViewHolder holder, int position) {
        binding.textView.setText(files.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public class FileViewHolder extends RecyclerView.ViewHolder {

        public FileViewHolder(@NonNull @NotNull RecyclerFileListBinding binding) {
            super(binding.getRoot());
        }
    }
}
