package com.example.galleryapp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.galleryapp.classes.File;
import com.example.galleryapp.classes.FileResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FileRepository {

    private static final String BASE_URL = "https://www.googleapis.com/";

    private GetFileInterface getFileInterface;
    private MutableLiveData<FileResponse> mutableLiveData;
    private Context context;

    public FileRepository( Context context )
    {
        this.context = context;
        mutableLiveData = new MutableLiveData<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        getFileInterface = retrofit.create(GetFileInterface.class);
    }

    public void get_file ( String token )
    {
        Toast.makeText(context, "hhhh", Toast.LENGTH_SHORT).show();
        getFileInterface.get_File_List(token).enqueue(new Callback<FileResponse>() {
            @Override
            public void onResponse(Call<FileResponse> call, Response<FileResponse> response) {
                if (response.body() != null) {
                   mutableLiveData.postValue(response.body());
                    Log.d("hhhhhhhhhhhhhh",response.toString());
                }
            }

            @Override
            public void onFailure(Call<FileResponse> call, Throwable t) {
                mutableLiveData.postValue(null);
                Log.d("hhhhhhhhhhh","failed" + t.toString());
            }
        });
    }

    public LiveData<FileResponse> getFileLiveData() {
        return mutableLiveData;
    }

    public void get_bearer_token()
    {
        ApiCalls apiCalls = new ApiCalls(context);
        apiCalls.get_bearer_token();
    }

}
