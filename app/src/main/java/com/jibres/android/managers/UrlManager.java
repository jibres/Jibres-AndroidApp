package com.jibres.android.managers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

public class UrlManager {
    public static void save_endPoint(Context context,String url){
        save.get(context).save_endPoint(url);
    }



    public static class get{

        public static String endPoint(Context context){
            return save.get(context).getstring_appINFO().get(save.endPoint);
        }






        public static String app_detail(Context context){
            return endPoint(context)+"/app";
        }
        public static String language(Context context){
            return endPoint(context)+"/language";
        }

        public static String token(Context context){
            return endPoint(context)+"/account/token";
        }
        public static String add_user(Context context){
            return endPoint(context)+"/account/android/add";
        }
        public static String enter_mobile(Context context){
            return endPoint(context)+"/account/enter";
        }
        public static String verify_code(Context context){
            return endPoint(context)+"/account/enter/verify";
        }
        public static String session(Context context){
            return endPoint(context)+"/account/session";
        }

        public static String tiket_list(Context context,String page){
            return endPoint(context)+"/ticket/list?page="+page;
        }
        public static String tiket_view(Context context,String tiket){
            return endPoint(context)+"/ticket/"+tiket;
        }
        public static String tiket_replay(Context context,String tiket){
            return endPoint(context)+"/ticket/"+tiket+"/replay";
        }
        public static String tiket_set_status(Context context,String tiket){
            return endPoint(context)+"/ticket/"+tiket+"/status";
        }
        public static String tiket_set_solved(Context context,String tiket){
            return endPoint(context)+"/ticket/"+tiket+"/solved";
        }
        public static String tiket_add(Context context){
            return endPoint(context)+"/ticket/add";
        }


        //        Static URL
        public static String url_site(Context context){
            return save.get(context).getstring_appINFO().get(save.url_site);
        }
        public static String url_kingdom(Context context){
            return save.get(context).getstring_appINFO().get(save.url_kingdom);
        }
        public static String url_domain(Context context){
            return save.get(context).getstring_appINFO().get(save.url_domain);
        }
        public static String url_root(Context context){
            return save.get(context).getstring_appINFO().get(save.url_root);
        }
        public static String url_update(Context context){
            return save.get(context).getstring_appINFO().get(save.url_update);
        }



    }

    public static class save extends ContextWrapper {

        SharedPreferences.Editor editor;
        SharedPreferences sharedPreferences;
        public static final String SH_PREF_NAME = "SAVE_URL_JIBRES";


        @SuppressLint("CommitPrefEdits")
        private save(Context context) {
            super(context);
            sharedPreferences = getSharedPreferences(SH_PREF_NAME, MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
        public static save get(Context context) {
            return new save(context);
        }


        /** App Info */
        public static final String endPoint = "endPoint";
        public static final String url_site = "url_site";
        public static final String url_kingdom = "url_kingdom";
        public static final String url_domain = "url_domain";
        public static final String url_root = "url_root";
        public static final String url_update = "url_update";


        public void save_url(String site, String kingdom, String domain, String root, String update ) {
            if (site!=null){
                editor.putString(save.url_site, site);
            }
            if (kingdom!=null){
                editor.putString(save.url_kingdom, kingdom);
            }
            if (domain!=null){
                editor.putString(save.url_domain, domain);
            }
            if (root!=null){
                editor.putString(save.url_root, root);
            }
            if (update!=null){
                editor.putString(save.url_update, update);
            }
            editor.apply();
        }
        public void save_endPoint(String url ) {
            if (url!=null){
                editor.putString(save.endPoint, url);
                editor.apply();
            }
        }

        public Map<String, String> getstring_appINFO() {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put(endPoint, sharedPreferences.getString(endPoint, "https://core.jibres.com/r10" ));

            hashMap.put(url_site, sharedPreferences.getString(url_site, "https://salamquran.com" ));
            hashMap.put(url_kingdom, sharedPreferences.getString(url_kingdom, "https://salamquran.com/en" ));
            hashMap.put(url_domain, sharedPreferences.getString(url_domain, "salamquran.com" ));
            hashMap.put(url_root, sharedPreferences.getString(url_root, "salamquran" ));
            hashMap.put(url_update, sharedPreferences.getString(url_update, "https://salamquran.com/app/update" ));

            return hashMap;
        }
    }
}
