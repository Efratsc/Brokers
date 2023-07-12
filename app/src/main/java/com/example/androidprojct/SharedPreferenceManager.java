package com.example.androidprojct;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import androidx.preference.PreferenceManager;

import com.example.androidprojct.models.PostModel;
import com.example.androidprojct.models.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharedPreferenceManager {
    private static final String SHARED_PREF_NAME = "my_shared_preff";

    private static SharedPreferenceManager mInstance;
    private Context mCtx;
    private SharedPreferenceManager(Context mCtx) {
        this.mCtx = mCtx;
    }




    public static synchronized SharedPreferenceManager getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new SharedPreferenceManager(mCtx);
        }
        return mInstance;
    }


    public void saveUser(User user) {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("id", user.getId());
        editor.putString("email", user.getEmail());
        editor.putString("first name", user.getFirst_name());
        editor.putString("last name", user.getLast_name());
        editor.putString("phone number",user.getPhone_number());
        editor.putString("username",user.getUsername());
        editor.apply();

    }
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("id", -1) != -1;
    }
    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
               // sharedPreferences.getInt("id", -1),
                sharedPreferences.getString("first name", null),
                sharedPreferences.getString("last name", null),
                sharedPreferences.getString("phone number", null),
                sharedPreferences.getString("kebele",null),
                sharedPreferences.getString("woreda",null),
                sharedPreferences.getString("email",null),
                sharedPreferences.getString("username",null),
                sharedPreferences.getString("password",null)
        );
    }
    public void savePosts(List<PostModel> postList) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Convert the list of posts to a JSON string
        Gson gson = new Gson();
        String jsonPosts = gson.toJson(postList);

        // Save the JSON string in shared preferences
        editor.putString("posts", jsonPosts);
        editor.apply();
    }
    public static int getCurrentUserID(Context context) {
        // Get the shared preferences object
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        // Get the user ID from the shared preferences file
        int uid = sharedPreferences.getInt("user_id", -1);

        return uid;
    }
    public List<PostModel> getPosts() {
        List<PostModel> postList = new ArrayList<>();

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        int postCount = sharedPreferences.getInt("post_count", 0);

        for (int i = 0; i < postCount; i++) {
            int postId = sharedPreferences.getInt("post_id_" + i, -1);
            int userId = sharedPreferences.getInt("post_user_id_" + i, -1);
            String postText = sharedPreferences.getString("post_text_" + i, null);
            int serviceId = sharedPreferences.getInt("post_service_id_" + i, -1);
            String postImage = sharedPreferences.getString("post_image_" + i, null);


            PostModel postModel = new PostModel(postId, userId, postText, serviceId, postImage);
            postList.add(postModel);
        }

        return postList;
    }



    public static void setPostUserID(Context context, int postUserID) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("postUserID", postUserID);
        editor.apply();
    }
    private static final String PREF_USER_ID = "user_id";

    public static void setUserID(Context context, int userID) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(PREF_USER_ID, userID);
        editor.apply();
    }

    public static int getUserID(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(PREF_USER_ID, 0);
    }
    public static int getPostUserID(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt("postUserID", 0); // Provide a default value if the postUserID is not found
    }

    public String getUsername() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("username", null);
    }
    public void clear() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
