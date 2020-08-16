package com.riscodev.worldapp.view;

import android.animation.ObjectAnimator;
import android.graphics.drawable.ColorDrawable;
import android.graphics.text.LineBreaker;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.riscodev.worldapp.R;
import com.riscodev.worldapp.adapter.ImageAdapter;
import com.riscodev.worldapp.model.Data;
import com.riscodev.worldapp.model.PlaceBean;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.riscodev.worldapp.view.MainActivity.TAG_INDEX;

public class DetailActivity extends AppCompatActivity {

    private TextView title, address, description, date, area, latitude, longitude, labelArea, read;
    private ImageView image;
    private RecyclerView listImage;
    private Toolbar toolbar;
    private boolean fullRead = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();

        // Get Data
        Type type = TypeToken.getParameterized(ArrayList.class, PlaceBean.class).getType();
        List<PlaceBean> placeBeans = new Gson().fromJson(Data.get(), type);
        int index = getIntent().getIntExtra(TAG_INDEX, 0);
        setView(placeBeans.get(index));
        setImageList(placeBeans.get(index).getImages());
        setToolbar(placeBeans.get(index).getPlace());

        // map
        initMap(placeBeans.get(index));
    }

    private void initView() {
        title = findViewById(R.id.title);
        address = findViewById(R.id.address);
        description = findViewById(R.id.desc);
        date = findViewById(R.id.date);
        area = findViewById(R.id.area);
        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        image = findViewById(R.id.image);
        listImage = findViewById(R.id.list_image);
        labelArea = findViewById(R.id.label_area);
        toolbar = findViewById(R.id.toolbar);
        read = findViewById(R.id.read);
        read.setOnClickListener(view -> showFullRead());
    }

    private void setToolbar(String title) {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(title);
        }
    }

    private void showFullRead() {
        if (!fullRead) {
            setLineDescription(99, 500);
            read.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_arrow_drop_up_24, 0);
        } else {
            setLineDescription(10, 100);
            read.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_arrow_drop_down_24, 0);
        }
        fullRead = !fullRead;
    }

    private void setLineDescription(int value, int time) {
        ObjectAnimator animation = ObjectAnimator.ofInt(
                description,
                "maxLines",
                value);
        animation.setDuration(time);
        animation.start();
    }

    private void setView(PlaceBean placeBean) {
        title.setText(placeBean.getPlace());
        address.setText(placeBean.getLocation().get(0).getAddress());
        description.setText(placeBean.getDescription());
        date.setText(placeBean.getDate());
        setArea(placeBean);
        latitude.setText(placeBean.getLocation().get(0).getLatitude());
        longitude.setText(placeBean.getLocation().get(0).getLongitude());
        setImage(placeBean.getImages().get(0));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            description.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
        }
    }

    private void initMap(final PlaceBean placeBean) {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(googleMap -> {
                LatLng location = new LatLng(
                        Double.parseDouble(placeBean.getLocation().get(0).getLatitude()),
                        Double.parseDouble(placeBean.getLocation().get(0).getLongitude()));
                googleMap.addMarker(new MarkerOptions()
                        .position(location)
                        .title(placeBean.getPlace()));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 7f));
            });
        }
    }

    private void setImageList(List<String> images) {
        LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        ImageAdapter imageAdapter = new ImageAdapter(images, url -> setImage(url));
        listImage.setLayoutManager(layoutManager);
        listImage.setHasFixedSize(true);
        listImage.setNestedScrollingEnabled(false);
        listImage.setAdapter(imageAdapter);
    }

    private void setArea(PlaceBean placeBean) {
        PlaceBean.BuildingEntity buildingEntity = placeBean.getBuilding().get(0);
        if (!buildingEntity.getArea().isEmpty()) {
            labelArea.setText("Area");
            area.setText(buildingEntity.getArea());
        }
        else if (!buildingEntity.getLength().isEmpty()) {
            labelArea.setText("Length");
            area.setText(buildingEntity.getLength());
        }
        else if (!buildingEntity.getHeight().isEmpty()) {
            labelArea.setText("Height");
            area.setText(buildingEntity.getHeight());
        }
        else {
            area.setText("-");
        }
    }

    private void setImage(String url) {
        Glide.with(this)
                .load(url)
                .placeholder(new ColorDrawable(ContextCompat.getColor(this, android.R.color.darker_gray)))
                .into(image);
    }
}