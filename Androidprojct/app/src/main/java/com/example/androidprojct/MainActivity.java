package com.example.androidprojct;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidprojct.models.User;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextEmail, editTextPassword, editTextFirstName, editTextPhoneNumber,editLastname;
    private EditText editKebele,editWoreda,editUserName;
    ProgressDialog pd;
    //@SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextFirstName = findViewById(R.id.edit_first_name);
        editLastname=findViewById(R.id.edit_last_name);
        editTextPhoneNumber = findViewById(R.id.edit_phone_number);
        editKebele=findViewById(R.id.edit_kebele);
        editWoreda=findViewById(R.id.edit_woreda);
        editUserName=findViewById(R.id.edit_username);
        editTextEmail = findViewById(R.id.edit_email);
        editTextPassword = findViewById(R.id.edit_password);



        findViewById(R.id.button_signup).setOnClickListener(this);
        findViewById(R.id.text_login).setOnClickListener(this);
    }
    /*@Override
        protected void onStart() {
            super.onStart();
            if(SharedPreferenceManager.getInstance(this).isLoggedIn()){
                Intent intent = new Intent(this, ProfileActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
            else {
                Intent intent = new Intent(this, Onboarding.class);
                startActivity(intent);
                finish();
            }
    }*/
   private void userSignUp(){

        String first_name=editTextFirstName.getText().toString().trim();
        String last_name=editLastname.getText().toString().trim();
       String phonenumber = editTextPhoneNumber.getText().toString().trim();
       String kebele=editKebele.getText().toString().trim();
       String woreda=editWoreda.getText().toString().trim();

       String email = editTextEmail.getText().toString().trim();
       String user_name=editUserName.getText().toString().trim();
       String password = editTextPassword.getText().toString().trim();


       if (email.isEmpty()) {
           editTextEmail.setError("Email is required");
           editTextEmail.requestFocus();
           return;
       }
       if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
           editTextEmail.setError("Enter a valid email");
           editTextEmail.requestFocus();
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

       if (first_name.isEmpty()) {
           editTextFirstName.setError("Name required");
           editTextFirstName.requestFocus();
           return;
       }

       if (phonenumber.isEmpty()) {
           editTextPhoneNumber.setError("phone number required");
           editTextPhoneNumber.requestFocus();
           return;
       }

       User user = new User(first_name,last_name,phonenumber,kebele,woreda,email,user_name,password);
       Call<User> call = RetrofitClient.getInstance().getApi().createUser(user);
       call.enqueue(new Callback<User>() {
           @Override
           public void onResponse(Call<User> call, Response<User> response) {
               pd.cancel();
               if (response.isSuccessful()) {
                   User createdUser = response.body();
                   if (createdUser != null) {
                       Toast.makeText(MainActivity.this, "User created: " + createdUser.getUsername(), Toast.LENGTH_LONG).show();
                       SharedPreferenceManager sharedPreferenceManager = SharedPreferenceManager.getInstance(MainActivity.this);
                       sharedPreferenceManager.saveUser(createdUser);
                       Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
                       startActivity(intent);
                       finish();
                   }
               } else if (response.code() == 422) {
                   Toast.makeText(MainActivity.this, "User already exists", Toast.LENGTH_LONG).show();
               }
           }

           @Override
           public void onFailure(Call<User> call, Throwable t) {
               Log.e("API Error", t.getMessage());
               Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
               pd.cancel();
           }
       });


       ;

   }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_signup:
                pd=new ProgressDialog(MainActivity.this);
                pd.setTitle("Creating");
                pd.setMessage("user");
                pd.show();
                userSignUp();
                break;
            case R.id.text_login:

                startActivity(new Intent(this, LoginActivity.class));

                break;
        }
    }
}