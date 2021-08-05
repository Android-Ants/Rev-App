package com.example.galleryapp;


import com.example.galleryapp.classes.ChildFolderResponse;
import com.example.galleryapp.classes.FolderResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface GetFileInterface {

    @GET("drive/v3/files")
    Call<FolderResponse> get_File_List (@Header("Authorization") String token);

    @GET("drive/v3/files/{fileId}/children")
    Call<ChildFolderResponse> get_child_folder (@Header("Authorization") String token , @Path("fileId") String fileId );
}


