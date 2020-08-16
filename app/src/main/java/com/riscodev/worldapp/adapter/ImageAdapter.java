package com.riscodev.worldapp.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.riscodev.worldapp.R;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private List<String> images;
    private ImageAdapter.imageCallback imageCallback;

    public ImageAdapter(List<String> images, ImageAdapter.imageCallback imageCallback) {
        this.images = images;
        this.imageCallback = imageCallback;
    }

    @NonNull
    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageAdapter.ViewHolder(
                parent.getContext(),
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.ViewHolder holder, final int position) {
        holder.bind(images.get(position));
        holder.itemView.setOnClickListener(view -> imageCallback.onClick(images.get(position)));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private Context context;

        ViewHolder(Context context, View view) {
            super(view);
            this.context = context;
            image = view.findViewById(R.id.image);
        }

        void bind(String url) {
            Glide.with(context)
                    .load(url)
                    .placeholder(new ColorDrawable(ContextCompat.getColor(context, android.R.color.darker_gray)))
                    .into(image);
        }
    }

    public interface imageCallback {
        void onClick(String url);
    }
}
