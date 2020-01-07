package com.jibres.android.managers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

public class JsonManager extends ContextWrapper {
    public static String intro_en = "[{\"title\": \"Say hello to Quran! Quran is calling you..\", \"desc\": \"Read Quran By font Osman Taha\", \"bg_from\": \"#ffffff\", \"bg_to\": \"#ffffff\", \"title_color\": \"#000000\", \"desc_color\": \"#000000\", \"image\": null, \"btn\": [{\"title\": \"Next\", \"action\": \"next\"} ] }, {\"title\": \"Recitation of the Qur'an's By Qari Prominent \", \"desc\": \"Easily use recitation and illustrative translations\", \"bg_from\": \"#ffffff\", \"bg_to\": \"#ffffff\", \"title_color\": \"#000000\", \"desc_color\": \"#000000\", \"image\": null, \"btn\": [{\"title\": \"Prev\", \"action\": \"prev\"}, {\"title\": \"Next\", \"action\": \"next\"} ] }, {\"title\": \"Quick and easy learning of the Holy Quran\", \"desc\": \"Simply increase your Quranic skills\", \"bg_from\": \"#ffffff\", \"bg_to\": \"#ffffff\", \"title_color\": \"#000000\", \"desc_color\": \"#000000\", \"image\": null, \"btn\": [{\"title\": \"Prev\", \"action\": \"prev\"}, {\"title\": \"Next\", \"action\": \"next\"} ] }, {\"title\": \"Completely Free\", \"desc\": \"Take advantage of all the possibilities of Salam Quran for free!\", \"bg_from\": \"#ffffff\", \"bg_to\": \"#ffffff\", \"title_color\": \"#000000\", \"desc_color\": \"#000000\", \"image\": null, \"btn\": [{\"title\": \"Start\", \"action\": \"start\"} ] } ]";
    public static String intro_fa = "[{\"title\": \"به قرآن سلام کن! قرآن تو را میخواند..\", \"desc\": \"با قلم آشنای عثمان طه، راحت تر از همیشه قرآن بخوانید\", \"bg_from\": \"#ffffff\", \"bg_to\": \"#ffffff\", \"title_color\": \"#000000\", \"desc_color\": \"#000000\", \"image\": null, \"btn\": [{\"title\": \"بعدی\", \"action\": \"next\"} ] }, {\"title\": \"تلاوت قاریان برجسته قرآن\", \"desc\": \"به آسانی از تلاوت ها و ترجمه های گویا استفاده کنید\", \"bg_from\": \"#ffffff\", \"bg_to\": \"#ffffff\", \"title_color\": \"#000000\", \"desc_color\": \"#000000\", \"image\": null, \"btn\": [{\"title\": \"قبلی\", \"action\": \"prev\"}, {\"title\": \"بعدی\", \"action\": \"next\"} ] }, {\"title\": \"یادگیری سریع و آسان قرآن کریم\", \"desc\": \"به سادگی مهارت های قرآنی خود را افزایش دهید\", \"bg_from\": \"#ffffff\", \"bg_to\": \"#ffffff\", \"title_color\": \"#000000\", \"desc_color\": \"#000000\", \"image\": null, \"btn\": [{\"title\": \"قبلی\", \"action\": \"prev\"}, {\"title\": \"بعدی\", \"action\": \"next\"} ] }, {\"title\": \"دسترسی کاملا رایگان\", \"desc\": \"از تمامی امکانات سلام قرآن به صورت رایگان استفاده کنید!\", \"bg_from\": \"#ffffff\", \"bg_to\": \"#ffffff\", \"title_color\": \"#000000\", \"desc_color\": \"#000000\", \"image\": null, \"btn\": [{\"title\": \"شروع\", \"action\": \"start\"} ] } ]";
    public static String intro_ar = "[{\"title\": \"قل سلام للقرآن! القرآن يقرأ لك ..\", \"desc\": \"بمع القلم المألوف عثمان طه ، من الأسهل قراءة القرآن من أي وقت مضى\", \"bg_from\": \"#ffffff\", \"bg_to\": \"#ffffff\", \"title_color\": \"#000000\", \"desc_color\": \"#000000\", \"image\": null, \"btn\": [{\"title\": \"التالي\", \"action\": \"next\"} ] }, {\"title\": \"تلاوة قراء المشهور القرآن الكريم\", \"desc\": \"بسهولة استخدام التلاوة والترجمات الصوتية\", \"bg_from\": \"#ffffff\", \"bg_to\": \"#ffffff\", \"title_color\": \"#000000\", \"desc_color\": \"#000000\", \"image\": null, \"btn\": [{\"title\": \"سابق\", \"action\": \"prev\"}, {\"title\": \"التالي\", \"action\": \"next\"} ] }, {\"title\": \"التعلم السريع والسهل للقرآن الكريم\", \"desc\": \"زيادة مهاراتك القرآنية\", \"bg_from\": \"#ffffff\", \"bg_to\": \"#ffffff\", \"title_color\": \"#000000\", \"desc_color\": \"#000000\", \"image\": null, \"btn\": [{\"title\": \"سابق\", \"action\": \"prev\"}, {\"title\": \"التالي\", \"action\": \"next\"} ] }, {\"title\": \"حرية الوصول تماما\", \"desc\": \"استخدام جميع ميزات سلام قرآن مجانا!\", \"bg_from\": \"#ffffff\", \"bg_to\": \"#ffffff\", \"title_color\": \"#000000\", \"desc_color\": \"#000000\", \"image\": null, \"btn\": [{\"title\": \"بداية\", \"action\": \"start\"} ] } ]";


    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    public static final String SH_PREF_NAME = "ShPerfManager_Jibres_JsonManager";

    @SuppressLint("CommitPrefEdits")
    private JsonManager(Context context) {
        super(context);
        sharedPreferences = getSharedPreferences(SH_PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public static JsonManager get(Context context) {
        return new JsonManager(context);
    }

    /** App Info */
    public static final String jsonIntro = "jsonIntro";

    public void setJsonIntro(String json) {
        editor.putString(jsonIntro, json);
        editor.apply();
    }

    public Map<String, String> getJsonIntro_fromSaveManager() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(jsonIntro, sharedPreferences.getString(jsonIntro,intro_en));
        return hashMap;
    }

    public static String getJsonIntro(Context context){
        return JsonManager
                .get(context)
                .getJsonIntro_fromSaveManager()
                .get(JsonManager.jsonIntro);
    }


}
