package com.example.admin.casinogames.UtilClass;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by omriGlam on 3/14/2015.
 */
public class utils {
    public static Bitmap decodeTobase64(String image) {
        byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    public static String encodeTobase64(Bitmap image) {
        System.gc();

        if (image == null) return null;

        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] b = baos.toByteArray();

        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT); // min minSdkVersion 8

        return imageEncoded;
    }
}
