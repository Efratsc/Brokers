package com.example.androidprojct;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.androidprojct.models.PostModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UsersFragment extends Fragment {

    private Button btn1, btn2, btn3, btn4;
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private PostModel postModel;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        btn1 = view.findViewById(R.id.btn1);
        btn2 = view.findViewById(R.id.btn2);
        btn3 = view.findViewById(R.id.btn3);
        btn4 = view.findViewById(R.id.btn4);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        postAdapter = new PostAdapter(getActivity());
        recyclerView.setAdapter(postAdapter);
        setButtonClickListeners();
        return view;
    }

    private void setButtonClickListeners() {
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPostsByServiceId(1); // Pass the appropriate service ID
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPostsByServiceId(2); // Pass the appropriate service ID
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPostsByServiceId(3); // Pass the appropriate service ID
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPostsByServiceId(5); // Pass the appropriate service ID
            }
        });
    }
    private void getPostsByServiceId(int serviceId) {
        // Call your API or retrieve posts from Shared Preferences based on the service ID
        Call<PostResponse> call = RetrofitClient.getInstance().getApi().getPostsByServiceId(serviceId);
        call.enqueue(new Callback<PostResponse>() {


            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (response.isSuccessful()) {
                    if (response.isSuccessful()) {
                        // Get the response body
                        PostResponse postResponse = response.body();

                        // Convert the response to a string
                        String responseString = new Gson().toJson(postResponse);

                        // Log the response
                        Log.d("API Response", "Response: " + responseString);

                        // Parse the response and update UI
                        if (postResponse != null) {
                            if (postResponse.isError()) {
                                // Handle error response
                                Toast.makeText(getActivity(), "Error: " + postResponse.isError(), Toast.LENGTH_SHORT).show();
                            } else {
                                List<PostModel> posts = postResponse.getPosts();
                                if (posts != null && !posts.isEmpty()) {
                                    // Handle array response
                                    // Process the list of posts
                                } else {
                                    PostModel post = (PostModel) postResponse.getPosts();
                                    if (post != null) {
                                        // Handle object response
                                        // Process the single post
                                    } else {
                                        // Handle empty response
                                        Toast.makeText(getActivity(), "No posts available", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        } else {
                            // Handle null response
                            Toast.makeText(getActivity(), "Null response", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Handle unsuccessful response
                        Toast.makeText(getActivity(), "Failed to retrieve posts. Error code: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                // Handle failure by retrieving posts from SharedPreferences
                List<PostModel> storedPosts = SharedPreferenceManager.getInstance(requireContext()).getPosts();
                if (storedPosts != null && !storedPosts.isEmpty()) {
                    postAdapter.setPosts(storedPosts);
                    postAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), "Failed to retrieve posts: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("API Error", "Failed to retrieve posts", t);
                }
            }



        });
    }

}