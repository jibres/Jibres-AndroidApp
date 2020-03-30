package com.jibres.android.utility.file;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class FileUtil {
    public static String ReadFileAssets(Context context, String FileName) {
        try {
            InputStream is = context.getAssets().open(FileName);
            // We guarantee that the available method returns the total
            // size of the asset...  of course, this does mean that a single
            // asset can't be more than 2 gigs.
            int size = is.available();
            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            // Convert the buffer into a string.
            String text = new String(buffer);
            // Finally stick the string into the text view.
            // Replace with whatever you need to have the text into.
            if (text.length() == 0) {
                return null;
            } else {
                return text;
            }
        } catch (IOException e) {
            // Should never happen!
            throw new RuntimeException(e);
        }
    }
}