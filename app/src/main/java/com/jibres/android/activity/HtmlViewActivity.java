package com.jibres.android.activity;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.jibres.android.JibresApplication;
import com.jibres.android.R;

public class HtmlViewActivity extends AppCompatActivity {

    private String url = null;
    private String title = null;
    private TextView textView ;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html_view);
        textView = findViewById(R.id.text);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);


        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");

        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        mTitle.setText(title);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        swipeRefreshLayout.setRefreshing(true);
        getData();
        swipeRefreshLayout.setOnRefreshListener(this::getData);

    }


    private void getData(){
        if (url!=null){
            StringRequest request = new StringRequest(Request.Method.GET, url, response
                    -> {
                Spanned html_content = Html.fromHtml(response);
                textView.setText(html_content);
                swipeRefreshLayout.setRefreshing(false);
            }, Throwable::printStackTrace);

            request.setRetryPolicy(
                    new DefaultRetryPolicy(
                            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            JibresApplication.getInstance().addToRequestQueue(request);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
