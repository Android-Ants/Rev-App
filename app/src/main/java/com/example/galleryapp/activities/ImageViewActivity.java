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
        data.add(new ModelImage("https://picsum.photos/id/0/200/200"));
        data.add(new ModelImage("https://picsum.photos/id/1/200/200"));
        data.add(new ModelImage("https://picsum.photos/id/2/200/200"));
        data.add(new ModelImage("https://picsum.photos/id/3/200/200"));
        data.add(new ModelImage("https://picsum.photos/id/4/200/200"));
        data.add(new ModelImage("https://picsum.photos/id/5/200/200"));
        data.add(new ModelImage("https://picsum.photos/id/6/200/200"));
        data.add(new ModelImage("https://picsum.photos/id/7/200/200"));
        data.add(new ModelImage("https://picsum.photos/id/8/200/200"));
        data.add(new ModelImage("https://picsum.photos/id/9/200/200"));
        data.add(new ModelImage("https://picsum.photos/id/10/200/200"));
        data.add(new ModelImage("https://picsum.photos/id/11/200/200"));
        data.add(new ModelImage("https://picsum.photos/id/12/200/200"));
        data.add(new ModelImage("https://picsum.photos/id/13/200/200"));
        data.add(new ModelImage("https://picsum.photos/id/14/200/200"));
        data.add(new ModelImage("https://picsum.photos/id/15/200/200"));
        data.add(new ModelImage("https://picsum.photos/id/16/200/200"));
        data.add(new ModelImage("https://picsum.photos/id/17/200/200"));
        data.add(new ModelImage("https://picsum.photos/id/18/200/200"));
        data.add(new ModelImage("https://picsum.photos/id/19/200/200"));
        data.add(new ModelImage("https://picsum.photos/id/20/800/1200"));


        adapter = new SingleImageRvAdapter(this,data);

        binding.singleImgViewPager.setAdapter(adapter);
        binding.singleImgViewPager.setCurrentItem(position+1);

        binding.singleImgViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
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