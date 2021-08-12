package com.example.galleryapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.galleryapp.R;
import com.example.galleryapp.classes.ParentFireBase;
import com.example.galleryapp.databinding.RecyclerFileListBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FileRecyclerAdapter extends RecyclerView.Adapter<FileRecyclerAdapter.FileViewHolder> {

    private List<ParentFireBase> parentFireBases = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private RecyclerFileListBinding binding;
    private On_Click_Listener_getChild onClickListener;
    private On_Click_Listener_Radio_Button on_click_listener_radio_button;
    private String fragment;

    public FileRecyclerAdapter (Context context , List<ParentFireBase> parentFireBases, On_Click_Listener_getChild onClickListener, String fragment)
    {
        layoutInflater = LayoutInflater.from(context);
        this.parentFireBases = parentFireBases;
        this.onClickListener = onClickListener;
        this.fragment = fragment;
    }

    public FileRecyclerAdapter (Context context , List<ParentFireBase> parentFireBases, On_Click_Listener_Radio_Button onClickListener, String fragment)
    {
        layoutInflater = LayoutInflater.from(context);
        this.parentFireBases = parentFireBases;
        this.on_click_listener_radio_button = onClickListener;
        this.fragment = fragment;
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
        binding.folderName.setText(parentFireBases.get(position).getName());

        if ( parentFireBases.get(position).getChilds().size() == 1 )
            binding.numberOfPhotos.setText(String.valueOf(parentFireBases.get(position).getChilds().size()) + " photo");
        else
        binding.numberOfPhotos.setText(String.valueOf(parentFireBases.get(position).getChilds().size()) + " photos");

        if ( fragment.equalsIgnoreCase("settings") )
        {
            binding.radio.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return parentFireBases.size();
    }

    public class FileViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public FileViewHolder(@NonNull @NotNull RecyclerFileListBinding binding ) {
            super(binding.getRoot());
            //binding.textView.setOnClickListener(this::onClick);
            binding.radio.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View v) {

            if ( fragment.equalsIgnoreCase("folder") )
            if ( v.getId() == R.id.textView )
            {
                onClickListener.child_list(getAdapterPosition());
            }

            if ( fragment.equalsIgnoreCase("settings") )
                if (v.getId() == R.id.radio)
                {
                    if ( binding.radio.isChecked() )
                    {
                        binding.radio.setChecked(false);
                    }
                    else
                    {
                        binding.radio.setChecked(true);
                    }
                    on_click_listener_radio_button.radio_button_clicked(getAdapterPosition(), binding.radio.isChecked());
                }

        }
    }

    public interface On_Click_Listener_getChild {

        void child_list( int a );

    }

    public interface On_Click_Listener_Radio_Button{

        void radio_button_clicked( int a , Boolean checked);

    }
}
