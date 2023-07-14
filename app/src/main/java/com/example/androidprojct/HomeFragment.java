package com.example.androidprojct;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.postrecicler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        TextView textPost = view.findViewById(R.id.textpost);
        postAdapter = new PostAdapter(getActivity());
        CardView cardView=view.findViewById(R.id.goCreatePost);
        rec=view.findViewById(R.id.postrecicler);
        rec.setLayoutManager(new LinearLayoutManager(getActivity()));
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), PostActivity.class));
            }
        });
        Call<List<PostModel>> call = RetrofitClient.getInstance().getApi().getPosts();

        call.enqueue(new Callback<List<PostModel>>() {
            @Override
            public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
                postAdapter = new PostAdapter(getActivity());
                if (response.isSuccessful()) {
                    List<PostModel> postResponse = response.body();
                    if (postResponse != null) {
                         //List<PostModel>posts = postResponse.getPosts();
                       // Toast.makeText(getActivity(), Integer.toString(postResponse.size()), Toast.LENGTH_SHORT).show();
                        // Set the retrieved posts to the adapter
                        postAdapter = new PostAdapter(getActivity());
                        //postAdapter= new PostAdapter(get);
                        postAdapter.setPosts(postResponse);
                        recyclerView.setAdapter(postAdapter);
                        postAdapter.notifyDataSetChanged();
                    } else {
                        // If the response doesn't have posts, retrieve posts from SharedPreferenceManager
                        SharedPreferenceManager sharedPreferenceManager = SharedPreferenceManager.getInstance(getActivity());
                        posts = sharedPreferenceManager.getPosts();
                        if (posts != null) {
                            postAdapter.setPosts(posts);
                        } else {
                            Toast.makeText(getActivity(), "No posts available", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), "Failed to retrieve posts", Toast.LENGTH_SHORT).show();
                }
            }







            @Override
            public void onFailure(Call<List<PostModel>> call, Throwable t) {
               // Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("MainActivity", t.getMessage());
            }
        });
    }
}

