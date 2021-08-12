package com.example.galleryapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.galleryapp.ImagesViewModel;
import com.example.galleryapp.Randomize;
import com.example.galleryapp.adapters.ImagesRvAdapter;
import com.example.galleryapp.databinding.FragmentHomeBinding;
import com.example.galleryapp.models.ModelImage;

import java.util.ArrayList;


public class HomeFragment extends Fragment {



    private FragmentHomeBinding binding;
    private ImagesRvAdapter adapter;
    private static Context context;

    private static ArrayList<ModelImage> data;

    private ImagesViewModel imagesViewModel;

    public static Randomize obj = new Randomize();
    public HomeFragment ()
    {

    }

    public HomeFragment (Context context)
    {
        this.context = context;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.imagesViewModel = new ViewModelProvider(this).get(ImagesViewModel.class);
        imagesViewModel.initializeModel();
        imagesViewModel.fetchingLikedImages();
        data = new ArrayList<>(imagesViewModel.getImagesList());


        ArrayList<ModelImage> randomData = obj.getRandomized(data);

        adapter = new ImagesRvAdapter(context,randomData);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater,container,false);
        binding.imageRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.imageRecycler.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}



// trial images
//        data = new ArrayList<>();
//        data.add(new ModelImage("https://picsum.photos/id/0/200/200"));
//        data.add(new ModelImage("https://picsum.photos/id/1/200/200"));
//        data.add(new ModelImage("https://picsum.photos/id/2/200/200"));
//        data.add(new ModelImage("https://picsum.photos/id/3/200/200"));
//        data.add(new ModelImage("https://picsum.photos/id/4/200/200"));
//        data.add(new ModelImage("https://picsum.photos/id/5/200/200"));
//        data.add(new ModelImage("https://picsum.photos/id/6/200/200"));
//        data.add(new ModelImage("https://picsum.photos/id/7/200/200"));
//        data.add(new ModelImage("https://picsum.photos/id/8/200/200"));
//        data.add(new ModelImage("https://picsum.photos/id/9/200/200"));
//        data.add(new ModelImage("https://picsum.photos/id/10/200/200"));
//        data.add(new ModelImage("https://picsum.photos/id/11/200/200"));
//        data.add(new ModelImage("https://picsum.photos/id/12/200/200"));
//        data.add(new ModelImage("https://picsum.photos/id/13/200/200"));
//        data.add(new ModelImage("https://picsum.photos/id/14/200/200"));
//        data.add(new ModelImage("https://picsum.photos/id/15/200/200"));
//        data.add(new ModelImage("https://picsum.photos/id/16/200/200"));
//        data.add(new ModelImage("https://picsum.photos/id/17/800/1200"));
//        data.add(new ModelImage("https://picsum.photos/id/18/800/1200"));
//        data.add(new ModelImage("https://picsum.photos/id/19/800/1200"));
//        data.add(new ModelImage("https://picsum.photos/id/20/800/1200"));
//
