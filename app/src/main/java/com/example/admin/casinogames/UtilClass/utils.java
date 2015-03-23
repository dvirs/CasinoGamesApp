package com.example.admin.casinogames.UtilClass;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.util.Base64;

import com.example.admin.casinogames.SettingActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by omriGlam on 3/14/2015.
 */
public class utils {
    private static MediaPlayer mp;

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

    public static void playSound( final Context context,final int sound){
       if(SettingActivity.getBgMusicEnabled(context)) {
           new Thread(new Runnable() {
               @Override
               public void run() {
                   mp = MediaPlayer.create(context, sound);
                   try {
                       mp.prepare();

                   } catch (IllegalStateException e) {
                       e.printStackTrace();
                   } catch (IOException e) {
                       e.printStackTrace();
                   }

                   mp.start();

               }
           }).start();
       }
    }

    public static void endSound(final Context context){
        if(SettingActivity.getBgMusicEnabled(context)) {
            mp.release();
        }
    }
}
