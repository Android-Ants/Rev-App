package com.example.galleryapp.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
    public static int count = 0;

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
        binding.seenCount.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String msg = "";
                if(count>0){
                    msg = "Do you want to reset count?";

                    AlertDialog.Builder alert = new AlertDialog.Builder(ImageViewActivity.this)
                            .setTitle("Confirmation Message")
                            .setMessage(msg)
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    count = 0;
                                    viewModel.updateCount(data.get(x),count+"");
                                    data.get(x).setCount(count+"");
                                    binding.seenCount.setText(count+"");
                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(ImageViewActivity.this, "Count on this image : "+count
                                            , Toast.LENGTH_SHORT).show();
                                }
                            });
                    alert.show();
                }
                return true;
            }
        });
        binding.seenCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data.get(x).getClickable().equalsIgnoreCase("false")){
                    Toast.makeText(ImageViewActivity.this, "You read this today", Toast.LENGTH_SHORT).show();
                    return;
                }
                count = Integer.parseInt(binding.seenCount.getText().toString());
                count++;
                viewModel.updateCount(data.get(x),count+"");
                data.get(x).setClickable("false");
                data.get(x).setCount(count+"");
                binding.seenCount.setText(count+"");
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
                    //viewModel.setLikedStatus(data.get(x).getId(),"false");
                    //data.get(x).setClickable("false");
                    binding.likeButton.setImageDrawable(getDrawable(R.drawable.ic_baseline_favorite_border_24));
                    viewModel.removeLiked(data.get(x));
                }
                else{
                    binding.likeButton.setImageDrawable(getDrawable(R.drawable.ic_baseline_favorite_24));
                    //viewModel.setLikedStatus(data.get(x).getId(),"true");
                    //data.get(x).setClickable("true");
                    viewModel.addLiked(data.get(x));
                }
                //liked = data.get(x).getClickable();
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