package com.android.aquadelivery.AccountPage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.aquadelivery.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class EdiProfileActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText emailEditText;

    private EditText addressEditText;
    private EditText contactEditText;
    private Button updateButton;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edi_profile);

        // Get references to the views
        usernameEditText = findViewById(R.id.update_username);
        emailEditText = findViewById(R.id.update_email);
        addressEditText = findViewById(R.id.update_address);
        contactEditText = findViewById(R.id.update_contact);
        updateButton = findViewById(R.id.update_button);

        // Get reference to the Firebase database and the current user
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Create a reference to the user's data node in the Firebase database
        if (user != null) {
            String userId = user.getUid();
            userRef = database.getReference("users").child(userId);
        }

        // Set OnClickListener for the update button
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve the updated information from the EditText fields
                String updatedUsername = usernameEditText.getText().toString();
                String updatedEmail = emailEditText.getText().toString();
                String updatedAddress = addressEditText.getText().toString();
                String updatedContact = contactEditText.getText().toString();

                // Update the user's profile information in the Firebase database
                userRef.child("username").setValue(updatedUsername);
                userRef.child("email").setValue(updatedEmail);
                userRef.child("address").setValue(updatedAddress);
                userRef.child("contact").setValue(updatedContact);

                Toast.makeText(EdiProfileActivity.this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
