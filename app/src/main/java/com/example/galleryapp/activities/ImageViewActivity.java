package com.example.galleryapp.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.galleryapp.ImagesViewModel;
import com.example.galleryapp.Randomize;
import com.example.galleryapp.adapters.SingleImageRvAdapter;
import com.example.galleryapp.databinding.ActivityImageViewBinding;
import com.example.galleryapp.models.ModelImage;

import java.util.ArrayList;

public class ImageViewActivity extends AppCompatActivity {

    SingleImageRvAdapter adapter;
    ArrayList<ModelImage> data;
    ActivityImageViewBinding binding;
    private ImagesViewModel viewModel;
    public static int x;
    public static String liked = "false";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityImageViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int position = getIntent().getIntExtra("position",0);

        this.viewModel = new ViewModelProvider(this).get(ImagesViewModel.class);

        data = Randomize.getPrevRandomized();

        adapter = new SingleImageRvAdapter(ImageViewActivity.this,data);
        binding.singleImgViewPager.setAdapter(adapter);
        binding.singleImgViewPager.setCurrentItem(position);

        // tracker for the image
        x = position;

//        if(data.get(x).getId()!=null)
//        {
//           liked = viewModel.getLikedStatus(data.get(x).getId());
//        }
//        else
//        {
//            data.get(x).setId(x+"photo");
//            viewModel.setLikedStatus(data.get(x).getId(),"false");
//            liked = "false";
//        }
//
//        if(liked.equalsIgnoreCase("true"))
//            binding.likeButton.setImageDrawable(getDrawable(R.drawable.ic_baseline_favorite_24));

        binding.singleImgViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                x = position;
//                viewModel.call(x+"photo");
//                if(data.get(x).getId()!=null) {
//                    liked = viewModel.getLikedStatus(data.get(x).getId());
//                }
//                else {
//                    data.get(x).setId(x+"photo");
//                    viewModel.setLikedStatus(data.get(x).getId(),"false");
//                    liked = "false";
//                }
//
//                if(liked.equalsIgnoreCase("true"))
//                    binding.likeButton.setImageDrawable(getDrawable(R.drawable.ic_baseline_favorite_24));
//                else
//                    binding.likeButton.setImageDrawable(getDrawable(R.drawable.ic_baseline_favorite_border_24));

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
        binding.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(x);
                liked = data.get(x).isLiked();
//                if(liked.equalsIgnoreCase("true")) binding.likeButton.setImageDrawable(getDrawable(R.drawable.ic_baseline_favorite_border_24));
//                else{
//                    binding.likeButton.setImageDrawable(getDrawable(R.drawable.ic_baseline_favorite_24));
//                    if(data.get(x).getId()!=null)
//                    {
//                        viewModel.setLikedStatus(data.get(x).getId(),"true");
//                    }
//                    else
//                    {
//                        data.get(x).setId(x+"photo");
//                        viewModel.setLikedStatus(data.get(x).getId(),"true");
//                        liked = "true";
//                    }
//                }
            }
        });



    }
}