package com.example.galleryapp.fragments;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.galleryapp.adapters.FilesChildRecyclerAdapter;
import com.example.galleryapp.adapters.FileRecyclerAdapter;
import com.example.galleryapp.classes.ParentFireBase;
import com.example.galleryapp.databinding.FragmentFoldersBinding;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FoldersFragment extends Fragment implements FileRecyclerAdapter.On_Click_Listener_getChild, FilesChildRecyclerAdapter.Get_child{

    private FragmentFoldersBinding binding;
    private Context context;
//    private Application application;
//    private FileViewModal fileViewModal;
//    private SharedPreferences sharedPreferences;
    private FileRecyclerAdapter fileRecyclerAdapter;
    private List<ParentFireBase> parentFireBases = new ArrayList<>();
    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;
//    private List<ChildFolder> childFolderList = new ArrayList<>();
//    private FilesChildRecyclerAdapter filesChildRecyclerAdapter;

    public FoldersFragment() {
        // Required empty public constructor
    }

    public FoldersFragment(Context context , LayoutInflater layoutInflater , Application application)
    {
        binding = FragmentFoldersBinding.inflate(layoutInflater);
        this.context = context;
        databaseReference = FirebaseDatabase.getInstance().getReference("Parent");
      //  this.application = application;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        ProgressDialog progressDialog = new ProgressDialog(context);
//        progressDialog.setMessage("Please Wait ....");
//        progressDialog.show();

//        this.fileViewModal = new ViewModelProvider(this , new FileViewModal.MyViewModalFactory(application, context)).get(FileViewModal.class);
//        fileViewModal.init_folder();
//        sharedPreferences = context.getSharedPreferences("Drive", Context.MODE_PRIVATE);
       // fileRecyclerAdapter = new FileRecyclerAdapter(context,filesList, this::child_list);
//
//        fileViewModal.get_files_list("Bearer " + sharedPreferences.getString("bearer token",""));
//
//        fileViewModal.getListLiveData().observe(this, new Observer<FolderResponse>() {
//            @Override
//            public void onChanged(FolderResponse files) {
//                if (files.getFileList().size() > 0) {
//                    filesList.clear();
//                    filesList.addAll(files.getFileList());
//                    fileRecyclerAdapter.notifyDataSetChanged();
//                }
//            }
//        });
//        progressDialog.dismiss();

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

                ParentFireBase parentFireBase = snapshot.getValue(ParentFireBase.class);
                parentFireBases.add(parentFireBase);
                fileRecyclerAdapter.notifyDataSetChanged();

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

        fileRecyclerAdapter = new FileRecyclerAdapter(context,parentFireBases , this,"folder");
        binding.recyclerView.setAdapter(fileRecyclerAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        return binding.getRoot();
    }

    @Override
    public void child_list(int a) {

//        if ( filesList.get(a).getMimeType().equalsIgnoreCase("application/vnd.google-apps.folder") )
//        {
//            ProgressDialog progressDialog = new ProgressDialog(context);
//            progressDialog.setMessage("Please Wait ....");
//            progressDialog.show();
//
//            fileViewModal.init_child_folder();
//            filesChildRecyclerAdapter = new FilesChildRecyclerAdapter(context,childFolderList,this::child_list_next);
//            fileViewModal.get_child_folder_list("Bearer " + sharedPreferences.getString("bearer token",""),filesList.get(a).getId());
//            binding.recyclerView.setAdapter(filesChildRecyclerAdapter);
//            fileViewModal.getChildFolderResponseLiveData().observe(this, new Observer<ChildFolderResponse>() {
//                @Override
//                public void onChanged(ChildFolderResponse childFolderResponse) {
//                    childFolderList.clear();
//                    childFolderList.addAll(childFolderResponse.getItems());
//                    filesChildRecyclerAdapter.notifyDataSetChanged();
//                }
//            });
//            progressDialog.dismiss();
//        }

    }

//    public void check_file ()
//    {
//        for ( int i =0 ; i < filesList.size() ; i++ )
//        {
//            if ( filesList.get(i).getMimeType().equalsIgnoreCase("image/jpeg")
//                    || filesList.get(i).getMimeType().equalsIgnoreCase("image/jpg")
//                    || filesList.get(i).getMimeType().equalsIgnoreCase("image/png"))
//            {
//
//
//            }
//        }
//    }

    @Override
    public void child_list_next(int b) {

    }
}