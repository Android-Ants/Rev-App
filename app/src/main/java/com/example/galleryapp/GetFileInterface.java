package com.example.galleryapp;


import com.example.galleryapp.classes.File;
import com.example.galleryapp.classes.FileResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface GetFileInterface {

    @GET("drive/v3/files")
    Call<FileResponse> get_File_List (@Header("Authorization") String token);

}


