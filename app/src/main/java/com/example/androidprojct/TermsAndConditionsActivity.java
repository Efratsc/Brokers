package com.example.androidprojct;

import static com.example.androidprojct.SharedPreferenceManager.getCurrentUserID;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

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
                    showConfirmationDialog();
                } else {
                    termsCheckBox.setError("Please accept the terms and conditions");
                }
            }
        });
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("100 birr will be deducted from your account. Do you agree?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                performDeductionAndNavigate();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                termsCheckBox.setChecked(false);
                Toast.makeText(TermsAndConditionsActivity.this, "You declined the agreement.", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void performDeductionAndNavigate() {
        startActivity(new Intent(TermsAndConditionsActivity.this, post_details.class));
    }
}