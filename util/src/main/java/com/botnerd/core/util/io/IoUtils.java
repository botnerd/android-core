package com.botnerd.core.util.io;

import android.os.Build;
import androidx.annotation.RequiresApi;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.Locale;

public class IoUtils {

    /**
     * Convert byte array to hex string
     * @param bytes array to convert to hex string
     * @return hex string
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sbuf = new StringBuilder();
        for (byte aByte : bytes) {
            int intVal = aByte & 0xff;
            if (intVal < 0x10) sbuf.append("0");
            sbuf.append(Integer.toHexString(intVal).toUpperCase(Locale.US));
        }
        return sbuf.toString();
    }

    /**
     * Get utf8 byte array.
     * @param str string to convert
     * @return  array of NULL if error was found
     */
    @SuppressWarnings("CharsetObjectCanBeUsed")
    public static byte[] getUTF8Bytes(String str) {
        try { return str.getBytes("UTF-8"); } catch (Exception ex) { return null; }
    }

    /**
     * Load UTF8withBOM or any ansi text file.
     * @param filename file name
     * @return file contents as string
     * @throws java.io.IOException IO errors from file loading
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressWarnings("CharsetObjectCanBeUsed")
    public static String loadFileAsString(String filename) throws java.io.IOException {
        final int BUFLEN=1024;
        try (BufferedInputStream is = new BufferedInputStream(new FileInputStream(filename), BUFLEN)) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(BUFLEN);
            byte[] bytes = new byte[BUFLEN];
            boolean isUTF8 = false;
            int read, count = 0;
            while ((read = is.read(bytes)) != -1) {
                if (count == 0 && bytes[0] == (byte) 0xEF && bytes[1] == (byte) 0xBB && bytes[2] == (byte) 0xBF) {
                    isUTF8 = true;
                    baos.write(bytes, 3, read - 3); // drop UTF8 bom marker
                } else {
                    baos.write(bytes, 0, read);
                }
                count += read;
            }
            return isUTF8 ? new String(baos.toByteArray(), "UTF-8") : new String(baos.toByteArray());
        }
    }


}