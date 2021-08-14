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
import com.example.galleryapp.classes.FireBaseCount;
import com.example.galleryapp.databinding.FragmentFavoritesBinding;

import java.util.ArrayList;


public class FavoritesFragment extends Fragment {

    private Context context;
    FragmentFavoritesBinding binding;
    ImagesViewModel viewModel;
    ArrayList<FireBaseCount> data;
    ArrayList<FireBaseCount> raw = new ArrayList<>();
    ArrayList<String> Ids;
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
        viewModel.initializingDb(context);
        data = (ArrayList<FireBaseCount>) viewModel.getImagesList();
        Ids = new ArrayList<>(viewModel.getLikedListIds());
        if(raw!=null)
        raw.clear();
        if(data!=null)
        for (FireBaseCount f: data) {
            if(Ids.contains(f.getId()))
                raw.add(f);
        }
        if(raw!=null){
            Randomize obj = new Randomize();
            ArrayList<FireBaseCount> randomData = obj.getRandomized(raw);

            imagesRvAdapter = new ImagesRvAdapter(context, randomData);
        }

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