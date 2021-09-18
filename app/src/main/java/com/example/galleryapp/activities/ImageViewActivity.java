package com.example.galleryapp.activities;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

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
    private SharedPreferences.Editor editor;
    private ImagesViewModel viewModel;
    public static int x;
    public static String liked = "false";
    public static String date = "false";
    public static boolean controller = true;
    public static int count = 0;
    private static boolean dCLick = false;
    private static boolean checker = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityImageViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Paper.init(this);

        sharedPreferences = getSharedPreferences("Drive", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        date = MainActivity.getCurrentDateAndTime();
        Log.d("Date check", "onCreate: "+date);


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

        if(!sharedPreferences.getString("date","").equalsIgnoreCase(date)){
            editor.putString("date",date);
            Log.d("Date check", "onCreate: "+date);
            editor.commit();
            for (FireBaseCount f: data) {
                f.setClickable("true");
            }
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
            //liked = data.get(x).getClickable();
            String scount =viewModel.getCount(data.get(x).getId());
            if(scount!=null)
            count = Integer.parseInt(scount);
            else count = 0;
        }

        binding.seenCount.setText(count+"");
        if(data.get(x).getName().length()>15){
            data.get(x).setName(data.get(x).getName().substring(0,14)+"...");
        }
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
                if(data.get(x).getName().length()>15){
                    data.get(x).setName(data.get(x).getName().substring(0,14)+"...");
                }
                binding.textView2.setText(data.get(x).getName());
                if(data.get(x).getId()!=null) {
                    //liked = data.get(x).getClickable();
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








        binding.seenCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCountOnClick();
            }
        });
        binding.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(x+"dddd");
                //liked = data.get(x).getClickable();
                System.out.println(liked);
                if(s.contains(data.get(x).getId()))
                {
                    binding.likeButton.setImageDrawable(getDrawable(R.drawable.ic_baseline_favorite_border_24));
                    viewModel.removeLiked(data.get(x));
                }
                else{
                    binding.likeButton.setImageDrawable(getDrawable(R.drawable.ic_baseline_favorite_24));
                    viewModel.addLiked(data.get(x));
                }
                System.out.println(liked);
            }
        });




    }


    public void fullScreen(View view) {
        if(dCLick){
            checker = false;
            updateCountOnClick();
            dCLick = !dCLick;
            return;
        }else{
            dCLick = true;
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dCLick = false;

                    Log.d(TAG, "onClick: checking for the on click listener");
                    if(controller&&checker) {
                        binding.actionBar.setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    }else if(!controller&&checker){
                        binding.actionBar.setVisibility(View.VISIBLE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

                    }
                    controller=!controller;
                    checker = true;

                }
            }, 300);

        }


    }

    private void updateCountOnClick() {
        binding.onLiked.setVisibility(View.VISIBLE);
        if(data.get(x).getClickable().equalsIgnoreCase("false")){
            if(count==0) return;
            count--;
            viewModel.updateCount(data.get(x),count+"");
            data.get(x).setCount(count+"");
            binding.seenCount.setText(count+"");
            data.get(x).setClickable("true");
        }else if(data.get(x).getClickable().equalsIgnoreCase("true")){
            count = Integer.parseInt(binding.seenCount.getText().toString());
            count++;
            viewModel.updateCount(data.get(x),count+"");
            data.get(x).setClickable("false");
            data.get(x).setCount(count+"");
            binding.seenCount.setText(count+"");
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.onLiked.setVisibility(View.GONE);
            }
        },500);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }


}