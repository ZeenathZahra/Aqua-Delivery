package com.android.aquadelivery.AccountPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.android.aquadelivery.AccountActivity;
import com.android.aquadelivery.R;

public class IntroVideo extends AppCompatActivity {

    private ImageView BackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_video);

        // Hide action bar
        getSupportActionBar().hide();
        // Set full screen mode
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        VideoView videoView = findViewById(R.id.videoView);
        videoView.setVideoPath("android.resource://"+getPackageName() + "/" + R.raw.intro);
        videoView.start();

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        BackButton = findViewById(R.id.BackButton);

        // Set a click listener for the registration button
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAccount();
            }
        });
    }

    private void openAccount() {
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }

}