package com.example.galleryapp.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.galleryapp.adapters.SingleImageRvAdapter;
import com.example.galleryapp.databinding.ActivityImageViewBinding;
import com.example.galleryapp.models.ModelImage;

import java.util.ArrayList;

public class ImageViewActivity extends AppCompatActivity {

    SingleImageRvAdapter adapter;
    ArrayList<ModelImage> data;
    ActivityImageViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityImageViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int position = getIntent().getIntExtra("position",0);

        // using just dummy data for layout check
        data = new ArrayList<>();



        adapter = new SingleImageRvAdapter(this,data);

        binding.singleImgViewPager.setAdapter(adapter);

        binding.singleImgViewPager.setCurrentItem(position);
        binding.singleImgViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                binding.singleImgViewPager.setCurrentItem(position);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });


    }
}