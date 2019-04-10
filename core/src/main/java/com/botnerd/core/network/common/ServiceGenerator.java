package com.botnerd.core.network.common;

import androidx.annotation.NonNull;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public interface ServiceGenerator {

    @NonNull
    String getBaseUrl();

    @NonNull Retrofit.Builder getRetrofitBuilder();

    @NonNull OkHttpClient.Builder getClientBuilder();

}