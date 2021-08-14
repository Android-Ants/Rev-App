package com.example.galleryapp.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.galleryapp.ImagesViewModel;
import com.example.galleryapp.adapters.ImagesRvAdapter;
import com.example.galleryapp.classes.FireBaseCount;
import com.example.galleryapp.databinding.FragmentRecentBinding;

import java.util.ArrayList;


public class RecentFragment extends Fragment {

    FragmentRecentBinding fragmentRecentBinding;
    ImagesViewModel viewModel;
    Context context;
    ArrayList<FireBaseCount> data;
    private ImagesRvAdapter imagesRvAdapter;

    public RecentFragment(Context context) {
        this.context = context;
    }

    public RecentFragment ()
    {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.viewModel = new ViewModelProvider(this).get(ImagesViewModel.class);
        viewModel.initializingDb(context);
        viewModel.initializeArrangement((ArrayList<FireBaseCount>) viewModel.getImagesList());
        data = (ArrayList<FireBaseCount>) viewModel.getArrangedCount();

        imagesRvAdapter = new ImagesRvAdapter(context,data);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentRecentBinding = FragmentRecentBinding.inflate(inflater,container,false);
        fragmentRecentBinding.recentRv.setLayoutManager(new LinearLayoutManager(context));
        fragmentRecentBinding.recentRv.setAdapter(imagesRvAdapter);

        viewModel.initializeArrangement((ArrayList<FireBaseCount>) viewModel.getImagesList());
        data = (ArrayList<FireBaseCount>) viewModel.getArrangedCount();

        imagesRvAdapter.notifyDataSetChanged();

        return fragmentRecentBinding.getRoot();
    }
}