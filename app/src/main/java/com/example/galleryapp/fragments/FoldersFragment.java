package com.example.galleryapp.fragments;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.galleryapp.FileRecyclerAdapter;
import com.example.galleryapp.FileViewModal;
import com.example.galleryapp.classes.Folder;
import com.example.galleryapp.classes.FolderResponse;
import com.example.galleryapp.databinding.FragmentProfileBinding;

import java.util.ArrayList;
import java.util.List;

public class FoldersFragment extends Fragment implements FileRecyclerAdapter.Get_child{

    private FragmentProfileBinding binding;
    private Context context;
    private Application application;
    private FileViewModal fileViewModal;
    private SharedPreferences sharedPreferences;
    private FileRecyclerAdapter fileRecyclerAdapter;
    private List<Folder> filesList = new ArrayList<>();

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
        fileViewModal.init_folder();
        sharedPreferences = context.getSharedPreferences("Drive", Context.MODE_PRIVATE);
        fileRecyclerAdapter = new FileRecyclerAdapter(context,filesList, this::child_list);

        fileViewModal.get_files_list("Bearer " + sharedPreferences.getString("bearer token",""));

        fileViewModal.getListLiveData().observe(this, new Observer<FolderResponse>() {
            @Override
            public void onChanged(FolderResponse files) {
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

    @Override
    public void child_list(int a) {

        if ( filesList.get(a).getMimeType().equalsIgnoreCase("application/vnd.google-apps.folder") )
        {
            ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Please Wait ....");
            progressDialog.show();

            fileViewModal.init_child_folder();

        }

    }

    public void check_file ()
    {
        for ( int i =0 ; i < filesList.size() ; i++ )
        {
            if ( filesList.get(i).getMimeType().equalsIgnoreCase("image/jpeg")
                    || filesList.get(i).getMimeType().equalsIgnoreCase("image/jpg")
                    || filesList.get(i).getMimeType().equalsIgnoreCase("image/png"))
            {


            }
        }
    }

}