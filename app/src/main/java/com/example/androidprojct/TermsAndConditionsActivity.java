package com.example.androidprojct;

import static com.example.androidprojct.SharedPreferenceManager.getCurrentUserID;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.androidprojct.models.Connections;
import com.example.androidprojct.models.PostModel;
import com.example.androidprojct.models.Transaction;
import com.example.androidprojct.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TermsAndConditionsActivity extends AppCompatActivity {

    private CheckBox termsCheckBox;
    private Button continueButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);
        termsCheckBox = findViewById(R.id.checkboxAcceptTerms);
        continueButton = findViewById(R.id.btnContinue);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (termsCheckBox.isChecked()) {
                    int postUserID = SharedPreferenceManager.getPostUserID(TermsAndConditionsActivity.this);
                 //   PostModel postModel = new PostModel(1, postUserID, "Sample post text",2,  );
                    connectToUser(postUserID);

                } else {
                    termsCheckBox.setError("Please accept the terms and conditions");
                }
            }
        });
    }
    private void connectToUser(int userId) {
        // Create an instance of the RetrofitClient
       /* RetrofitClient retrofitClient = RetrofitClient.getInstance();

        // Get the API interface
        Api api = retrofitClient.getApi();

        // Deduct 100 birr from the user's balance
        Call<Transaction> transactionCall = api.createTransaction(getCurrentUserID(), "deduction");

        transactionCall.enqueue(new Callback<Transaction>() {
            @Override
            public void onResponse(Call<Transaction> call, Response<Transaction> response) {
                if (response.isSuccessful()) {
                    // Deduction successful, make the connection
                    // Create a new instance of Connections with the necessary data
                    Connections connection = new Connections(0, getCurrentUserID(this), userId, "type");

                    // Make the API call to create the connection
                    Call<Connections> calls = api.createConnection(connection);

                    calls.enqueue(new Callback<Connections>() {
                        @Override
                        public void onResponse(Call<Connections> call, Response<Connections> response) {
                            if (response.isSuccessful()) {
                                // Connection successful, retrieve the posted user information
                                int postedUserId = response.body().getUserId2();

                                // Call the API to get the user details
                                Call<User> userCall = api.getUserById(postedUserId);

                                userCall.enqueue(new Callback<User>() {
                                    @Override
                                    public void onResponse(Call<User> call, Response<User> response) {
                                        if (response.isSuccessful()) {

                                            User postedUser = response.body();

                                            // Pass the user details to the next activity using intent extras
                                            Intent intent = new Intent(TermsAndConditionsActivity.this, PostedUserDetailActivity.class);
                                            intent.putExtra("postedUser", (CharSequence) postedUser);
                                            startActivity(intent);
                                        } else {
                                            // Handle unsuccessful API response
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<User> call, Throwable t) {
                                        // Handle API call failure
                                    }
                                });
                            } else {
                                // Handle unsuccessful API response
                            }
                        }

                        @Override
                        public void onFailure(Call<Connections> call, Throwable t) {
                            // Handle API call failure
                        }
                    });
                } else {
                    // Handle unsuccessful deduction response
                }
            }

            @Override
            public void onFailure(Call<Transaction> call, Throwable t) {
                // Handle deduction API call failure
            }
        });
    }


}
*/}}

