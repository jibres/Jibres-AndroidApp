package com.jibres.android.activity.notif;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jibres.android.R;
import com.jibres.android.weight.DividerItemDecoratorWeighet;

import java.util.ArrayList;
import java.util.List;

public class NotifViewActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    NotifAdapter adapter;
    List<NotifModel> item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif_view);

        recyclerView = findViewById(R.id.recycler_view);
        item = new ArrayList<>();
        adapter = new NotifAdapter(item, this, this::onCliked);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        LinearLayoutManager sLayoutManager =
                new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);

        RecyclerView.ItemDecoration dividerItemDecoration =
                new DividerItemDecoratorWeighet(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.divider));

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        GetLanguage();
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(sLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.notifyDataSetChanged();
    }

    private void GetLanguage() {
        item.add(new NotifModel(false,"اطلاع رسانی ۱","عنوان تستی","متن توضیحی زیاد این متن تکرار میشود \n و هی تکرار میشود","۲۵ دقیقه"));
        item.add(new NotifModel(false,"اطلاع رسانی ۲","عنوان تستی","متن توضیحی زیاد این متن تکرار میشود \n و هی تکرار میشود","۲ روز قبل"));
        item.add(new NotifModel(true,"اطلاع رسانی ۳","عنوان تستی","متن توضیحی زیاد این متن تکرار میشود \n و هی تکرار میشود","۲۷ روز قبل"));
        item.add(new NotifModel(true,"اطلاع رسانی ۴","عنوان تستی","متن توضیحی زیاد این متن تکرار میشود \n و هی تکرار میشود","۲ روز قبل"));
        item.add(new NotifModel(true,"اطلاع رسانی ۵","عنوان تستی","متن توضیحی زیاد این متن تکرار میشود \n و هی تکرار میشود","۲۲ روز قبل"));
    }

    private void onCliked(int i) {

    }
}
