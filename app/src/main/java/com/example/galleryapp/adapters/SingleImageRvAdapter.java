package com.example.galleryapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.galleryapp.R;
import com.example.galleryapp.databinding.RvSingleImageBinding;
import com.example.galleryapp.models.ModelImage;

import java.util.ArrayList;

public class SingleImageRvAdapter extends RecyclerView.Adapter<SingleImageRvAdapter.ImageHolder> {



    Context context;
    ArrayList<ModelImage> data;
    RvSingleImageBinding binding;
    LayoutInflater inflater;

    public SingleImageRvAdapter(Context context, ArrayList<ModelImage> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RvSingleImageBinding.inflate(inflater);
        RecyclerView.LayoutParams layoutParams =
                (RecyclerView.LayoutParams) binding.getRoot().getLayoutParams();
        System.out.println(layoutParams);
        return new ImageHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher);
        Glide.with(context).load(data.get(position).getUrl()).apply(options).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    ImageView imageView;
    public static class ImageHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ImageHolder(@NonNull RvSingleImageBinding binding) {
            super(binding.getRoot());
            imageView = binding.imageSingle;
        }
    }
}
