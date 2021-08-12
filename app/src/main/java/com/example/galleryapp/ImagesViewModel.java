package com.example.galleryapp;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.example.galleryapp.models.ModelImage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImagesViewModel extends ViewModel {

    final private FirebaseDatabase database = FirebaseDatabase.getInstance();
    final private DatabaseReference reference = database.getReference().child("Photos"+"Tarun");
    final private DatabaseReference referenceLiked = database.getReference().child("Photos"+"Tarun"+"Liked");
    private static List<ModelImage> images =  new ArrayList<>();
    private static List<ModelImage> imagesLiked =  new ArrayList<>();
    private static String likedStatus;
    public static String count;
    private static boolean exist = false;

    public void initializeModel()
    {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                images.clear();
                for (DataSnapshot snapshot1: snapshot.getChildren())
                {
                    images.add(snapshot1.getValue(ModelImage.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void fetchingLikedImages()
    {
        reference.orderByChild("liked").equalTo("true").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                imagesLiked.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    imagesLiked.add(snapshot1.getValue(ModelImage.class));
                    System.out.println(snapshot1.getValue(ModelImage.class).getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public List<ModelImage> getImagesList()
    {
        System.out.println(images);
        return images;
    }

    public void addLiked(ModelImage img){
        imagesLiked.add(img);
    }

    public void removeLiked(ModelImage img){
        imagesLiked.remove(img);
    }

    public List<ModelImage> getLikedList()
    {
        System.out.println(imagesLiked);
        return imagesLiked;
    }



    public void setLikedStatus(String id,String status)
    {
        Map<String,Object> like = new HashMap<>();
        like.put("liked",status);
        likedStatus = status;
        reference.child(id).updateChildren(like);
    }


    public void call(String id)
    {
        reference.child(id);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println(exist+"");
                if(snapshot.exists()) exist = true;
                System.out.println(exist+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public boolean isExist(){
        System.out.println(exist+"");
        return exist;
    }

    public void updateCount(String id, String count) {
        Map<String,Object> countMap = new HashMap<>();
        countMap.put("count",count);
        this.count = count;
        reference.child(id).updateChildren(countMap);
    }
}
