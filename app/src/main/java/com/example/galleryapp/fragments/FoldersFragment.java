package com.example.galleryapp.fragments;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.galleryapp.activities.ChildImagesActivity;
import com.example.galleryapp.adapters.FileRecyclerAdapter;
import com.example.galleryapp.adapters.FilesChildRecyclerAdapter;
import com.example.galleryapp.classes.ParentFireBase;
import com.example.galleryapp.databinding.FragmentFoldersBinding;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class FoldersFragment extends Fragment implements FileRecyclerAdapter.On_Click_Listener_getChild, FilesChildRecyclerAdapter.Get_child{

    private FragmentFoldersBinding binding;
    private Context context;
//    private Application application;
//    private FileViewModal fileViewModal;

    private static final ArrayList<String> foldersId = new ArrayList<>(Paper.book("Folders").getAllKeys());

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private FileRecyclerAdapter fileRecyclerAdapter;
    private static final List<ParentFireBase> parentFireBases = new ArrayList<>();
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
        sharedPreferences = context.getSharedPreferences("Drive", Context.MODE_PRIVATE);
      //  this.application = application;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        parentFireBases.clear();
        for (int i = 0; i < foldersId.size(); i++) {
            ParentFireBase folderCheck = Paper.book("Folders").read(foldersId.get(i));
            if (sharedPreferences.contains(folderCheck.getParentId())) {
                if (!sharedPreferences.getBoolean(folderCheck.getParentId(), false))
                    parentFireBases.add(folderCheck);
            }
            else
                parentFireBases.add(folderCheck);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fileRecyclerAdapter = new FileRecyclerAdapter(context,parentFireBases , this,"folder");
        binding.recyclerView.setAdapter(fileRecyclerAdapter);
        if(parentFireBases==null){

            binding.imageView.setVisibility(View.VISIBLE);
            binding.text.setVisibility(View.VISIBLE);
            binding.recyclerView.setVisibility(View.GONE);

        }
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        return binding.getRoot();
    }

    @Override
    public void child_list(int a) {


        Intent intent = new Intent(context, ChildImagesActivity.class);
        intent.putExtra("FolderId",parentFireBases.get(a).getParentId());
        startActivity(intent);


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