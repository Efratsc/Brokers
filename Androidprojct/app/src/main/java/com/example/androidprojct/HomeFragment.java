package com.example.androidprojct;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidprojct.models.PostModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    View view;
    private PostAdapter postAdapter;
    private List<PostModel> posts;
    RecyclerView recyclerView,rec;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.postrecicler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        TextView textPost = view.findViewById(R.id.textpost);
        /*textPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the PostActivity
                Intent intent = new Intent(getActivity(), PostActivity.class);
                startActivity(intent);
            }
        });*/
        // Set up the adapter
        postAdapter = new PostAdapter(getActivity(), new ArrayList<>());
        //rec.setAdapter(postAdapter);
        CardView cardView=view.findViewById(R.id.goCreatePost);
        rec=view.findViewById(R.id.postrecicler);

        rec.setLayoutManager(new LinearLayoutManager(getActivity()));
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), PostActivity.class));
            }
        });
        Call<PostResponse> call= RetrofitClient.getInstance().getApi().getPosts();
        call.enqueue(new Callback<PostResponse>() {

            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (response.isSuccessful()) {
                    PostResponse postResponse = response.body();
                    if (postResponse != null) {
                        List<PostModel>posts = postResponse.getPosts();

                        if (posts != null) {
                            // Set the retrieved posts to the adapter
                            postAdapter.setPosts(posts);
                            postAdapter.notifyDataSetChanged();
                        } else {
                            // If the response doesn't have posts, retrieve posts from SharedPreferenceManager
                            SharedPreferenceManager sharedPreferenceManager = SharedPreferenceManager.getInstance(getActivity());
                            posts = sharedPreferenceManager.getPosts();
                            if (posts != null) {
                                postAdapter.setPosts(posts);
                                postAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getActivity(), "No posts available", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(getActivity(), "Failed to retrieve posts", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Failed to retrieve posts. Error code: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {

            }
        });
       // postAdapter=new PostAdapter(this);
       // return view;
    }

}