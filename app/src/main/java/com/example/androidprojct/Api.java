package com.example.androidprojct;

import com.example.androidprojct.models.Connections;
import com.example.androidprojct.models.Login;
import com.example.androidprojct.models.PostModel;
import com.example.androidprojct.models.User;
import com.example.androidprojct.models.UserRating;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Api {
    @GET("users")
    Call<List<User>> getUsers();

    @POST("users")
    Call<User> createUser(@Body User user);

    @PUT("users/{id}")
    Call<User> updateUser(@Path("id") int id, @Body User user);

    // Example: Delete a user
    @DELETE("users/{id}")
    Call<Void> deleteUser(@Path("id") int id);
    @GET("ratings/{id}")
    Call<UserRating> getRatingById(@Path("id") int id);
    @GET("login")
    Call<Login> getAllLogins();

    @GET("login/{id}")
    Call<Login> getLoginById(@Path("id") int id);
    @GET("users/{id}")
    Call<User> getUserById(@Path("id") int id);
    @GET("login/username/{username}")
    Call<LoginResponse> getLoginByUsername(@Path("username") String username);
    @GET("posts")
    Call<List<PostModel>> getPosts();
    @POST("/connections")
    Call<Connections> createConnection(@Body Connections connection);
    @POST("posts")
    Call<PostModel> createPost(@Body PostModel post);
    @GET("posts/service/{service_id}")
    Call<PostResponse> getPostsByServiceId(@Path("service_id") int serviceId);

    //@GET("posts/service/{id}")
    //Call<PostResponse> getPostsByServiceId(@Path("id") int serviceId);



}
