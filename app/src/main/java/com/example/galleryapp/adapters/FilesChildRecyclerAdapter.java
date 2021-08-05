package com.example.galleryapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.galleryapp.classes.ChildFolder;
import com.example.galleryapp.databinding.RecyclerFileListBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FilesChildRecyclerAdapter extends RecyclerView.Adapter<FilesChildRecyclerAdapter.FileViewHolder>{

    private List<ChildFolder> folders = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private RecyclerFileListBinding binding;
    private Get_child get_child;

    public FilesChildRecyclerAdapter (Context context , List<ChildFolder> folders,Get_child get_child)
    {
        layoutInflater = LayoutInflater.from(context);
        this.folders = folders;
        this.get_child = get_child;
    }


    @NonNull
    @NotNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        binding = RecyclerFileListBinding.inflate(layoutInflater);
        return new FileViewHolder(binding );
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FilesChildRecyclerAdapter.FileViewHolder holder, int position) {
       // binding.textView.setText(folders.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return folders.size();
    }

    public class FileViewHolder extends RecyclerView.ViewHolder {
        public FileViewHolder(@NonNull RecyclerFileListBinding binding) {
            super(binding.getRoot());
        }
    }

    public interface Get_child{
        void child_list( int a );
    }
}
