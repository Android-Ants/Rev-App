package com.example.galleryapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.galleryapp.R;
import com.example.galleryapp.databinding.RvSingleImageBinding;
import com.example.galleryapp.classes.FireBaseCount;

import java.util.ArrayList;

public class SingleImageRvAdapter extends RecyclerView.Adapter<SingleImageRvAdapter.ImageHolder> {



    Context context;
    ArrayList<FireBaseCount> data;
    RvSingleImageBinding binding;
    LayoutInflater inflater;

    public SingleImageRvAdapter(Context context, ArrayList<FireBaseCount> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rv_single_image,parent,false);
        return new ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {
        RequestOptions options = new RequestOptions()
                .fitCenter()
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_image_24);
        Glide.with(context).load(data.get(position).getUrl()).apply(options).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    ImageView imageView;
    public static class ImageHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout Layout;
        public ImageView imageView;

        public ImageHolder(@NonNull View binding) {
            super(binding);
            imageView = binding.findViewById(R.id.trial_image);
            Layout = binding.findViewById(R.id.frame);
        }
    }
}
