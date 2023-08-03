package com.android.aquadelivery;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.aquadelivery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText addressEditText;
    private EditText contactEditText;
    private Button signUpButton;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference userRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        // Get references to the views
        usernameEditText = findViewById(R.id.getusername);
        emailEditText = findViewById(R.id.getemail);
        passwordEditText = findViewById(R.id.getpassword);
        addressEditText = findViewById(R.id.getaddress);
        contactEditText = findViewById(R.id.getcontactnumber);
        signUpButton = findViewById(R.id.button10);
        progressBar = findViewById(R.id.progressBar2);

        // Initialize Firebase authentication
        firebaseAuth = FirebaseAuth.getInstance();


        // Initialize Firebase database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users");



        // Set OnClickListener for the signup button
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve user input
                String username = usernameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String address = addressEditText.getText().toString().trim();
                String contactNumber = contactEditText.getText().toString().trim();

                // Validate user input
                if (username.isEmpty() || email.isEmpty() || password.isEmpty() || address.isEmpty() || contactNumber.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Show progress bar
                    progressBar.setVisibility(View.VISIBLE);

                    // Create a new user in Firebase authentication
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // User registration successful
                                        // Get the user ID of the newly created user
                                        String userId = firebaseAuth.getCurrentUser().getUid();

                                        // Create a new User object with the user information
                                        User user = new User(username, email, address, contactNumber);

                                        // Save the user information to the Firebase database
                                        userRef.child(userId).setValue(user)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            // User information saved successfully
                                                            Toast.makeText(SignUpActivity.this, "Signup successful", Toast.LENGTH_SHORT).show();
                                                            finish(); // Finish the signup activity
                                                        } else {
                                                            // Failed to save user information
                                                            Toast.makeText(SignUpActivity.this, "Failed to save user information", Toast.LENGTH_SHORT).show();
                                                        }
                                                        // Hide progress bar
                                                        progressBar.setVisibility(View.GONE);
                                                    }
                                                });
                                    } else {
                                        // User registration failed
                                        Toast.makeText(SignUpActivity.this, "Signup failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        // Hide progress bar
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                }
            }
        });
    }
}