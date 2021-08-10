package com.example.galleryapp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.galleryapp.classes.ChildFolderResponse;
import com.example.galleryapp.classes.FolderResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FileRepository {

    private static final String BASE_URL = "https://www.googleapis.com/";

    private GetFileInterface getFileInterface;
    private MutableLiveData<FolderResponse> mutableLiveData;
    private MutableLiveData<ChildFolderResponse> childMutableData;
    private Context context;

    public FileRepository( Context context )
    {
        this.context = context;
        mutableLiveData = new MutableLiveData<>();
        childMutableData = new MutableLiveData<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        getFileInterface = retrofit.create(GetFileInterface.class);
    }

    public void get_file ( String token )
    {
        getFileInterface.get_File_List(token).enqueue(new Callback<FolderResponse>() {
            @Override
            public void onResponse(Call<FolderResponse> call, Response<FolderResponse> response) {
                if (response.body() != null) {
                   mutableLiveData.postValue(response.body());
                    Log.d("hhhhhhhhhhhhhh",response.toString());
                }
            }

            @Override
            public void onFailure(Call<FolderResponse> call, Throwable t) {
                mutableLiveData.postValue(null);
                Log.d("hhhhhhhhhhh","failed" + t.toString());
            }
        });
    }

    public LiveData<FolderResponse> getFileLiveData() {
        return mutableLiveData;
    }

    public void get_bearer_token()
    {
        ApiCalls apiCalls = new ApiCalls(context);
        apiCalls.get_bearer_token();
    }

    public void get_child_folder( String token , String fileId )
    {
        getFileInterface.get_child_folder(token,fileId).enqueue(new Callback<ChildFolderResponse>() {
            @Override
            public void onResponse(Call<ChildFolderResponse> call, Response<ChildFolderResponse> response) {
                if (response.body() != null) {
                    childMutableData.postValue(response.body());
                    Log.d("hhhhhhhhhhhhhh",response.toString());
                }
            }

            @Override
            public void onFailure(Call<ChildFolderResponse> call, Throwable t) {
                childMutableData.postValue(null);
                Log.d("hhhhhhhhhhh","failed" + t.toString());
            }
        });
    }

    public LiveData<ChildFolderResponse> getChildFileLiveData() {
        return childMutableData;
    }

}
