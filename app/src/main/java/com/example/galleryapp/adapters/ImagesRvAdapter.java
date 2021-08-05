package com.example.galleryapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.galleryapp.R;
import com.example.galleryapp.databinding.RvImagesBinding;
import com.example.galleryapp.fragments.HomeFragment;
import com.example.galleryapp.models.ModelImage;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ImagesRvAdapter extends RecyclerView.Adapter<ImagesRvAdapter.ImageHolder> {

    private Context context;
    private ArrayList<ModelImage> data  =  new ArrayList<>();
    LayoutInflater inflater;
    RvImagesBinding binding;
    private static int resumePosition;

    public ImagesRvAdapter(Context context,ArrayList<ModelImage> data)
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
    public void onBindViewHolder(@NonNull @NotNull ImagesRvAdapter.ImageHolder holder, @SuppressLint("RecyclerView") final int position) {
        resumePosition = position*3 + 1;
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher);

        Glide.with(context).load(data.get(resumePosition-1).getUrl()).apply(options).into(binding.imageView0);
        Glide.with(context).load(data.get(resumePosition).getUrl()).apply(options).into(binding.imageView1);
        Glide.with(context).load(data.get(resumePosition+1).getUrl()).apply(options).into(binding.imageView2);

        binding.imageView0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment.singleView(position-1);
            }
        });
        binding.imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment.singleView(position);
            }
        });
        binding.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment.singleView(position+1);
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


    public class ImageHolder extends RecyclerView.ViewHolder {
        public ImageHolder(@NonNull RvImagesBinding binding) {
            super(binding.getRoot());
        }
    }
}
