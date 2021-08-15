package com.example.galleryapp.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.galleryapp.PaperDb;
import com.example.galleryapp.R;
import com.example.galleryapp.adapters.FileRecyclerAdapter;
import com.example.galleryapp.classes.CheckBlocked;
import com.example.galleryapp.classes.ParentFireBase;
import com.example.galleryapp.databinding.FragmentSettingsBinding;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class SettingsFragment extends Fragment implements View.OnClickListener, FileRecyclerAdapter.On_Click_Listener_Radio_Button {

    private FragmentSettingsBinding binding;
    private Context context;
    private List<ParentFireBase> parentFireBases = new ArrayList<>();
    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;
    private int folders_number;
    private Boolean open = false;
    private FileRecyclerAdapter fileRecyclerAdapter;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public SettingsFragment(Context context, int number_folders) {
        this.context = context;
        databaseReference = FirebaseDatabase.getInstance().getReference("Parent");
        this.folders_number = number_folders;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

                ParentFireBase parentFireBase = snapshot.getValue(ParentFireBase.class);
                parentFireBases.add(parentFireBase);
                fileRecyclerAdapter.notifyDataSetChanged();
                binding.numberOfFolders.setText(String.valueOf(parentFireBases.size()) + " folders");
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

        binding = FragmentSettingsBinding.inflate(inflater);
        binding.numberOfFolders.setText(String.valueOf(folders_number) + " folders");
        binding.image.setOnClickListener(this::onClick);

        fileRecyclerAdapter = new FileRecyclerAdapter(context, parentFireBases, this, "settings");
        binding.recyclerView.setAdapter(fileRecyclerAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.image:

                if (open) {
                    open = false;
                    binding.image.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24);
                    binding.recyclerView.setVisibility(View.GONE);
                } else {
                    open = true;
                    binding.image.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24);
                    binding.recyclerView.setVisibility(View.VISIBLE);
                }
                break;
        }


    }


    @Override
    public void radio_button_clicked(int a, Boolean checked) {

        String Message = "";
        Boolean block;

        if (checked) {
            Message = "Are you to unblock this folder ?";
            block = false;
        } else {
            Message = "Are you to block this folder ?";
            block = true;
        }

        AlertDialog.Builder alert = new AlertDialog.Builder(context)
                .setTitle("Confirmation Message")
                .setMessage(Message)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ParentFireBase parentFireBase = parentFireBases.get(a);
                        parentFireBase.setBlocked(block);
                        databaseReference.child(parentFireBase.getParentId()).setValue(parentFireBase);

                        PaperDb.change_block_status(parentFireBase.getParentId(), context);

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });

        alert.show();
    }
}