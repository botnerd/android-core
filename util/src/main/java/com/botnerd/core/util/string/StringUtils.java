package com.botnerd.core.util.string;

/**
 * String Utils class since Android's TextUtils cannot be mocked for Unit Tests
 * Pulled from Android SDK's TextUtils class with other additions
 */
public final class StringUtils {

    private StringUtils() {
        throw new IllegalStateException("No instance allowed.");
    }

    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean equals(final CharSequence a, final CharSequence b) {
        if (a == b) return true;
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }

    public static String trimToNull(final String str) {
        final String trimmed = str == null ? null : str.trim();
        return isEmpty(trimmed) ? null : trimmed;
    }

    public static String trimToEmpty(final String str) {
        final String trimmed = str == null ? null : str.trim();
        return isEmpty(trimmed) ? "" : trimmed;
    }

    public static boolean isAlphaNumericAndSpace(String str){
        if(str == null) return false;
        return str.matches("^[a-zA-Z0-9 ]*$");
    }

    public static int length(final String str) {
        return str == null ? 0 : str.length();
    }


}
