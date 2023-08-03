package com.android.aquadelivery;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SignInActivity extends AppCompatActivity {
    EditText editTextUserName, editTextPassword;
    TextView textViewForgotPassword, textViewRegister;
    ProgressBar progressBar;
    FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        editTextUserName = findViewById(R.id.editTextTextSignInUserName);
        editTextPassword = findViewById(R.id.editTextTextSignInPassword);
        textViewForgotPassword = findViewById(R.id.forgot);
        textViewRegister = findViewById(R.id.register);
        progressBar = findViewById(R.id.progressBarSignIn);
        mAuth = FirebaseAuth.getInstance();
    }

    public void txtSignInForgotClicked(View v) {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    public void txtSignInRegisterClicked(View v) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void buttonSignScrInClicked(View v) {
        String txtEmail = editTextUserName.getText().toString().trim();
        String txtPassword = editTextPassword.getText().toString().trim();

        if (txtEmail.isEmpty()) {
            editTextUserName.setError("Please enter your email");
            editTextUserName.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(txtEmail).matches()) {
            editTextUserName.setError("Please enter a valid email");
            editTextUserName.requestFocus();
            return;
        }

        if (txtPassword.isEmpty()) {
            editTextPassword.setError("Please enter your password");
            editTextPassword.requestFocus();
            return;
        } else if (txtPassword.length() < 6) {
            editTextPassword.setError("Please enter a password containing at least 6 characters");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(txtEmail, txtPassword).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(SignInActivity.this, "User has successfully logged in", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SignInActivity.this,MainActivity.class);
                startActivity(intent);
            } else {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(SignInActivity.this, "User failed to log in", Toast.LENGTH_LONG).show();
            }
        });
    }
}
