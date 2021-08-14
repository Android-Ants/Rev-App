package com.example.galleryapp.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.galleryapp.ImagesViewModel;
import com.example.galleryapp.R;
import com.example.galleryapp.Randomize;
import com.example.galleryapp.adapters.SingleImageRvAdapter;
import com.example.galleryapp.classes.FireBaseCount;
import com.example.galleryapp.databinding.ActivityImageViewBinding;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class ImageViewActivity extends AppCompatActivity {

    SingleImageRvAdapter adapter;
    ArrayList<FireBaseCount> data;
    ActivityImageViewBinding binding;
    SharedPreferences sharedPreferences;
    private ImagesViewModel viewModel;
    public static int x;
    public static String liked = "false";
    public static int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityImageViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Paper.init(this);

        sharedPreferences = getSharedPreferences("Drive", Context.MODE_PRIVATE);

        int position = getIntent().getIntExtra("position",0);

        this.viewModel = new ViewModelProvider(this).get(ImagesViewModel.class);
        viewModel.initializingDb(this);
        List<String> s = viewModel.getLikedListIds();
        if(sharedPreferences.getInt("FragmentId",0)==R.id.navigation_recent)
        {
            data = viewModel.getArrangedCount();
        }else{
            data = Randomize.getPrevRandomized();
        }


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
            liked = data.get(x).getLiked();
            String scount =viewModel.getCount(data.get(x).getId());
            if(scount!=null)
            count = Integer.parseInt(scount);
            else count = 0;
        }

        binding.seenCount.setText(count+"");
        binding.textView2.setText(data.get(x).getName());

        if(s.contains(data.get(x).getId()))
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
                binding.textView2.setText(data.get(x).getName());
                if(data.get(x).getId()!=null) {
                    liked = data.get(x).getLiked();
                    String scount =viewModel.getCount(data.get(x).getId());
                    if(scount!=null)
                        count = Integer.parseInt(scount);
                    else count = 0;
                }
                binding.seenCount.setText(count+"");
                if(s.contains(data.get(x).getId()))
                    binding.likeButton.setImageDrawable(getDrawable(R.drawable.ic_baseline_favorite_24));
                else
                    binding.likeButton.setImageDrawable(getDrawable(R.drawable.ic_baseline_favorite_border_24));

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
        binding.seenCount.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                count = 0;
                viewModel.updateCount(data.get(x),count+"");
                data.get(x).setCount(count+"");
                binding.seenCount.setText(count+"");
                return true;
            }
        });
        binding.seenCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = Integer.parseInt(data.get(x).getCount());
                count++;
                viewModel.updateCount(data.get(x),count+"");
                data.get(x).setCount(count+"");
                binding.seenCount.setText(count+"");
            }
        });
        binding.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(x+"dddd");
                liked = data.get(x).getLiked();
                System.out.println(liked);
                if(s.contains(data.get(x).getId()))
                {
                    //viewModel.setLikedStatus(data.get(x).getId(),"false");
                    data.get(x).setLiked("false");
                    binding.likeButton.setImageDrawable(getDrawable(R.drawable.ic_baseline_favorite_border_24));
                    viewModel.removeLiked(data.get(x));
                }
                else{
                    binding.likeButton.setImageDrawable(getDrawable(R.drawable.ic_baseline_favorite_24));
                    //iewModel.setLikedStatus(data.get(x).getId(),"true");
                    data.get(x).setLiked("true");
                    viewModel.addLiked(data.get(x));
                }
                liked = data.get(x).getLiked();
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