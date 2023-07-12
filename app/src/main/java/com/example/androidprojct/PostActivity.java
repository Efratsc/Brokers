package com.example.androidprojct;

import static com.example.androidprojct.SharedPreferenceManager.getCurrentUserID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.bumptech.glide.request.RequestOptions;
import com.example.androidprojct.models.PostModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostActivity extends AppCompatActivity {
    ImageView img;
    CardView cardView;
    int SELECT_IMAGE_CODE = 1;
    Uri uri;
    int service_id = 0;
    int userId = getCurrentUserID(this);

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
    private void createPost() {
        EditText postEditText = findViewById(R.id.editpost);
        String postText = postEditText.getText().toString();
        userId = SharedPreferenceManager.getCurrentUserID(getApplicationContext());
        Spinner serviceSpinner = findViewById(R.id.serviceSpinner);
        String selectedService = serviceSpinner.getSelectedItem().toString();

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

        if (uri != null) {
            File imageFile = new File(getRealPathFromURI(uri));
            String postImageName = userId + "_" + System.currentTimeMillis() + ".jpg";
            Log.d("imagename",postImageName);
            String imageUploadUrl = "http://localhost:3000/image/" + postImageName;
            RequestBody imageRequestBody = RequestBody.create(MediaType.parse("image/jpeg"), imageFile);
            MultipartBody.Part photoPart = MultipartBody.Part.createFormData("post_image", postImageName, imageRequestBody);

            RequestBody postTextRequestBody = RequestBody.create(MediaType.parse("text/plain"), postText);
            RequestBody userIdRequestBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(userId));
            RequestBody serviceIdRequestBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(service_id));
            RequestBody imageNameRequestBody = RequestBody.create(MediaType.parse("text/plain"), postImageName);
            Log.d("imgid",String.valueOf(userId));
            Log.d("serviceid",String.valueOf(service_id));
            Call<PostModel> createPostCall = RetrofitClient.getInstance().getApi().createPost(
                    userId,
                    postTextRequestBody,
                    serviceIdRequestBody,
                    imageNameRequestBody,
                    photoPart
            );


            createPostCall.enqueue(new Callback<PostModel>() {
                @Override
                public void onResponse(Call<PostModel> call, Response<PostModel> response) {
                    if (response.isSuccessful()) {
                        PostModel createdPost = response.body();
                        int postId = createdPost.getId();
                        String postText = createdPost.getPost_text();
                        postEditText.setText("");
                        serviceSpinner.setSelection(0);
                        int userId = SharedPreferenceManager.getCurrentUserID(getApplicationContext());
                    } else {
                        String errorMessage = "";
                        try {
                            if (response.errorBody() != null) {
                                errorMessage = response.errorBody().string();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(PostActivity.this, "Failed to create a post: " + errorMessage, Toast.LENGTH_LONG).show();
                        Log.e("PostActivity", "Failed to create a post: " + errorMessage);
                    }
                }

                @Override
                public void onFailure(Call<PostModel> call, Throwable t) {
                    Toast.makeText(PostActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(PostActivity.this, "Please select a photo", Toast.LENGTH_LONG).show();
        }
    }
    private String getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String filePath = cursor.getString(columnIndex);
        cursor.close();
        return filePath;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_IMAGE_CODE && resultCode == RESULT_OK && data != null) {
            uri = data.getData();
            Glide.with(this).load(uri).into(img);

        }
    }


}