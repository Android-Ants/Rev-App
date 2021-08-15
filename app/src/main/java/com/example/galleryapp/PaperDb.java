package com.example.galleryapp;

import android.content.Context;

import com.example.galleryapp.classes.FireBaseCount;
import com.example.galleryapp.classes.ParentFireBase;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class PaperDb {

    Context context;

    private static final ArrayList<String> foldersId = new ArrayList<>(Paper.book("Folders").getAllKeys());
    private static final ArrayList<String> likedId = new ArrayList<>();
    private static final ArrayList<String> countArrangedId = new ArrayList<>(Paper.book("Count").getAllKeys());
    private static final ArrayList<FireBaseCount> imagesList = new ArrayList<>();
    private static final ArrayList<FireBaseCount> full = new ArrayList<>();
    private static List<String> imagesLikedId;


    public static List<FireBaseCount> returnAll() {
        imagesList.clear();
        full.clear();
        System.out.println(foldersId);
        for (int i = 0; i < foldersId.size(); i++) {
            ParentFireBase folderCheck = Paper.book("Folders").read(foldersId.get(i));
            List<FireBaseCount> imagesByFolder = folderCheck.getChilds();
            for (FireBaseCount f : imagesByFolder){
                full.add(Paper.book("ImagesAll").read(f.getId()));
                if(!folderCheck.getBlocked()){
                    imagesList.add(Paper.book("ImagesAll").read(f.getId()));
                }
            }
        }
        Paper.book("ImagesList").write("fullList",full);
        return imagesList;
    }

    public static void updateImage(String id,FireBaseCount imagesUpdated){
        Paper.book("ImagesAll").write(id,imagesUpdated);
        full.remove(imagesUpdated);
        full.add(imagesUpdated);
        Paper.book("ImagesList").write("fullList",full);
    }

    public static List<String> returnLiked(){
        imagesLikedId =  new ArrayList<>(Paper.book("Liked").getAllKeys());
        return imagesLikedId;
    }

}
