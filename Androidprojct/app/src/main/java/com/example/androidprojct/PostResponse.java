package com.example.androidprojct;

import com.example.androidprojct.models.PostModel;

import java.util.List;

public class PostResponse {
    private boolean error;
    private List<PostModel>posts;

    public PostResponse(boolean error, List<PostModel> posts) {
        this.error = error;
        this.posts = posts;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<PostModel> getPosts() {
        return posts;
    }

    public void setPosts(List<PostModel> posts) {
        this.posts = posts;
    }
}

