package com.botnerd.core;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import com.botnerd.core.ui.BuildConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import timber.log.Timber;

/**
 * @author tjudkins
 * @since 1/30/17
 */
public abstract class CoreApplication extends Application {

	private static CoreApplication sInstance;

    @Override
	public void onCreate() {
		super.onCreate();

		sInstance = this;

		if (BuildConfig.DEBUG) {
			Timber.plant(new Timber.DebugTree());
		}

		Fresco.initialize(this);

    }

	/**
	 * @return PackageInfo
	 */
	public static PackageInfo getPackageInfo() {
		PackageInfo packageInfo = null;
		try {
			Context context = getAppContext();
			packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
		}
		catch (NameNotFoundException e) {
			Timber.e("getPackageInfo: exception trying to get package info");
		}

		return packageInfo;
	}

	/**
	 * Gets the {@link Context} of the {@link Application}. NOTE: This should only ever
	 * be called after {@link #onCreate()}, and only used where an application context will suffice
	 * (do not use for anything that needs a theme, like inflating views or manipulating the user
	 * interface).
	 *
	 * @return The application context
	 */
	public static Context getAppContext() {
		if (sInstance == null) {
			throw new IllegalStateException("Attempting to access application context before the application has been created");
		}
		return sInstance.getApplicationContext();
	}

}
