package com.example.galleryapp;

import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.galleryapp.classes.FireBaseCount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.paperdb.Paper;

public class ImagesViewModel extends ViewModel {

    final private FirebaseDatabase database = FirebaseDatabase.getInstance();
    final private DatabaseReference reference = database.getReference().child("Photos"+"Tarun");
    final private DatabaseReference referenceLiked = database.getReference().child("Photos"+"Tarun"+"Liked");
    private static List<FireBaseCount> images;
    private static List<FireBaseCount> imagesLiked;
    private static String likedStatus;
    public static String count;
    private static boolean exist = false;
    private MutableLiveData<List<FireBaseCount>> imagesList;
    private FileRepository fileRepository;
    private List<String> imagesLikedId;
    private static ArrayList<FireBaseCount> arrangedCount;

    public void initializingDb(Context context){
        fileRepository = new FileRepository(context);
        imagesList = fileRepository.get_images();
        imagesLikedId = fileRepository.get_images_liked();
    }

    public LiveData<List<FireBaseCount>> getAllImages(){
        return imagesList;
    }



    public List<FireBaseCount> getImagesList()
    {
        images = new ArrayList<>(Objects.requireNonNull(imagesList.getValue())) ;
        return images;
    }

    public void addLiked(FireBaseCount img){
        Paper.book("Liked").write(img.getId(),"true");
        imagesLikedId.add(img.getId());
    }

    public void removeLiked(FireBaseCount img){
        Paper.book("Liked").delete(img.getId());
        imagesLikedId.remove(img.getId());
    }

    public List<String> getLikedListIds()
    {
        System.out.println(imagesLiked);
        return imagesLikedId;
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

    public void updateCount(FireBaseCount f, String count) {
        Paper.book("Counts").write(f.getId(),count);
        images.get(images.indexOf(f)).setCount(count);
        ImagesViewModel.count = count;
    }

    public String getCount(String id){
        count = Paper.book("Counts").read(id);
        if(count==null)return 0+"";
        return count;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void initializeArrangement(ArrayList<FireBaseCount> fireBaseCounts){
        ArrayList r = new ArrayList();
        for (FireBaseCount f: fireBaseCounts) {
            f.setCount(getCount(f.getId()));
            r.add(f);
        }
        r.sort(new CountSorter());
        setArrangedCount(r);
    }

    public void setArrangedCount(ArrayList<FireBaseCount> fireBaseCounts){
        arrangedCount = new ArrayList<>(fireBaseCounts);
    }

    public ArrayList<FireBaseCount> getArrangedCount() {
        return arrangedCount;
    }
}
