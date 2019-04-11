package com.botnerd.core.network.state;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;

import java.util.HashSet;
import java.util.Set;

/**
 * @author tjudkins
 * @since 1/31/17
 *
 * TODO This needs to be updated to handle multiple service endpoints
 */

public class CookieStore {

    private static Set<String> cookies;

    public static synchronized @NonNull
    Set<String> getCookies() {
        if (null == cookies) {
            cookies = new HashSet<>();
        }
        return cookies;
    }

    public static synchronized String getCookieString() {
        Set<String> cookies = getCookies();

        StringBuilder sb = new StringBuilder();
        for (String cookie : cookies) {
            String[] parser = cookie.split(";");
            sb.append(parser[0])
                    .append("; ");
        }

        return sb.toString();
    }

    @SuppressLint("CommitPrefEdits")
    public static synchronized void setCookies(Set<String> newCookies) {
        cookies = newCookies;
    }

    public static void clear() {
        setCookies(new HashSet<>());
    }
}
