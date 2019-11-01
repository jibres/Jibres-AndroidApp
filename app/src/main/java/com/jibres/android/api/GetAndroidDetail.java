package com.jibres.android.api;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jibres.android.Static.url;
import com.jibres.android.utility.Network;

public class GetAndroidDetail {


    public static void GetJson(final JsonLocalListener jsonLocalListener){
        StringRequest get_local = new StringRequest(Request.Method.GET, url.app, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                jsonLocalListener.onGetJson_Online(response);

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                jsonLocalListener.onGetJson_error("VolleyError: "+error);
            }
        });
        get_local.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Network.getInstance().addToRequestQueue(get_local);

    }

    public interface JsonLocalListener {

        void onGetJson_Online(String ResponeOnline);

        void onGetJson_error(String error);
    }
}
