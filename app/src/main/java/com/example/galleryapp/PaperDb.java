package com.example.galleryapp;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.galleryapp.classes.FireBaseCount;
import com.example.galleryapp.classes.ParentFireBase;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class PaperDb {

    private static final ArrayList<String> foldersId = new ArrayList<>(Paper.book("Folders").getAllKeys());
    private static final ArrayList<String> likedId = new ArrayList<>();
    private static final ArrayList<String> countArrangedId = new ArrayList<>(Paper.book("Count").getAllKeys());
    private static final ArrayList<FireBaseCount> imagesList = new ArrayList<>();
    private static final ArrayList<FireBaseCount> full = new ArrayList<>();
    private static List<String> imagesLikedId;


    public static List<FireBaseCount> returnAll(Context context) {
        imagesList.clear();
        full.clear();
        SharedPreferences sharedPreferences = context.getSharedPreferences("Drive", Context.MODE_PRIVATE);
        System.out.println(foldersId);
        for (int i = 0; i < foldersId.size(); i++) {
            ParentFireBase folderCheck = Paper.book("Folders").read(foldersId.get(i));
            List<FireBaseCount> imagesByFolder = folderCheck.getChilds();
            for (FireBaseCount f : imagesByFolder) {
                full.add(Paper.book("ImagesAll").read(f.getId()));
                if (!sharedPreferences.getBoolean(folderCheck.getParentId(), false)) {
                    imagesList.add(Paper.book("ImagesAll").read(f.getId()));
                }
            }
        }
        Paper.book("ImagesList").write("fullList", full);
        return imagesList;
    }

    public static void updateImage(String id, FireBaseCount imagesUpdated) {
        Paper.book("ImagesAll").write(id, imagesUpdated);
        full.remove(imagesUpdated);
        full.add(imagesUpdated);
        Paper.book("ImagesList").write("fullList", full);
    }

    public static List<String> returnLiked() {
        imagesLikedId = new ArrayList<>(Paper.book("Liked").getAllKeys());
        return imagesLikedId;
    }


    public static void change_block_status(String parentId, Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("Drive", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (sharedPreferences.getBoolean(parentId, false))
            editor.putBoolean(parentId, false);
        else
            editor.putBoolean(parentId, true);
        editor.commit();

    }

    public static Boolean get_block_status(String parentId, Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("Drive", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(parentId, false);

    }

}
