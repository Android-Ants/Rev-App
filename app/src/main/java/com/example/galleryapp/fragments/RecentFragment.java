package com.example.galleryapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.galleryapp.R;
import com.example.galleryapp.databinding.FragmentRecentBinding;


public class RecentFragment extends Fragment {

    FragmentRecentBinding fragmentRecentBinding;

    public RecentFragment ()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentRecentBinding = FragmentRecentBinding.inflate(inflater,container,false);

        Glide.with(fragmentRecentBinding.imageView1).asBitmap()
                .load("https://drive.google.com/uc?id=15zhGEns0_U3KeI6UuxejFzGmRUOq_ATR&export=download")
                .into(fragmentRecentBinding.imageView1);
        Glide.with(fragmentRecentBinding.imageView2)
                .asBitmap()
                .load("https://drive.google.com/file/d/1_SWc5X9Xw_qlItmrDZKLc8loc8_YReLQ/view")
                .into(fragmentRecentBinding.imageView2);

        return inflater.inflate(R.layout.fragment_recent, container, false);
    }
}