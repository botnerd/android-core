package com.botnerd.core.network.interceptor;

import android.text.TextUtils;
import com.botnerd.core.network.state.CookieStore;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * This interceptor put all the Cookies in Preferences in the Request.
 * Your implementation on how to get the Preferences may ary, but this will work 99% of the time.
 */
public class AddCookiesInterceptor implements Interceptor {

    public AddCookiesInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

        if (!TextUtils.isEmpty(CookieStore.getCookieString())) {
            builder.addHeader("Cookie", CookieStore.getCookieString());
        }

        return chain.proceed(builder.build());
    }
}