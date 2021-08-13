package com.example.galleryapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

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
    private On_Click_Listener_getChild onClickListener;
    private On_Click_Listener_Radio_Button on_click_listener_radio_button;
    private String fragment;

    public FileRecyclerAdapter(Context context, List<ParentFireBase> parentFireBases, On_Click_Listener_getChild onClickListener, String fragment) {
        layoutInflater = LayoutInflater.from(context);
        this.parentFireBases = parentFireBases;
        this.onClickListener = onClickListener;
        this.fragment = fragment;
    }

    public FileRecyclerAdapter(Context context, List<ParentFireBase> parentFireBases, On_Click_Listener_Radio_Button onClickListener, String fragment) {
        layoutInflater = LayoutInflater.from(context);
        this.parentFireBases = parentFireBases;
        this.on_click_listener_radio_button = onClickListener;
        this.fragment = fragment;
    }

    @NonNull
    @NotNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
      View view = layoutInflater.inflate(R.layout.recycler_file_list,parent,false);
        return new FileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FileRecyclerAdapter.FileViewHolder holder, int position) {
        holder.folderName.setText(parentFireBases.get(position).getName());

        if (parentFireBases.get(position).getChilds().size() == 1)
            holder.number_of_photos.setText(String.valueOf(parentFireBases.get(position).getChilds().size()) + " photo");
        else
            holder.number_of_photos.setText(String.valueOf(parentFireBases.get(position).getChilds().size()) + " photos");

        if (fragment.equalsIgnoreCase("settings")) {
            holder.radioButton.setVisibility(View.VISIBLE);
            if (parentFireBases.get(position).getBlocked())
                holder.radioButton.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        return parentFireBases.size();
    }

    public class FileViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView folderName,number_of_photos;
        private RadioButton radioButton;

        public FileViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            //binding.textView.setOnClickListener(this::onClick);

            folderName = itemView.findViewById(R.id.folder_name);
            number_of_photos = itemView.findViewById(R.id.number_of_photos);
            radioButton = itemView.findViewById(R.id.radio);
            radioButton.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View v) {

            if (fragment.equalsIgnoreCase("folder"))
                if (v.getId() == R.id.textView) {
                    onClickListener.child_list(getAdapterPosition());
                }

            if (fragment.equalsIgnoreCase("settings"))
                if (v.getId() == R.id.radio){
                    if ( radioButton.isChecked() )
                    radioButton.setChecked(false);
                    else
                    radioButton.setChecked(true);
                    on_click_listener_radio_button.radio_button_clicked(getAdapterPosition(),radioButton.isChecked());
                }
        }
    }

    public interface On_Click_Listener_getChild {
        void child_list(int a);
    }

    public interface On_Click_Listener_Radio_Button {

        void radio_button_clicked(int a, Boolean checked);

    }
}
