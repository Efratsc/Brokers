package com.example.androidprojct;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MultipartInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        // Check if the request has a file to upload
        if (originalRequest.body() instanceof MultipartBody) {
            Request updatedRequest = originalRequest.newBuilder()
                    .header("Content-Type", "multipart/form-data")
                    .build();

            return chain.proceed(updatedRequest);
        }

        return chain.proceed(originalRequest);
    }
}

