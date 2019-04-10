package com.botnerd.core.util.connectivity;

import android.Manifest;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.annotation.RequiresPermission;

/**
 * @author tjudkins
 * @since 5/2/17
 */

public class ConnectivityUtils {
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isNetworkedConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null == cm) return false;

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}
