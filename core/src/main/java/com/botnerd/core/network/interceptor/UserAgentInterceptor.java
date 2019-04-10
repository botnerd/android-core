package com.botnerd.core.network.interceptor;

import com.botnerd.core.network.request.RequestHeaders;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/* This interceptor adds a custom User-Agent. */
public class UserAgentInterceptor implements Interceptor {

    private final String userAgent;

    public UserAgentInterceptor(String userAgent) {
        this.userAgent = userAgent;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request requestWithUserAgent = originalRequest.newBuilder()
            .header(RequestHeaders.Keys.USER_AGENT, userAgent)
            .build();
        return chain.proceed(requestWithUserAgent);
    }
}