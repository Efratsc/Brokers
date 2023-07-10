package com.example.androidprojct;

import com.example.androidprojct.models.Connections;
import com.example.androidprojct.models.Login;
import com.example.androidprojct.models.PostModel;
import com.example.androidprojct.models.Transaction;
import com.example.androidprojct.models.User;
import com.example.androidprojct.models.UserRating;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface Api {
    @GET("users")
    Call<List<User>> getUsers();

    @POST("users")
    Call<User> createUser(@Body User user);

    @PUT("users/{id}")
    Call<UserResponse> updateUser(
            @Path("user_id") int userId,
            @Field("email") String email,
            @Field("phone_number") String phone,
            @Field("first_name") String firstName
    );


    // Example: Delete a user
    @DELETE("users/{id}")
    Call<DefaultResponse> deleteUser(@Path("id") int id);
    @GET("ratings/{id}")
    Call<UserRating> getRatingById(@Path("id") int id);
    @GET("login")
    Call<Login> getAllLogins();
    @POST("transactions")
    Call<Transaction> createTransaction(@Body Transaction transaction);
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
    @Multipart
    @POST("posts")
    Call<PostModel> createPost(
            @Part("user_id") RequestBody userId,
            @Part("post_text") RequestBody postText,
            @Part("service_id") RequestBody serviceId,
            @Part("post_image") RequestBody imageName,
            @Part MultipartBody.Part postImage
    );

    @POST("posts")
    Call<PostModel> createPostWithPhoto(@Body PostModel post, @Part MultipartBody.Part photo);

    @GET("posts/service/{service_id}")
    Call<PostResponse> getPostsByServiceId(@Path("service_id") int serviceId);
    @PUT("users/{id}")
    Call<UserResponse> updateUser(User user);


    //@GET("posts/service/{id}")
    //Call<PostResponse> getPostsByServiceId(@Path("id") int serviceId);



}
