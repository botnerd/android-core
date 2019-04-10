package com.botnerd.core.network.request;

/**
 * Request header constants needed for network calls.
 *
 * @author tjudkins
 */
public class RequestHeaders {

	public static final String HEADER_CONTENT_TYPE_APPLICATION_JSON =
			Keys.CONTENT_TYPE + ": " + Values.CONTENT_TYPE_APPLICATION_JSON;

	@SuppressWarnings("WeakerAccess")
	public static class Keys {
		public static final String ACCEPT = "Accept";
		public static final String ACCEPT_LANGUAGE = "Accept-Language";
		public static final String CONTENT_TYPE = "Content-Type";
		public static final String AUTHORIZATION = "Authorization";
		public static final String USER_AGENT = "User-Agent";
	}

	@SuppressWarnings("WeakerAccess")
	public static class Values {
		public static final String ACCEPT_APPLICATION_JSON = "application/json";
		public static final String ACCEPT_LANGUAGE_EN_US = "en-US";
		public static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";
	}
}