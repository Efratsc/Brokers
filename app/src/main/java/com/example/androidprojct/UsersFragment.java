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
                getPostsByServiceId(1);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPostsByServiceId(2);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPostsByServiceId(3);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPostsByServiceId(4);
            }
        });
    }

    private void getPostsByServiceId(int serviceId) {
        Api api = RetrofitClient.getInstance().getApi();
      serviceId = 1;
        int postId = 2;

        api.getPostsByServiceId(serviceId).enqueue(new Callback<List<PostModel>>() {
            @Override
            public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
                if (response.isSuccessful()) {
                    List<PostModel> posts = response.body();
                    if (posts != null && !posts.isEmpty()) {
                        // filter the list to find the post with the desired ID
                        PostModel post = null;
                        for (PostModel p : posts) {
                            if (p.getId() == postId) {
                                post = p;
                                //postAdapter = new PostAdapter(getActivity());
                                //postAdapter= new PostAdapter(get);
                                //postAdapter.setPosts(posts);
                                //recyclerView.setAdapter(postAdapter);
                                break;
                            }
                        }
                        if (post != null) {
                            // do something with the post
                        } else {
                            // post with the specified ID not found
                        }
                    } else {
                        // no posts available for the specified service ID
                    }
                } else {
                    // handle unsuccessful response
                }
            }

            @Override
            public void onFailure(Call<List<PostModel>> call, Throwable t) {
                // handle failure by retrieving posts from SharedPreferences
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

