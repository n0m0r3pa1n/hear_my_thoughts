package com.nmp90.hearmythoughts.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by nmp on 15-3-15.
 */
public class FileUtils {
    public static final String TAG = FileUtils.class.getSimpleName();
    public static String readFileFromAssets(Context context, String fileName) {
        String tContents = "";
        try {
            InputStream stream = context.getAssets().open(fileName);

            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            tContents = new String(buffer);
        } catch (IOException e) {
            // Handle exceptions here
        }

        return tContents;
    }
}
