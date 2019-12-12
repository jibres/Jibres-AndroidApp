package com.jibres.android.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jibres.android.Static.file;
import com.jibres.android.Static.format;
import com.jibres.android.Static.lookServer;
import com.jibres.android.Static.value;
import com.jibres.android.utility.FileManager;
import com.jibres.android.MainApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class apiV6 {

    public static void app0(Context context, final appListener appListener){
        final String TAG = "api/v1";
        try {
            String settingApp = FileManager.read_FromStorage(context, file.setting, format.json);
            JSONObject mainObject = new JSONObject(settingApp);
            JSONObject result = mainObject.getJSONObject("result");

            appListener.lestener_GetRespone(String.valueOf(result));

            try {
                JSONObject url = result.getJSONObject("url");
                String url_update = url.getString("update");
                JSONObject version = result.getJSONObject("version");
                String update_title = version.getString("update_title");
                String update_desc = version.getString("update_desc");
                appListener.lestener_Updateversion(url_update,update_title,update_desc);
            }catch (Exception e){
                Log.e(TAG, "api/v1/app: url || version ",e);
            }

            try {
                JSONArray homepage = result.getJSONArray("homepage");
                for (int i = 0; i < homepage.length(); i++) {
                    JSONObject object_homepage = homepage.getJSONObject(i);
                    String type = object_homepage.getString("type");
                    switch (type){
                        case "banner":
                            try {
                                String baner_image = object_homepage.getString("image");
                                String baner_url = object_homepage.getString("url");
                                String baner_target = null;
                                if (!object_homepage.isNull("target")){
                                    baner_target = object_homepage.getString("target");
                                }
                                appListener.lestener_baner(baner_image,baner_url,baner_target);
                                break;
                            }catch (Exception e){
                                Log.e(TAG, "onResponse: banner",e );
                            }

                        case "slider":
                            try {
                                JSONArray slider_homepage = object_homepage.getJSONArray("slider");
                                appListener.lestener_slider(String.valueOf(slider_homepage));
                                break;
                            }catch (Exception e){
                                Log.e(TAG, "onResponse: slider",e );
                                break;
                            }
                        case "link1":
                            try {
                                String image_link1 = object_homepage.getString("image");
                                String url_link1 = object_homepage.getString("url");
                                String link1_target = null;
                                if (!object_homepage.isNull("target")){
                                    link1_target = object_homepage.getString("target");
                                }
                                appListener.lestener_link_1(image_link1,url_link1,link1_target);
                                break;
                            }catch (Exception e){
                                Log.e(TAG, "onResponse: link1",e );
                                break;
                            }
                        case "link2":
                            try {
                                JSONArray link2_homepage = object_homepage.getJSONArray("curl");
                                appListener.lestener_link_2(String.valueOf(link2_homepage));
                                break;
                            }catch (Exception e){
                                Log.e(TAG, "onResponse: link2",e );
                                break;
                            }
                        case "linkdesc":
                            try {
                                String title,desc,image,url;
                                title = object_homepage.getString("title");
                                desc = object_homepage.getString("desc");
                                image = object_homepage.getString("image");
                                url = object_homepage.getString("url");
                                appListener.lestener_link_3_desc(title,desc,image,url);
                                break;
                            }catch (Exception e){
                                Log.e(TAG, "onResponse: linkdesc",e );
                                break;
                            }
                        case "link4":

                            try {
                                JSONArray link4_homepage = object_homepage.getJSONArray("curl");
                                appListener.lestener_link_4(String.valueOf(link4_homepage));
                                break;

                            }catch (Exception e){
                                Log.e(TAG, "onResponse: link4",e );
                                break;
                            }
                        case "inapplink":
                        case "titlelink":
                            try {
                                String titlelink_title = object_homepage.getString("title");
                                String titlelink_url = object_homepage.getString("curl");
                                String titlelink_target = null;
                                if (!object_homepage.isNull("target")){
                                    titlelink_target = object_homepage.getString("target");
                                }
                                appListener.lestener_title_link(titlelink_title,null,titlelink_url,titlelink_target);
                                break;
                            }catch (Exception e){
                                Log.e(TAG, "onResponse: inapplink || titlelink",e );
                                break;
                            }
                        case "title":
                            try {
                                String titleNONE_title = object_homepage.getString("title");
                                appListener.lestener_title_none(titleNONE_title);
                                break;
                            }catch (Exception e){
                                Log.e(TAG, "onResponse: title",e );
                                break;
                            }
                        case "salawat":
                            try {
                                int count_salawat = 0;
                                if (!object_homepage.isNull("count")){
                                    count_salawat = object_homepage.getInt("count");
                                }
                                appListener.lestener_salavat(count_salawat);
                                break;
                            }catch (Exception e){
                                Log.e(TAG, "onResponse: salawat",e );
                                break;
                            }
                        case "news":
                            try {
                                JSONArray news = object_homepage.getJSONArray("news");
                                appListener.lestener_news(String.valueOf(news));
                                break;
                            }catch (Exception e){
                                Log.e(TAG, "onResponse: news",e );
                                break;
                            }
                        case "hr":
                            try {
                                appListener.lestener_hr();
                                break;
                            }catch (Exception e){
                                Log.e(TAG, "onResponse: hr",e );
                                break;
                            }
                        case "change_language":
                            try {
                                appListener.lestener_language();
                                break;
                            }catch (Exception e){
                                Log.e(TAG, "onResponse: change_language",e );
                                break;
                            }
                    }

                    if (i == homepage.length()-1){
                        appListener.lestener_versionApp();
                    }
                }
            }catch (Exception e){
                Log.e(TAG, "api/v1/app: hompage ",e);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            appListener.error();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void app(String url ,final appListener appListener){

        final String TAG = "api/v1";

        StringRequest mainRQ = new StringRequest(Request.Method.GET,url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                appListener.error();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> sernd_headers = new HashMap<>();
                sernd_headers.put("x-app-request", "android");
                sernd_headers.put("versionCode",String.valueOf(value.versionCode));
                sernd_headers.put("versionName",value.versionName);
                return sernd_headers;
            }
        }
        ;

        MainApplication.getInstance().addToRequestQueue(mainRQ);
    }

    public interface appListener{
        void lestener_GetRespone(String result);
        void lestener_Updateversion(String url,String title,String desc);
        void lestener_baner(String image,String url,String type);
        void lestener_link_1(String image,String url,String type);
        void lestener_link_2(String link2Array);
        void lestener_link_3_desc(String title,String desc,String image,String url);
        void lestener_link_4(String link4Array);
        void lestener_title_link(String title,String image,String url,String type);
        void lestener_title_none(String title);
        void lestener_salavat(int count);
        void lestener_hadith();
        void lestener_slider(String respone);
        void lestener_news(String newsArray);
        void lestener_hr();
        void lestener_language();
        void lestener_versionApp();
        void error();
    }


    /*List News*/

    public static void listNews(String url,String limit ,final listNewsListener listNewsListener){
        StringRequest mainRQ = new StringRequest(Request.Method.GET,url+"?limit="+limit, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject mainObject = new JSONObject(response);
                    JSONArray result = mainObject.getJSONArray("result");
                    listNewsListener.lestener_news(String.valueOf(result));


                } catch (JSONException e) {
                    e.printStackTrace();
                    listNewsListener.error();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                listNewsListener.error();
            }
        });mainRQ.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MainApplication.getInstance().addToRequestQueue(mainRQ);
    }

    public interface listNewsListener{
        void lestener_news(String newsArray);
        void error();
    }

    /*Salavat*/
    public static void salawat(String url,final String apikey, final salawatListener salawatListener){
        StringRequest salawayRQ = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject mainObject = new JSONObject(response);

                    boolean ok = mainObject.getBoolean("ok");
                    if (ok){
                        JSONArray msg = mainObject.getJSONArray("msg");
                        JSONObject result = mainObject.getJSONObject("result");
                        int count_salawat = 0;
                        if (!result.isNull("count")){
                            count_salawat = result.getInt("count");
                        }
                        salawatListener.saveSalawat(count_salawat, String.valueOf(msg));
                    }else {
                        JSONArray msg = mainObject.getJSONArray("msg");
                        salawatListener.errorSalawat(String.valueOf(msg));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    salawatListener.errorSalawat("JSONException: "+e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                salawatListener.errorSalawat("onErrorResponse: "+e);
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("appkey", lookServer.appkey);
                if (apikey !=null){
                    headers.put("apikeey",apikey);
                }
                return headers;
            }
        };salawayRQ.setRetryPolicy(new DefaultRetryPolicy(2 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MainApplication.getInstance().addToRequestQueue(salawayRQ);

    }

    public interface salawatListener{
        void saveSalawat(int count,String msgArray);
        void errorSalawat(String error);
    }

    /*News*/
    public static void news(String url,String id,final newsLinstener newsLinstener ){
        StringRequest newsRQ = new StringRequest(Request.Method.GET, url+id, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject mainObject = new JSONObject(response);
                    boolean ok = mainObject.getBoolean("ok");
                    if (ok){
                        JSONObject result = mainObject.getJSONObject("result");
                        newsLinstener.resultValueNes(String.valueOf(result));
                        JSONObject meta = result.getJSONObject("meta");
                        if (!meta.isNull("gallery")){
                            JSONArray gallery = meta.getJSONArray("gallery");
                            newsLinstener.resultGaleryNws(String.valueOf(gallery));
                        }
                    }else {
                        newsLinstener.failedValueNes("ok: "+ok);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    newsLinstener.failedValueNes("JSONException: "+e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                newsLinstener.failedValueNes("VolleyError: "+e);
            }
        });
        newsRQ.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MainApplication.getInstance().addToRequestQueue(newsRQ);
    }

    public interface newsLinstener{
        void resultValueNes(String respone);
        void resultGaleryNws(String responeArray);
        void failedValueNes(String error);
    }


    public static void del(String url,int page,final String apikey, final delListener delListener){
        StringRequest getDelRQ = new StringRequest(Request.Method.GET, url+"?page="+page, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject mainObject = new JSONObject(response);
                    boolean ok = mainObject.getBoolean("ok");
                    if (ok){
                        if (!mainObject.isNull("pagination")){
                            JSONObject pagination = mainObject.getJSONObject("pagination");
                            if (!pagination.isNull("total_page")){
                                int total_page = pagination.getInt("total_page");
                                delListener.total_page(total_page);
                            }
                        }else {delListener.total_page(3);}

                        JSONArray result = mainObject.getJSONArray("result");
                        delListener.result(String.valueOf(result));
                    }else {
                        delListener.error("ok: "+ok);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    delListener.error("JSONException: "+e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                delListener.error("VolleyError: "+e);
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("appkey", lookServer.appkey);
                if (apikey !=null){
                    headers.put("apikeey",apikey);
                }
                return headers;
            }
        };getDelRQ.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MainApplication.getInstance().addToRequestQueue(getDelRQ);
    }

    public interface delListener{
        void result(String respone);
        void total_page(int endPage);
        void error(String error);
    }


    public static void like_del(String url,final String apikey, final String id, final likeListener likeListener){
        StringRequest likeDelRQ = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject mainObject = new JSONObject(response);

                    boolean ok = mainObject.getBoolean("ok");
                    if (ok){
                        JSONArray msg = mainObject.getJSONArray("msg");
                        likeListener.liked(String.valueOf(msg));
                    }else {
                        JSONArray msg = mainObject.getJSONArray("msg");
                        likeListener.filed(String.valueOf(msg));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    likeListener.filed("JSONException: "+e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                likeListener.filed("onErrorResponse: "+e);
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("appkey", lookServer.appkey);
                if (apikey !=null){
                    headers.put("apikeey",apikey);
                }
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> body = new HashMap<>();
                body.put("like", id);
                return body;
            }
        };likeDelRQ.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MainApplication.getInstance().addToRequestQueue(likeDelRQ);
    }

    public interface likeListener{
        void liked(String respone);
        void filed(String error);
    }


    public static void sendDel(String url, final String apikey, final String name, final String text, final String mobile, final String switchGender, final sendelListener sendelListener){
        StringRequest sendDelRQ = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject mainObject = new JSONObject(response);

                    boolean ok = mainObject.getBoolean("ok");
                    if (ok){
                        JSONArray msg = mainObject.getJSONArray("msg");
                        sendelListener.result(String.valueOf(msg));
                    }else {
                        JSONArray msg = mainObject.getJSONArray("msg");
                        sendelListener.error(String.valueOf(msg));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    sendelListener.error("JSONException: "+e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                sendelListener.error("onErrorResponse: "+e);
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("appkey", lookServer.appkey);
                if (apikey !=null){
                    headers.put("apikeey",apikey);
                }
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> body = new HashMap<>();
                body.put("desc",text);
                if (mobile !=null){
                    body.put("mobile",mobile);
                }
                if (switchGender !=null){
                    body.put("switchGender",switchGender);
                }
                if (switchGender !=null){
                    body.put("name",name);
                }
                return body;
            }
        };
        sendDelRQ.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MainApplication.getInstance().addToRequestQueue(sendDelRQ);
    }

    public interface sendelListener{
        void result(String respone);
        void error(String error);
    }

    public static void api(String url,final apiListener apiListener){

        StringRequest apiRQ = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                apiListener.result(String.valueOf(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                apiListener.error("VolleyError: "+e);
            }
        });
        apiRQ.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MainApplication.getInstance().addToRequestQueue(apiRQ);

    }

    public interface apiListener{
        void result(String respone);
        void error(String error);
    }

}
