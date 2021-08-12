package com.example.galleryapp.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.galleryapp.ImagesViewModel;
import com.example.galleryapp.R;
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
    public static int count = 0;

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
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // tracker for the image
        x = position;

        if(data.get(x).getId()!=null)
        {
            liked = data.get(x).isLiked();
            count = Integer.parseInt(data.get(x).getCount());
        }

        binding.seenCount.setText(count+"");


        liked = data.get(x).isLiked();
        if(liked.equals("true"))
            binding.likeButton.setImageDrawable(getDrawable(R.drawable.ic_baseline_favorite_24));

        binding.singleImgViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);



            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                x = position;
                if(data.get(x).getId()!=null) {
                    liked = data.get(x).isLiked();
                    count = Integer.parseInt(data.get(x).getCount());
                }
                binding.seenCount.setText(count+"");
                if(liked.equalsIgnoreCase("true"))
                    binding.likeButton.setImageDrawable(getDrawable(R.drawable.ic_baseline_favorite_24));
                else
                    binding.likeButton.setImageDrawable(getDrawable(R.drawable.ic_baseline_favorite_border_24));

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
        binding.seenCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = Integer.parseInt(data.get(x).getCount());
                count++;
                viewModel.updateCount(data.get(x).getId(),count+"");
                data.get(x).setCount(count+"");
                binding.seenCount.setText(count+"");
            }
        });
        binding.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(x+"dddd");
                liked = data.get(x).isLiked();
                System.out.println(liked);
                if(liked.equalsIgnoreCase("true"))
                {
                    viewModel.setLikedStatus(data.get(x).getId(),"false");
                    data.get(x).setLiked("false");
                    binding.likeButton.setImageDrawable(getDrawable(R.drawable.ic_baseline_favorite_border_24));
                    viewModel.removeLiked(data.get(x));
                }
                else{
                    binding.likeButton.setImageDrawable(getDrawable(R.drawable.ic_baseline_favorite_24));
                    viewModel.setLikedStatus(data.get(x).getId(),"true");
                    data.get(x).setLiked("true");
                    viewModel.addLiked(data.get(x));
                }
                liked = data.get(x).isLiked();
                System.out.println(liked);
            }
        });




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

//        SharedPreferences sharedPreferences = getSharedPreferences("Drive", Context.MODE_PRIVATE);
//        switch (sharedPreferences.getInt("FragmentId",0))
//        {
//            case R.id.navigation_favourite:
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_container, new FavoritesFragment(App.getContext()))
//                        .commit();
//        }
    }
}