package com.jibres.android.managers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

import com.jibres.android.activity.language.LanguageManager;

import java.util.HashMap;
import java.util.Map;

public class UrlManager {
    public static class get{
        public static String google = "http://www.google.com";
        public static String local = "https://jibres.com";
        static String api = "/api/v1";

        public static String local_api(Context context){
            return local + "/" +
                    LanguageManager.getAppLanguage(context)
                    + api + "/"
                    + AppManager.getStore(context);
        }
        public static String local_api_NOSTORE(Context context){

            return local + "/" +
                    LanguageManager.getAppLanguage(context);
        }

        public static String language_list = "/language";

        public static String country_list = "/location/country";
        public static String province_list = "/location/province";
        public static String city_list = "/location/city";

        public static String store = "/y885";

        public static String app_detail(Context context){
            return local_api(context)+"/app";
        }
        public static String language(Context context){
            return local_api(context)+"/language";
        }

        public static String token(Context context){
            return local_api(context)+"/account/token";
        }
        public static String add_user(Context context){
            return local_api(context)+"/account/android/add";
        }
        public static String enter_mobile(Context context){
            return local_api(context)+"/account/enter";
        }
        public static String verify_code(Context context){
            return local_api(context)+"/account/enter/verify";
        }
        public static String session(Context context){
            return local_api(context)+"/account/session";
        }

        public static String tiket_list(Context context,String page){
            return local_api(context)+"/ticket/list?page="+page;
        }
        public static String tiket_view(Context context,String tiket){
            return local_api(context)+"/ticket/"+tiket;
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
        public static final String SH_PREF_NAME = "ShPerfManager_Payamres";


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

        public Map<String, String> getstring_appINFO() {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put(url_site, sharedPreferences.getString(url_site, "https://salamquran.com" ));
            hashMap.put(url_kingdom, sharedPreferences.getString(url_kingdom, "https://salamquran.com/en" ));
            hashMap.put(url_domain, sharedPreferences.getString(url_domain, "salamquran.com" ));
            hashMap.put(url_root, sharedPreferences.getString(url_root, "salamquran" ));
            hashMap.put(url_update, sharedPreferences.getString(url_update, "https://salamquran.com/app/update" ));

            return hashMap;
        }
    }
}
