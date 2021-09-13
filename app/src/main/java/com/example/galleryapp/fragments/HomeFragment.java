package com.example.galleryapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.galleryapp.ImagesViewModel;
import com.example.galleryapp.Randomize;
import com.example.galleryapp.adapters.ImagesRvAdapter;
import com.example.galleryapp.classes.FireBaseCount;
import com.example.galleryapp.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;


public class HomeFragment extends Fragment {



    private FragmentHomeBinding binding;
    private ImagesRvAdapter adapter;
    private static Context context;

    private static List<FireBaseCount> data;

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

        Paper.init(context);

        this.imagesViewModel = new ViewModelProvider(this).get(ImagesViewModel.class);
        imagesViewModel.initializingDb(context);
        //data = new ArrayList<>(imagesViewModel.getImagesList());
        imagesViewModel.getAllImages().observe(this, new Observer<List<FireBaseCount>>() {
            @Override
            public void onChanged(List<FireBaseCount> fireBaseCounts) {
                data = new ArrayList<>(fireBaseCounts);
            }
        });

        ArrayList<FireBaseCount> randomData = new ArrayList<>();
        if(data!=null)
                    randomData = obj.getRandomized((ArrayList<FireBaseCount>) data);

        adapter = new ImagesRvAdapter(context,randomData);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater,container,false);
        binding.imageRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.imageRecycler.setAdapter(adapter);
        if(data==null){
            binding.imageView.setVisibility(View.VISIBLE);
            binding.text.setVisibility(View.VISIBLE);
            binding.imageRecycler.setVisibility(View.GONE);
        }
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
