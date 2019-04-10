package com.botnerd.core.network.interceptor;

import com.botnerd.core.network.state.CookieStore;
import okhttp3.Interceptor;
import okhttp3.Response;

import java.io.IOException;
import java.util.Set;

public class ReceivedCookiesInterceptor implements Interceptor {

    public ReceivedCookiesInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            Set<String> cookies = CookieStore.getCookies();

            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
            }

            CookieStore.setCookies(cookies);
        }

        return originalResponse;
    }
}