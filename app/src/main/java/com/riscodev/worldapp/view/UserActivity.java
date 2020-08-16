package com.riscodev.worldapp.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.riscodev.worldapp.R;

public class UserActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_CALL = 0;
    private LinearLayout email, call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("User Profile");
        }

        email = findViewById(R.id.email);
        email.setOnClickListener(view -> openEmail());
        call = findViewById(R.id.call);
        call.setOnClickListener(view -> checkPermission(this::openCall));
    }

    private void openEmail() {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_EMAIL, "riscoramdani80@gmail.com");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Hello, Risco");
            startActivity(Intent.createChooser(intent, "Send Email"));
        } catch (Exception e) {
            Toast.makeText(this, "Something when wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void openCall() {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "081224386022"));
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "Something when wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkPermission(Callback callback) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE_CALL);
        } else {
            callback.onAction();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_CALL:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCall();
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
        }
    }

    interface Callback {
        void onAction();
    }

}