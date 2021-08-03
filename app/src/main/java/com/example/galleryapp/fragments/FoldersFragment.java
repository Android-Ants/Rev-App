package com.example.galleryapp.fragments;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.galleryapp.FileRecyclerAdapter;
import com.example.galleryapp.FileViewModal;
import com.example.galleryapp.R;
import com.example.galleryapp.classes.File;
import com.example.galleryapp.classes.FileResponse;
import com.example.galleryapp.databinding.FragmentProfileBinding;

import java.util.ArrayList;
import java.util.List;

public class FoldersFragment extends Fragment {

    private FragmentProfileBinding binding;
    private Context context;
    private Application application;
    private FileViewModal fileViewModal;
    private SharedPreferences sharedPreferences;
    private FileRecyclerAdapter fileRecyclerAdapter;
    private List<File> filesList = new ArrayList<>();

    public FoldersFragment() {
        // Required empty public constructor
    }

    public FoldersFragment(Context context , LayoutInflater layoutInflater , Application application)
    {
        binding = FragmentProfileBinding.inflate(layoutInflater);
        this.context = context;
        this.application = application;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait ....");
        progressDialog.show();

        this.fileViewModal = new ViewModelProvider(this , new FileViewModal.MyViewModalFactory(application, context)).get(FileViewModal.class);
        fileViewModal.init();
        sharedPreferences = context.getSharedPreferences("Drive", Context.MODE_PRIVATE);
        fileRecyclerAdapter = new FileRecyclerAdapter(context,filesList);

        fileViewModal.get_files_list("Bearer " + sharedPreferences.getString("bearer token",""));

        fileViewModal.getListLiveData().observe(this, new Observer<FileResponse>() {
            @Override
            public void onChanged(FileResponse files) {
                if (files.getFileList().size() > 0) {
                    filesList.clear();
                    filesList.addAll(files.getFileList());
                    fileRecyclerAdapter.notifyDataSetChanged();
                }
            }
        });
        progressDialog.dismiss();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        binding.recyclerView.setAdapter(fileRecyclerAdapter);
        return binding.getRoot();
    }
}