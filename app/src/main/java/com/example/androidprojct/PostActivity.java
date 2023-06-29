package com.example.androidprojct;

import static com.example.androidprojct.SharedPreferenceManager.getCurrentUserID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.androidprojct.models.PostModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostActivity extends AppCompatActivity {
    ImageView img;
    CardView cardView;
    int SELECT_IMAGE_CODE = 1;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        cardView=findViewById(R.id.pickphoto);
        img=findViewById(R.id.pickedimage);
        String username = SharedPreferenceManager.getInstance(this).getUsername();
        TextView usernameTextView = findViewById(R.id.username);
        usernameTextView.setText(username);
        FloatingActionButton fabCreatePost = findViewById(R.id.fab_create_post);
        Spinner serviceSpinner = findViewById(R.id.serviceSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.service_options_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        serviceSpinner.setAdapter(adapter);
        fabCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPost();
                Intent intent = new Intent(PostActivity.this, ProfileActivity.class);
                startActivity(intent);
                /*HomeFragment fragment = new HomeFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.act_post, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();*/

            }
        });
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_galery();
            }
        });
    }
    private void open_galery(){
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_IMAGE_CODE);
    }
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.create_post_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemid = item.getItemId();
        if (itemid == R.id.post) {
            EditText postEditText = findViewById(R.id.editpost);
            String postText = postEditText.getText().toString();
            int userId = getCurrentUserID(this);
            String imageUri = uri.toString();
            PostModel newPost = new PostModel(0, 0, userId, postText,imageUri);

            Call<PostModel> call = RetrofitClient.getInstance().getApi().createPost(newPost);
            call.enqueue(new Callback<PostModel>() {
                @Override
                public void onResponse(Call<PostModel> call, Response<PostModel> response) {
                    if (response.isSuccessful()) {
                        PostModel createdPost = response.body();
                        // Handle the created post as needed
                        // ...
                    } else {
                        // Handle the error case
                        if (response.code() == 400) {
                            // Bad Request
                            String errorMessage = "Invalid request. Please check your input.";
                            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                        } else if (response.code() == 401) {
                            // Unauthorized
                            String errorMessage = "You are not authorized to perform this action.";
                            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                            // You can also redirect the user to the login screen if needed
                            // startActivity(new Intent(PostActivity.this, LoginActivity.class));
                        } else if (response.code() == 404) {
                            // Not Found
                            String errorMessage = "Requested resource not found.";
                            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                        } else if (response.code() == 500) {
                            // Internal Server Error
                            String errorMessage = "Internal server error. Please try again later.";
                            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                        } else {
                            // Other error cases
                            String errorMessage = "An error occurred. Please try again.";
                            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<PostModel> call, Throwable t) {
                    // Handle the failure case
                    // ...
                }
            });

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
*/

    private void createPost() {
        EditText postEditText = findViewById(R.id.editpost);
        String postText = postEditText.getText().toString();
        int userId = SharedPreferenceManager.getCurrentUserID(getApplicationContext());

        Spinner serviceSpinner = findViewById(R.id.serviceSpinner);
        String selectedService = serviceSpinner.getSelectedItem().toString();
        ImageView postImage = findViewById(R.id.pickedimage);
        int service_id = 0;

        switch (selectedService) {
            case "Maid":
                service_id = 1;
                break;
            case "Security":
                service_id = 2;
                break;
            case "Tutor":
                service_id = 3;
                break;
            case "Housing":
                service_id = 5;
                break;
        }

        Drawable drawableImage = postImage.getDrawable();
        Bitmap bitmapImage = null;
        String base64Image = null;

        if (drawableImage != null) {
            bitmapImage = ((BitmapDrawable) drawableImage).getBitmap();
        }

        if (bitmapImage != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();
            base64Image = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        }

        PostModel newPost = new PostModel(0, userId, postText, service_id, base64Image);

        Call<PostModel> call = RetrofitClient.getInstance().getApi().createPost(newPost);
        call.enqueue(new Callback<PostModel>() {
            @Override
            public void onResponse(Call<PostModel> call, Response<PostModel> response) {
                if (response.isSuccessful()) {
                    PostModel createdPost = response.body();
                    // Handle the created post as needed
                    // ...
                } else {
                    // Handle the error case
                    if (response.code() == 400) {
                        // Bad Request
                        String errorMessage = "Invalid request. Please check your input.";
                        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 401) {
                        // Unauthorized
                        String errorMessage = "You are not authorized to perform this action.";
                        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                        // You can also redirect the user to the login screen if needed
                        // startActivity(new Intent(PostActivity.this, LoginActivity.class));
                    } else if (response.code() == 404) {
                        // Not Found
                        String errorMessage = "Requested resource not found.";
                        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 500) {
                        // Internal Server Error
                        String errorMessage = "Internal server error. Please try again later.";
                        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    } else {
                        // Other error cases
                        String errorMessage = "An error occurred. Please try again.";
                        Log.e("API Error", errorMessage);
                        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PostModel> call, Throwable t) {
                // Handle the failure case
                // ...
            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_IMAGE_CODE && resultCode == RESULT_OK && data != null) {
            uri = data.getData();
            Glide.with(this).load(uri).into(img);

        }
    }


}