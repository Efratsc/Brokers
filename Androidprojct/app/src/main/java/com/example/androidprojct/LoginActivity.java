package com.example.androidprojct;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidprojct.models.Login;

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
       // findViewById(R.id.textViewRegister).setOnClickListener(this);
    }
    @Override
    protected void onStart() {
        super.onStart();

        if (SharedPreferenceManager.getInstance(this).isLoggedIn()) {
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

                if (!loginResponse.isError()) {
                    // Retrieve user information from the LoginResponse object
                  Login login = loginResponse.getUser();

                    // Save user information using SharedPreferenceManager or any other method

                    Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                pd.cancel();
                // Handle failure scenario
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