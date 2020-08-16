package com.riscodev.worldapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.riscodev.worldapp.BaseActivity;
import com.riscodev.worldapp.R;
import com.riscodev.worldapp.adapter.PlaceAdapter;
import com.riscodev.worldapp.model.Data;
import com.riscodev.worldapp.model.PlaceBean;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    public final static String TAG_INDEX = "INDEX";
    private RecyclerView listItem;
    private PlaceAdapter placeAdapter;
    private boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Home");
        }
        setContentView(R.layout.activity_main);
        listItem = findViewById(R.id.list_item);
        showList();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       switch (item.getItemId()) {
           case R.id.user:
               openUserPage();
               return true;
           case R.id.order_name:
               orderListByName();
               return true;
           case R.id.order_default:
               orderListDefault();
               return true;
           default:
               return super.onOptionsItemSelected(item);
       }
    }

    private void orderListByName() {
        placeAdapter.orderListByName();
    }

    private void orderListDefault() {
        placeAdapter.orderDefault();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    private void openUserPage() {
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }

    private void openDetailPage(ImageView image, int position) {
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this, image,"image_transition");
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(TAG_INDEX, position);
        startActivity(intent, activityOptionsCompat.toBundle());
    }

    private void showList() {
        Type type = TypeToken.getParameterized(ArrayList.class, PlaceBean.class).getType();
        List<PlaceBean> placeBeans = new Gson().fromJson(Data.get(), type);
        placeAdapter = new PlaceAdapter(placeBeans, this::openDetailPage);
        LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listItem.setLayoutManager(layoutManager);
        listItem.setHasFixedSize(true);
        listItem.setAdapter(placeAdapter);
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, "Press back again to Exit", Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler(Looper.getMainLooper()).postDelayed(() -> exit = false, 2000);
        }
    }
}