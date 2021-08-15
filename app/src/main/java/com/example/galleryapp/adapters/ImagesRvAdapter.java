package com.example.galleryapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.galleryapp.R;
import com.example.galleryapp.activities.ImageViewActivity;
import com.example.galleryapp.classes.FireBaseCount;
import com.example.galleryapp.databinding.RvImagesBinding;

import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class ImagesRvAdapter extends RecyclerView.Adapter<ImagesRvAdapter.ImageHolder> {

    private static Context context;
    private static ImageViewActivity activity;
    private ArrayList<FireBaseCount> data  =  new ArrayList<>();
    LayoutInflater inflater;
    RvImagesBinding binding;
    private static int resumePosition;

    public ImagesRvAdapter(Context context, ArrayList<FireBaseCount> data)
    {
        this.data = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = RvImagesBinding.inflate(inflater);
        return new ImageHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ImagesRvAdapter.ImageHolder holder, @SuppressLint("RecyclerView") int position) {
        resumePosition = position*3 + 1;
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .centerCrop()
                .error(R.drawable.ic_launcher_background);



        Glide.with(context).load(data.get(resumePosition-1).getUrl()).apply(options).into(binding.imageView0);
        Log.d("imagesssssssssssssss",""+LoadImageFromWebOperations(data.get(resumePosition-1).getUrl()));
        if(!(resumePosition>=data.size()))
        {
            Glide.with(context).load(data.get(resumePosition).getUrl()).apply(options).into(binding.imageView1);
            Log.d("ImageUrl",data.get(resumePosition).getUrl());
        }
        if(!(resumePosition+1>=data.size()))
        {
            Glide.with(context).load(data.get(resumePosition + 1).getUrl()).apply(options).into(binding.imageView2);
        }

        binding.imageView0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleView(position*3);
            }
        });
        binding.imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleView(position*3+1);
            }
        });
        binding.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleView(position*3+2);
            }
        });
    }


    @Override
    public int getItemCount() {
        int count = data.size();
        if(count%3==0) count /=3;
        else{
            count = count/3;
            count++;
        }
        return count;
    }

    public static void singleView(int i) {
        Intent intent = new Intent(context, ImageViewActivity.class);
        intent.putExtra("position",i);
        context.startActivity(intent);
        //activity.finish();
    }

    public class ImageHolder extends RecyclerView.ViewHolder {
        public ImageHolder(@NonNull RvImagesBinding binding) {
            super(binding.getRoot());
        }
    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            return null;
        }
    }

}
