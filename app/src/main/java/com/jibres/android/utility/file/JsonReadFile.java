package com.jibres.android.utility.file;

import android.content.Context;

public class JsonReadFile {
    private static String folder_json = "json";
    private static String file_intro = "/intro_default.json";
    private static String file_splash = "/splash_default.json";

    public static String intro(Context context) {
        return FileUtil.ReadFileAssets(context, folder_json + file_intro);
    }

    public static String splash(Context context) {
        return FileUtil.ReadFileAssets(context, folder_json + file_splash);
    }
}
