package com.jibres.android.utility;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class Database extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "khadijeApp.db";

    /*Table Get SMS*/
    public static String table_del = "del";
    public static String del_id ="id";
    public static String del_liked ="liked";

    public static final int DATABASE_VERSION = 1;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        setForcedUpgrade();
    }

    public static String insetTo_del(String id,String liked){
        return "INSERT INTO "
                + table_del+ "("
                + del_id +","
                + del_liked + ")"
                + "Values ("
                + "'"+id+"',"
                + " '"+liked+"' )";
    }


    public static String select_del(String id){
        return "SELECT * FROM "+table_del+
                " WHERE "+del_id + " = '"+id+"' "+
                " AND "+del_liked + " = 'true' limit 1";


    }




}