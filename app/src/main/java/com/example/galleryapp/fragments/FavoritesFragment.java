package com.example.galleryapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.galleryapp.ImagesViewModel;
import com.example.galleryapp.Randomize;
import com.example.galleryapp.adapters.ImagesRvAdapter;
import com.example.galleryapp.databinding.FragmentFavoritesBinding;
import com.example.galleryapp.models.ModelImage;

import java.util.ArrayList;


public class FavoritesFragment extends Fragment {

    private Context context;
    FragmentFavoritesBinding binding;
    ImagesViewModel viewModel;
    ArrayList<ModelImage> data;
    private ImagesRvAdapter imagesRvAdapter;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    public FavoritesFragment(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.viewModel = new ViewModelProvider(this).get(ImagesViewModel.class);
        viewModel.fetchingLikedImages();
        data = new ArrayList<>(viewModel.getLikedList());

        Randomize obj = new Randomize();
        ArrayList<ModelImage> randomData = obj.getRandomized(data);
        imagesRvAdapter = new ImagesRvAdapter(context, randomData);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFavoritesBinding.inflate(inflater,container,false);
        binding.favouritesRv.setLayoutManager(new LinearLayoutManager(context));
        binding.favouritesRv.setAdapter(imagesRvAdapter);
        return binding.getRoot();
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        data.clear();
//        ArrayList<ModelImage> refresh = new ArrayList<>(viewModel.getLikedList());
//        for (ModelImage i: refresh) {
//            data.add(i);
//        }
//        Randomize obj = new Randomize(data);
//        obj.randomize();
//        imagesRvAdapter.notifyDataSetChanged();
//    }
}