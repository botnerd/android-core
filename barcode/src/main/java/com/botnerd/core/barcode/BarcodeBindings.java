package com.botnerd.core.barcode;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import androidx.databinding.BindingAdapter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.EnumMap;
import java.util.Map;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

/**
 * @author tjudkins
 * @since 2/13/17
 */

public class BarcodeBindings {

    @BindingAdapter({"qrcode"})
    public static void setQRCode(final ImageView imageView, final String codeString) {
        if (!TextUtils.isEmpty(codeString)) {
            //wait until view is ready so we can get get it's height/width to create the QR code
            ViewTreeObserver viewTreeObserver = imageView.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        imageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                        Resources resources = imageView.getContext().getResources();
                        int fallbackWidth = resources.getDimensionPixelSize(R.dimen.default_barcode_width);
                        int fallbackHeight = resources.getDimensionPixelSize(R.dimen.default_barcode_width);
                        int viewWidth = imageView.getWidth() > 0 ? imageView.getWidth() : fallbackWidth;
                        int viewHeight = imageView.getHeight() > 0 ? imageView.getHeight() : fallbackHeight;

                        try {
                            Bitmap bitmap = BarcodeBindings.createQrCode(codeString, viewWidth, viewHeight);
                            if (null != bitmap) {
                                Bitmap pq = Bitmap.createScaledBitmap(bitmap, viewWidth, viewHeight, true);
                                imageView.setImageBitmap(pq);
                            }
                        } catch (WriterException ignored) {
                        }
                    }
                });
            }

        }
    }

    @BindingAdapter({"barcode"})
    public static void setBarcode(final ImageView imageView, final String codeString) {
        if (!TextUtils.isEmpty(codeString)) {
            //wait until view is ready so we can get get it's height/width to create the QR code
            ViewTreeObserver viewTreeObserver = imageView.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        imageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                        Resources resources = imageView.getContext().getResources();
                        int fallbackWidth = resources.getDimensionPixelSize(R.dimen.default_barcode_width);
                        int fallbackHeight = resources.getDimensionPixelSize(R.dimen.default_barcode_width);
                        int viewWidth = imageView.getWidth() > 0 ? imageView.getWidth() : fallbackWidth;
                        int viewHeight = imageView.getHeight() > 0 ? imageView.getHeight() : fallbackHeight;

                        try {
                            Bitmap bitmap = BarcodeBindings.createBarcode(codeString, viewWidth, viewHeight);
                            if (null != bitmap) {
                                Bitmap pq = Bitmap.createScaledBitmap(bitmap, viewWidth, viewHeight, true);
                                imageView.setImageBitmap(pq);
                            }
                        } catch (Exception ignored) {
                        }
                    }
                });
            }

        }
    }

    /**
     * create QR code bitmap
     *
     * @param str    string to be encoded
     * @param width  bitmap width
     * @param height bitmap height
     * @return bitmap
     * @throws WriterException
     */
    public static Bitmap createQrCode(String str, int width, int height) throws WriterException {
        BitMatrix result;
        try {

            //add hint to have 0 padding/margin in bitmap
            Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
            hints.put(EncodeHintType.MARGIN, 0);
            result = new QRCodeWriter().encode(str,
                    BarcodeFormat.QR_CODE, width, height, hints);
        } catch (IllegalArgumentException iae) {
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, w, h);
        return bitmap;
    }

    /**
     * create barcode bitmap
     *
     * @param str    string to be encoded
     * @param width  bitmap width
     * @param height bitmap height
     * @return bitmap
     * @throws WriterException when bitmap fails to be written
     */
    public static Bitmap createBarcode(String str, int width, int height) throws WriterException {
        BitMatrix result;
        try {

            //add hint to have 0 padding/margin in bitmap
            Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
            hints.put(EncodeHintType.MARGIN, 0);
            result = new Code128Writer().encode(str,
                    BarcodeFormat.CODE_128, width, height, hints);
        } catch (IllegalArgumentException iae) {
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, w, h);
        return bitmap;
    }
}
