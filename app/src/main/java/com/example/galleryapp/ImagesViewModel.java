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
    private List<ModelImage> images =  new ArrayList<>();
    private static List<ModelImage> imageList =  new ArrayList<>();
    private static String imageLiked;
    private static boolean exist = false;

    public void initializeModel()
    {
        clearList();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //images.clear();
                int i=0;
                for (DataSnapshot snapshot1: snapshot.getChildren())
                {
                    images.add(snapshot1.getValue(ModelImage.class));
                    addToImagesList(snapshot1.getValue(ModelImage.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void clearList(){
        imageList.clear();
    }
    private void addToImagesList(ModelImage d){
        this.imageList.add(d);
    }


    public List<ModelImage> getImagesList()
    {
        return this.imageList;
    }

    public void setLikedStatus(String id,String status)
    {
        Map<String,Object> like = new HashMap<>();
        like.put(id,status);
        imageLiked = status;
        reference.child(id).updateChildren(like);
    }

    public String getLikedStatus(String id)
    {
        reference.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                imageLiked = snapshot.getValue(ModelImage.class).isLiked();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return imageLiked;
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
}
