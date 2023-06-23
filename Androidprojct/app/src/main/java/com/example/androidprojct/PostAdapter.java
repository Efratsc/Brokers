package com.example.androidprojct;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidprojct.models.PostModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {
    private Context context;
    private List<PostModel> postModelList;

    public PostAdapter(Context context, List<PostModel> postModelList) {
        this.context = context;
        this.postModelList = postModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PostModel post = postModelList.get(position);

       // holder.username.setText(post.getUsername());
        holder.textpost.setText(post.getPost_text());
        holder.bind(post);

        // Load user profile image using Picasso library
       // Picasso.get().load(post.getUserProfileImage()).into(holder.userprofile);

        // Load post image using Picasso library
        Picasso.get().load(post.getPost_image()).into(holder.postimage);

        // Set other data to the remaining views (rate, comment)
        //holder.rate.setText(String.valueOf(post.getRate()));
       // holder.comment.setText(String.valueOf(post.getCommentCount()));

        // Click listener for rate view
        holder.connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the Terms and Conditions activity
                Intent intent = new Intent(context, TermsAndConditionsActivity.class);
                context.startActivity(intent);
            }
        });
        holder.rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle rate view click event
            }
        });

        // Click listener for comment view
        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    public void setPosts(List<PostModel> posts) {
        postModelList = posts;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return postModelList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView username, textpost, rate, comment;
        private Button connect;
        private int userId;
        private ImageView userprofile, postimage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            textpost = itemView.findViewById(R.id.postText);
            userprofile = itemView.findViewById(R.id.userimage);
            postimage = itemView.findViewById(R.id.postimage);
            rate = itemView.findViewById(R.id.rate);
            comment = itemView.findViewById(R.id.connect);
            connect=itemView.findViewById(R.id.connect);
        }
        public void bind(PostModel post) {
            // Set the user_id
            userId = post.getUser_id();

            // Bind other data to the views
            // ...
        }
    }
}
