package com.jibres.android.utility;

import android.content.Context;

public class SecretReadFile {
    private static String folder_secret = "secret";
    private static String endpoint = "/endpoint.txt";
    private static String endpoint_test = "/endpoint_test.txt";
    private static String store = "/store.txt";

    public static String endpoint_test(Context context){
        return ReadFile(context,endpoint_test);
    }
    public static String endpoint(Context context){
        return ReadFile(context,endpoint);
    }
    public static String store(Context context){
        return ReadFile(context,store);
    }

    private static String ReadFile(Context context,String FileName){
        return FileUtil.ReadFileAssets(context,folder_secret+FileName);
    }
}
