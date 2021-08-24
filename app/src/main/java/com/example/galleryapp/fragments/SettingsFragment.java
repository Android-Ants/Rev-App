package com.example.galleryapp.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.galleryapp.FetchData;
import com.example.galleryapp.ImagesViewModel;
import com.example.galleryapp.PaperDb;
import com.example.galleryapp.R;
import com.example.galleryapp.adapters.FileRecyclerAdapter;
import com.example.galleryapp.classes.ParentFireBase;
import com.example.galleryapp.databinding.FragmentSettingsBinding;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    private Boolean open = true;
    private Boolean check_ = false;
    private FileRecyclerAdapter fileRecyclerAdapter;
    private ImagesViewModel imagesViewModel;


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
        imagesViewModel = new ViewModelProvider(this).get(ImagesViewModel.class);


        final ArrayList<String> foldersId = new ArrayList<>(Paper.book("Folders").getAllKeys());

        parentFireBases.clear();
        for (int i = 0; i < foldersId.size(); i++) {
            ParentFireBase folderCheck = Paper.book("Folders").read(foldersId.get(i));
            parentFireBases.add(folderCheck);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSettingsBinding.inflate(inflater);
        binding.nFolders.setText(parentFireBases.size() + " folders");
        binding.image.setOnClickListener(this::onClick);
        binding.fetch.setOnClickListener(this::onClick);
        binding.radio.setOnClickListener(this::onClick);

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

            case R.id.fetch:

                FetchData fetchData = new FetchData(context);
                fetchData.fetchingAllPhotos();

                break;

            case R.id.radio:

                String Message = "";

                if (!check_) {
                    Message = "Are you sure to unblock all folders ?";
                } else {
                    Message = "Are you sure to block all folders ?";
                }

                AlertDialog.Builder alert = new AlertDialog.Builder(context)
                        .setTitle("Confirmation Message")
                        .setMessage(Message)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (!check_)
                                {
                                    check_ = true;
                                    for ( int i=0 ; i< parentFireBases.size() ;i++ )
                                    {
                                        PaperDb.set_status_false(parentFireBases.get(i).getParentId(),context);
                                    }
                                    binding.radio.setChecked(true);
                                    binding.recyclerView.setAdapter(fileRecyclerAdapter);
                                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(context));
                                }
                                else {
                                    check_ = false;
                                    for ( int i=0 ; i< parentFireBases.size() ;i++ )
                                    {
                                        PaperDb.set_status_true(parentFireBases.get(i).getParentId(),context);
                                    }
                                    binding.radio.setChecked(false);
                                    binding.recyclerView.setAdapter(fileRecyclerAdapter);
                                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(context));
                                }

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (binding.radio.isChecked())
                                {
                                    binding.radio.setChecked(false);
                                }
                                else {
                                    binding.radio.setChecked(true);
                                }

                            }
                        });

                alert.show();

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
                        imagesViewModel.initializingDb(context);

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