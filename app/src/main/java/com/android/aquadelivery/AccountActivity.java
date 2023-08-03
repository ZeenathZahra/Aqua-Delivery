package com.android.aquadelivery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.aquadelivery.AccountPage.AboutUs;
import com.android.aquadelivery.AccountPage.EdiProfileActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountActivity extends AppCompatActivity {

    private Button IntroVideo;
    private Button Logout;
    private Button About;
    private Button Editprofile;
    private Button logoutButton;
    private TextView emailTextView;
    private TextView nameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference userRef = firebaseDatabase.getReference("users");

        String userId = firebaseAuth.getCurrentUser().getUid();

        userRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // User data exists in the database
                    User user = dataSnapshot.getValue(User.class);
                    String username = user.getUserName();
                    String email = user.getEmail();
                    nameTextView = findViewById(R.id.name);
                    emailTextView = findViewById(R.id.email);

                    nameTextView.setText("Hello "+ username);
                    emailTextView.setText(email);

                    // Use the retrieved username and email as needed
                    // For example, you can set them in TextViews or display them in the UI
                } else {
                    // User data does not exist in the database
                    // Handle the case where the user data is missing
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle potential errors in the database retrieval process
            }
        });
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("ACCOUNT");

        // Initialize the bottom navigation view
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);

        // Set the "Cart" item as selected in the bottom navigation view
        bottomNavigationView.setSelectedItemId(R.id.bottom_account);

        // Set an item selected listener for the bottom navigation view
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_cart:
                    startActivity(new Intent(getApplicationContext(), WaterMenuActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_location:
                    startActivity(new Intent(getApplicationContext(), GPSActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_account:
                    return true;
            }
            return false;
        });

        IntroVideo = findViewById(R.id.button2);
        Logout = findViewById(R.id.button6);
        About = findViewById(R.id.button5);
        Editprofile = findViewById(R.id.button3);

        IntroVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openIntroVedioActivity();
            }
        });
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogout();
            }
        });
        About.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAboutusActivity();
            }
        });
        Editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditprofileActivity();
            }
        });

        Logout = findViewById(R.id.button6);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent intent = new Intent(AccountActivity.this, SignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    private void openIntroVedioActivity() {
        Intent intent = new Intent(this, com.android.aquadelivery.AccountPage.IntroVideo.class);
        startActivity(intent);
    }

    private void openLogout() {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    private void openAboutusActivity() {
        Intent intent = new Intent(this, AboutUs.class);
        startActivity(intent);
    }

    private void openEditprofileActivity() {
        Intent intent = new Intent(this, EdiProfileActivity.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}
