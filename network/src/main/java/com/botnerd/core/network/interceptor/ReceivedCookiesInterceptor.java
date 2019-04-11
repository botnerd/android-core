package com.botnerd.core.network.interceptor;

import com.botnerd.core.network.state.CookieStore;
import okhttp3.Interceptor;
import okhttp3.Response;
import retrofit2.internal.EverythingIsNonNull;

import java.io.IOException;
import java.util.Set;

public class ReceivedCookiesInterceptor implements Interceptor {

    public ReceivedCookiesInterceptor() {
    }

    @Override
    public Response intercept(@EverythingIsNonNull Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            Set<String> cookies = CookieStore.getCookies();

            cookies.addAll(originalResponse.headers("Set-Cookie"));

            CookieStore.setCookies(cookies);
        }

        return originalResponse;
    }
}