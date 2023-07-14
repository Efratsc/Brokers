package com.example.androidprojct;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidprojct.models.Logins;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity  implements View.OnClickListener{
    private EditText editTextUSer;
    private EditText editTextPassword;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextUSer = findViewById(R.id.usernameEditText);
        editTextPassword = findViewById(R.id.passwordEditText);
        findViewById(R.id.loginButton).setOnClickListener(this);

    }
    @Override
    protected void onStart() {
        super.onStart();

        int userId = SharedPreferenceManager.getInstance(this).getCurrentUserID(this);

        if (SharedPreferenceManager.getInstance(this).isLoggedIn(userId)) {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
    private void userLogin() {

        String username = editTextUSer.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (username.isEmpty()) {
            editTextUSer.setError("username is required");
            editTextUSer.requestFocus();
            return;
        }



        if (password.isEmpty()) {
            editTextPassword.setError("Password required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Password should be atleast 6 character long");
            editTextPassword.requestFocus();
            return;
        }
        Call<LoginResponse> call = RetrofitClient.getInstance().getApi().getLoginByUsername(username);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                pd.cancel();
                LoginResponse loginResponse = response.body();

                if (loginResponse != null && !loginResponse.isError()) {
                    // Retrieve user information from the LoginResponse object
                    Logins login = loginResponse.getUser();

                    if (login != null) {
                        // Save user information using SharedPreferenceManager or any other method
                        // Call userLogin() method to save the user ID in shared preferences
                        int userId = login.getId();
                        SharedPreferenceManager.getInstance(LoginActivity.this).userLogin(userId);

                        // Check if user ID is valid
                        if (SharedPreferenceManager.getInstance(LoginActivity.this).isLoggedIn(userId)) {
                            // User is logged in, start profile activity
                            Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            // User ID is not valid, show error message
                            Toast.makeText(LoginActivity.this, "Unable to retrieve user information", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Unable to retrieve user information", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Unable to retrieve login", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                pd.cancel();
               Toast.makeText(LoginActivity.this,"cant login",Toast.LENGTH_LONG).show();
            }
        });


    }
    public void register(View view){
        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
        finish();

    }
    @Override
    public void onClick(View v) {
        pd=new ProgressDialog(LoginActivity.this);

        switch (v.getId()) {

            case R.id.loginButton:
                pd.setTitle("login");
                pd.setMessage("in progress");
                pd.show();
                userLogin();
                break;
            /*case R.id.register:
                startActivity(new Intent(this, MainActivity.class));
                break;*/
        }
    }
}