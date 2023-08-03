package com.android.aquadelivery.AccountPage;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.aquadelivery.AccountActivity;
import com.android.aquadelivery.R;
import com.android.aquadelivery.RegistrationActivity;


public class AboutUs extends AppCompatActivity {

    private ImageView BackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

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