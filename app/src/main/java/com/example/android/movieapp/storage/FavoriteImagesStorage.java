package com.example.android.movieapp.storage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;



public class FavoriteImagesStorage {

    public static void putImageInLocalStorage(String fileName, Bitmap imageStream) {
        try {
            File myImageFile = new File(getLocalStorageDirectory(), fileName + ".jpeg");
            myImageFile.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(myImageFile);
            imageStream.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap getImageFromLocalStorage(String fileName) {
        try {
            File localStorageDirectory = getLocalStorageDirectory();
            if (localStorageDirectory == null) return null;

            File myImageFile = new File(localStorageDirectory, fileName + ".jpeg");
            if (!myImageFile.exists()) return null;

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            return BitmapFactory.decodeFile(myImageFile.getAbsolutePath(), options);

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static boolean deleteImageFromLocalStorage(String fileName) {
       return new File(getLocalStorageDirectory(), fileName + ".jpeg").delete();
    }

    private static File getLocalStorageDirectory() {
        File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +File.separator+ "MoviesApp"+File.separator);
        if (!directory.exists()) directory.mkdir();
        return directory;
    }

}
