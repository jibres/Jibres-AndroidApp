package com.jibres.android.activity.intro;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.jibres.android.R;
import com.jibres.android.activity.WebViewActivity;
import com.jibres.android.managers.AppManager;
import com.jibres.android.managers.JsonManager;
import com.jibres.android.managers.UrlManager;
import com.jibres.android.utility.ColorUtil;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator2;

public class IntroActivity extends AppCompatActivity {
    private static String TAG = "amingoli-intro";
    int style = 1;
    int padding = 0;

    List<IntroModel> itemIntroList;
    RecyclerViewPager recyclerView;
    IntroAdapter adaptorIntro;
    LinearLayoutManager layout;

    View bg_dots;
    TextView next, skip, start;
    ImageView img_next;
    CircleIndicator2 indicator;

    String bg_from = "#ffffff";
    String bg_to = "#ffffff";
    String color_primary = "#eeeeee";
    String color_secondary = "#eeeeee";

    String lang_next = "Next";
    String lang_back = "Back";
    String lang_skip = "Skip";
    String lang_start = "GetStarted";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onWindowFocusChanged(true);
        setContentView(R.layout.activity_intro);
        AppManager.get(getApplication()).save_splash(2);

        bg_dots = findViewById(R.id.bg_dots);
        indicator = findViewById(R.id.indicator);
        next = findViewById(R.id.next);
        skip = findViewById(R.id.skip);
        start = findViewById(R.id.start);
        img_next = findViewById(R.id.image_next);

        itemIntroList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerViewPager_intro);
        adaptorIntro = new IntroAdapter(this, itemIntroList);
        recyclerView.setAdapter(adaptorIntro);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(recyclerView);
        layout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layout);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//      Dots
        indicator.attachToRecyclerView(recyclerView, pagerSnapHelper);
//      optional
        adaptorIntro.registerAdapterDataObserver(indicator.getAdapterDataObserver());

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (getPage() == itemIntroList.size() - 1) {
                    next.setText(lang_start);
                    start.setText(lang_start);
                    img_next.setTag("start");
                    img_next.animate().alpha(0).setDuration(150);
                    indicator.animate().alpha(0).setDuration(150);
                    start.animate().alpha(1).setDuration(100);
                    start.setEnabled(true);
                } else {
                    next.setText(lang_next);
                    img_next.setTag("next");
                    img_next.animate().alpha(1).setDuration(150);
                    indicator.animate().alpha(1).setDuration(150);
                    start.animate().alpha(0).setDuration(100);
                    start.setEnabled(false);
                }

                if (getPage() == 0) {
                    skip.setText(lang_skip);
                    skip.setTag("skip");
                } else {
                    skip.setText(lang_back);
                    skip.setTag("back");
                }
            }
        });

        getJson();
    }

    int getPage() {
        return recyclerView.getCurrentPosition();
    }

    public void btn_intro(View view) {
        int page = getPage();
        String tag = view.getTag().toString();
        switch (tag) {
            case "next":
                recyclerView.smoothScrollToPosition(page + 1);
                break;
            case "back":
                recyclerView.smoothScrollToPosition(page - 1);
                break;
            default:
                finish();
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("url", UrlManager.dashboard(getApplication()));
                startActivity(intent);
                break;
        }
    }


    private void getJson() {
        Log.d(TAG, "getJson: " + JsonManager.getJsonIntro(this));
        try {
            JSONObject result = new JSONObject(JsonManager.getJsonIntro(this));

            if (!result.isNull("theme")) {
                switch (result.getString("theme")) {
                    case "Jibres":
                        style = 1;
                        break;
                    default:
                        style = 2;
                        break;
                }
            }
            if (style == 2) {
                padding = (int) getResources().getDimension(R.dimen._25sdp);
                recyclerView.setPadding(padding, 0, padding, 0);
            } else {
                recyclerView.setPadding(0, 0, 0, 0);
            }

            if (!result.isNull("translation")) {
                JSONObject translation = result.getJSONObject("translation");
                if (!translation.isNull("img_next"))
                    lang_next = translation.getString("img_next");
                if (!translation.isNull("prev"))
                    lang_back = translation.getString("prev");
                if (!translation.isNull("skip"))
                    lang_skip = translation.getString("skip");
                if (!translation.isNull("start"))
                    lang_start = translation.getString("start");
            }

            if (!result.isNull("bg")) {
                JSONObject bg = result.getJSONObject("bg");
                if (!bg.isNull("from"))
                    bg_from = bg.getString("from");
                if (!bg.isNull("to"))
                    bg_to = bg.getString("to");
            }

            ColorUtil.setGradient(recyclerView, bg_from, bg_to);

            if (!result.isNull("color")) {
                JSONObject color = result.getJSONObject("color");
                if (!color.isNull("primary"))
                    color_primary = color.getString("primary");
                if (!color.isNull("secondary"))
                    color_secondary = color.getString("secondary");
                if (!color.isNull("dot"))
                    DotsIndicatorRecyclerView.colorInactive = color.getString("dot");
                if (!color.isNull("doSelected"))
                    DotsIndicatorRecyclerView.colorActive = color.getString("doSelected");
            }
            next.setTextColor(Color.parseColor(color_secondary));
            start.setTextColor(Color.parseColor(color_secondary));
            skip.setTextColor(Color.parseColor(color_secondary));

            if (!result.isNull("page")) {
                JSONArray page = result.getJSONArray("page");
                for (int i = 0; i < page.length(); i++) {
                    try {
                        JSONObject object = page.getJSONObject(i);

                        String image = null;
                        String title = null;
                        String subTitle = null;
                        String desc = null;

                        if (!object.isNull("image"))
                            image = object.getString("image");
                        if (!object.isNull("title")) {
                            title = object.getString("title");
                        } else if (!object.isNull("subtitle")) {
                            subTitle = object.getString("subtitle");
                        }
                        if (!object.isNull("desc"))
                            desc = object.getString("desc");
                        if (style == 1) {
                            if (title != null) {
                                itemIntroList.add(new IntroModel(title, null, desc, bg_from, bg_to, color_primary, color_secondary));
                            } else if (subTitle != null) {
                                itemIntroList.add(new IntroModel(null, subTitle, desc, bg_from, bg_to, color_primary, color_secondary));
                            }
                        } else {
                            if (image != null) {
                                int padding = (int) getResources().getDimension(R.dimen._30sdp);
                                recyclerView.setPadding(padding, 0, padding, 0);
                                itemIntroList.add(new IntroModel(image, title, null, desc, bg_from, bg_to, color_primary, color_secondary));
                            }
                        }
                        adaptorIntro.notifyDataSetChanged();
                        recyclerView.getAdapter();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (JSONException e) {
            Log.e(TAG, "getJson > result: ", e);
            e.printStackTrace();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
    }
}