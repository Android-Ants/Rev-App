package com.example.galleryapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.galleryapp.R;
import com.example.galleryapp.classes.Folder;
import com.example.galleryapp.databinding.RecyclerFileListBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FileRecyclerAdapter extends RecyclerView.Adapter<FileRecyclerAdapter.FileViewHolder> {

    private List<Folder> folders = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private RecyclerFileListBinding binding;
    private Get_child get_child;

    public FileRecyclerAdapter (Context context , List<Folder> folders, Get_child get_child)
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
    public void onBindViewHolder(@NonNull @NotNull FileRecyclerAdapter.FileViewHolder holder, int position) {
        binding.textView.setText(folders.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return folders.size();
    }

    public class FileViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public FileViewHolder(@NonNull @NotNull RecyclerFileListBinding binding ) {
            super(binding.getRoot());
            binding.textView.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View v) {
            if ( v.getId() == R.id.textView )
            {
                get_child.child_list(getAdapterPosition());
            }
        }
    }

    public interface Get_child{
        void child_list( int a );
    }
}
