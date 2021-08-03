package com.example.galleryapp;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.galleryapp.classes.File;
import com.example.galleryapp.classes.FileResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FileViewModal extends AndroidViewModel {

    private FileRepository fileRepository;
    private LiveData<FileResponse> listLiveData;
    private Context context;

    public FileViewModal(@NonNull @NotNull Application application , Context context) {
        super(application);
        this.context = context;
    }

    public void get_files_list( String token )
    {
        Log.d("hhhhhhhhh","in view modal");
        fileRepository.get_file(token);
    }
    public void init ()
    {
        fileRepository = new FileRepository(context);
        listLiveData = fileRepository.getFileLiveData();
    }

    public LiveData<FileResponse> getListLiveData ()
    {
        return listLiveData;
    }

    public void get_bearer_token()
    {
        fileRepository.get_bearer_token();
    }

    public static class MyViewModalFactory implements ViewModelProvider.Factory{

        private Application application;
        private Context context;

        public  MyViewModalFactory ( Application application , Context context )
        {
            this.application = application;
            this.context = context;
        }

        @NonNull
        @NotNull
        @Override
        public <T extends ViewModel> T create(@NonNull @NotNull Class<T> modelClass) {
            return (T) new FileViewModal(application,context);
        }
    }


}
