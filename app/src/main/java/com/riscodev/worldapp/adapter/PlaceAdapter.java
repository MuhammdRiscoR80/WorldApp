package com.riscodev.worldapp.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.riscodev.worldapp.R;
import com.riscodev.worldapp.model.PlaceBean;

import java.util.Collections;
import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {

    private List<PlaceBean> placeBeans;
    private PlaceCallback placeCallback;

    public PlaceAdapter(List<PlaceBean> placeBeans, PlaceCallback placeCallback) {
        this.placeBeans = placeBeans;
        this.placeCallback = placeCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                parent.getContext(),
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.bind(placeBeans.get(position));
        holder.itemView.setOnClickListener(view -> placeCallback.onClick(
                holder.image,
                position)
        );
    }

    @Override
    public int getItemCount() {
        return placeBeans.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView title, shortDesc;
        private Context context;

        ViewHolder(Context context, View view) {
            super(view);
            this.context = context;
            image = view.findViewById(R.id.image);
            title = view.findViewById(R.id.title);
            shortDesc = view.findViewById(R.id.short_desc);
        }

        void bind(PlaceBean placeBean) {
            title.setText(placeBean.getPlace());
            shortDesc.setText(placeBean.getShort_description());
            Glide.with(context)
                    .load(placeBean.getImages().get(0))
                    .placeholder(new ColorDrawable(ContextCompat.getColor(context, android.R.color.darker_gray)))
                    .into(image);
        }
    }

    public void orderListByName() {
        Collections.sort(placeBeans,
                (o1, o2) -> o1.getPlace().compareTo(o2.getPlace()));
        notifyDataSetChanged();
    }

    public void orderDefault() {
        Collections.sort(placeBeans, (o1, o2) ->
                Integer.parseInt(o1.getId()) - Integer.parseInt(o2.getId()));
        notifyDataSetChanged();
    }

    public interface PlaceCallback {
        void onClick(ImageView image, int position);
    }
}
