package com.jibres.android.activity.tiket.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jibres.android.R;
import com.jibres.android.activity.tiket.adapter.TiketListAdapter;
import com.jibres.android.activity.tiket.api.TiketApi;
import com.jibres.android.activity.tiket.api.TiketListener;
import com.jibres.android.activity.tiket.model.TiketListModel;
import com.jibres.android.managers.UrlManager;
import com.jibres.android.weight.DividerItemDecoratorWeighet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TiketListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TiketListAdapter adapter;
    List<TiketListModel> item;

    RecyclerView.ItemDecoration dividerItemDecoration;
    LinearLayoutManager sLayoutManager;

    boolean itemFirstAdded = false;
    int page = 1;
    int endPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiket_list);

        Log.d("amingili", "onCreate: local_api= "+ UrlManager.get.local_api(this));

        recyclerView = findViewById(R.id.recycler_view);
        item = new ArrayList<>();
        adapter = new TiketListAdapter(item, this, this::onCliked);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        sLayoutManager =
                new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);

        dividerItemDecoration =
                new DividerItemDecoratorWeighet(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.divider));

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        getItem(page);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager=LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
                int totalItemCount = layoutManager.getItemCount();
                int lastVisible = layoutManager.findLastVisibleItemPosition();

                boolean endHasBeenReached = lastVisible + 5 >= totalItemCount;
                if (totalItemCount > 0 && endHasBeenReached) {
                    if (!itemFirstAdded){
                        if (page < endPage){
//                            progressBar.setVisibility(View.VISIBLE);
                            page++;
                            getItem(page);
                        }
                        itemFirstAdded = true;
                    }
                }
            }
        });
    }

    void getItem(int PAGE) {
        TiketApi.list(getApplicationContext(),String.valueOf(PAGE),new TiketListener.listTiket() {
            @Override
            public void onReceived(String value, int total_page) {
                endPage = total_page;
                Log.d("amingoli", "onReceived: "+value);
                try {
                    JSONArray results = new JSONArray(value);
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject object = results.getJSONObject(i);
                        boolean solved = false;
                        String title = null ,
                                avatar = null,
                                displayname = null;
                        if (!object.isNull("solved")){
                            solved = true;
                        }
                        if (!object.isNull("title")){
                            title = object.getString("title");
                        }
                        if (!object.isNull("avatar")){
                            avatar = object.getString("avatar");
                        }
                        if (!object.isNull("displayname")){
                            displayname = object.getString("displayname");
                        }
                        item.add(new TiketListModel(
                                object.getString("id"),
                                title,
                                object.getString("content"),
                                object.getString("status"),
                                avatar,
                                displayname,
                                solved));
                        recyclerView.addItemDecoration(dividerItemDecoration);
                        recyclerView.setLayoutManager(sLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFiled(boolean hasNet) {
                Log.d("amingoli", "onFiled: "+hasNet);
            }
        });
    }

    private void onCliked(String id,boolean isLongClick) {
        if (isLongClick){
            dialogStatus(id);
        }else {
            Intent intent = new Intent(this,TiketViewActivity.class);
            intent.putExtra("id",id);
            startActivity(intent);
        }
    }

    public void add_tiket(View view) {
        Intent intent = new Intent(this,TiketAddActivity.class);
        intent.putExtra("title","تیکت");
        startActivity(intent);
    }

    void dialogStatus(String id){
        String[] amin = {"solved","close","deleted","awaiting","spam"};
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        builderSingle.setTitle("وضعیت تیکت را انتخاب کنید");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice);
        for (int i = 0; i < amin.length; i++) {
            arrayAdapter.add(textStatus(amin[i]));
        }

        builderSingle.setNegativeButton("خروج", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setStatusTiket(id,amin[which]);
                dialog.dismiss();
            }
        });
        builderSingle.show();
    }

    private String textStatus(String status){
        switch (status){
            case "solved":
                return "حل شده";
            case "close":
                return "بسته شده";
            case "deleted":
                return "حذف شده";
            case "awaiting":
                return "در انتظار";
            case "spam":
                return "اسپم";
            case "answered":
                return "پاسخ داده شده";
        }
        return "";
    }

    private void setStatusTiket(String idTiket,String status){
        if (!status.equals("solved")){
            TiketApi.setStatus(getApplicationContext(), idTiket, status,
                    new TiketListener.setStatus() {
                        @Override
                        public void onReceived(String massage, boolean statusISset) {
                            Toast.makeText(TiketListActivity.this, massage, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFiled(boolean hasNet) {

                        }
                    });
        }else {
            TiketApi.setSolved(getApplicationContext(), idTiket, true,
                    new TiketListener.setStatus() {
                        @Override
                        public void onReceived(String massage, boolean statusISset) {
                            Toast.makeText(TiketListActivity.this, massage, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFiled(boolean hasNet) {

                        }
                    });
        }

    }
}
