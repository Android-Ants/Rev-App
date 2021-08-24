package com.example.galleryapp.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.galleryapp.PaperDb;
import com.example.galleryapp.R;
import com.example.galleryapp.classes.ParentFireBase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FileRecyclerAdapter extends RecyclerView.Adapter<FileRecyclerAdapter.FileViewHolder> {

    private List<ParentFireBase> parentFireBases = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private On_Click_Listener_getChild onClickListener;
    private DatabaseReference databaseReference;
    private On_Click_Listener_Radio_Button on_click_listener_radio_button;
    private String fragment;
    private Context context;

    public FileRecyclerAdapter(Context context, List<ParentFireBase> parentFireBases, On_Click_Listener_getChild onClickListener, String fragment) {
        layoutInflater = LayoutInflater.from(context);
        this.parentFireBases = parentFireBases;
        this.onClickListener = onClickListener;
        databaseReference = FirebaseDatabase.getInstance().getReference("Parent");
        this.fragment = fragment;
        this.context = context;
    }

    public FileRecyclerAdapter(Context context, List<ParentFireBase> parentFireBases, On_Click_Listener_Radio_Button onClickListener, String fragment) {
        layoutInflater = LayoutInflater.from(context);
        this.parentFireBases = parentFireBases;
        this.on_click_listener_radio_button = onClickListener;
        databaseReference = FirebaseDatabase.getInstance().getReference("Parent");
        this.fragment = fragment;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recycler_file_list, parent, false);
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
            if (PaperDb.get_block_status(parentFireBases.get(position).getParentId(), context))
                holder.radioButton.setChecked(false);
            else 
                holder.radioButton.setChecked(true);

        }

        Log.d("hhhhhhhhhhhhh",PaperDb.get_block_status(parentFireBases.get(position).getParentId(),context).toString());

    }

    @Override
    public int getItemCount() {
        return parentFireBases.size();
    }

    public class FileViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView folderName, number_of_photos;
        private RadioButton radioButton;

        public FileViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            folderName = itemView.findViewById(R.id.folder_name);

            if (fragment.equalsIgnoreCase("folder"))
            {
                itemView.setOnClickListener(this::onClick);
            }

            number_of_photos = itemView.findViewById(R.id.number_of_photos);
            radioButton = itemView.findViewById(R.id.radio);
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String Message = "";
                    Boolean block;

                    if (PaperDb.get_block_status(parentFireBases.get(getAdapterPosition()).getParentId(), context)) {
                        Message = "Are you sure to unblock this folder ?";
                        block = false;
                    } else {
                        Message = "Are you sure to block this folder ?";
                        block = true;
                    }

                    AlertDialog.Builder alert = new AlertDialog.Builder(context)
                            .setTitle("Confirmation Message")
                            .setMessage(Message)
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    ParentFireBase parentFireBase = parentFireBases.get(getAdapterPosition());
                                    parentFireBase.setBlocked(block);
                                    databaseReference.child(parentFireBase.getParentId()).setValue(parentFireBase);

                                    PaperDb.change_block_status(parentFireBase.getParentId(), context);
                                    //imagesViewModel.initializingDb(context);

                                    if (PaperDb.get_block_status(parentFireBases.get(getAdapterPosition()).getParentId(), context))
                                        radioButton.setChecked(false);
                                    else
                                        radioButton.setChecked(true);

                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    if (PaperDb.get_block_status(parentFireBases.get(getAdapterPosition()).getParentId(), context))
                                        radioButton.setChecked(false);
                                    else
                                        radioButton.setChecked(true);
                                }
                            });

                    alert.show();
                }
            });
        }

        @Override
        public void onClick(View v) {

            if (fragment.equalsIgnoreCase("folder")) {
                onClickListener.child_list(getAdapterPosition());
            }

            if (fragment.equalsIgnoreCase("settings"))
                if (v.getId() == R.id.radio) {
                    if (radioButton.isChecked())
                        radioButton.setChecked(false);
                    else
                        radioButton.setChecked(true);
                    on_click_listener_radio_button.radio_button_clicked(getAdapterPosition(), radioButton.isChecked());
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
