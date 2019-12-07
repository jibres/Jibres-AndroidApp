package com.jibres.android.Adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jibres.android.Item.item_del;
import com.jibres.android.R;
import com.jibres.android.api.apiV6;
import com.jibres.android.utility.Database;
import com.jibres.android.utility.SaveManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.jibres.android.R.drawable.woman;

public class Adaptor_del extends RecyclerView.Adapter<Adaptor_del.ViewHolder> {

    private List<item_del> mData;
    private LayoutInflater mInflater;
    private Context context;
    private boolean duble = false;
    ImageView imageView;

    // data is passed into the constructor
    public Adaptor_del(Context context, List<item_del> data) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_delneveshte, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        imageView = holder.imageHert_dubelClick;
        String plus = mData.get(position).getPlus();
        String text = mData.get(position).getText();
        String sex = mData.get(position).getSex();
        String name = mData.get(position).getName();
//        boolean last_liked = mData.get(position).isLast_liked();
        final String id = mData.get(position).getId();
        holder.bg_img_plus.setColorFilter(Color.parseColor("#757474"));

        if (name != null){
            holder.name.setVisibility(View.VISIBLE);
            holder.name.setText(name);
        }

        holder.text.setText(text);
        holder.plus.setText(plus);


        if (sex.equals(context.getString(R.string.sex_female))){
            holder.avatar.setImageResource(woman);
        }else {
            holder.avatar.setImageResource(R.drawable.man);
        }

        SQLiteDatabase databases = new Database(context).getReadableDatabase();
        Cursor checkID_del = databases.rawQuery(Database.select_del(id),null);


        if (checkID_del.getCount() == 1){
            holder.bg_img_plus.setColorFilter(Color.RED);
        }
        databases.close();
        checkID_del.close();




        final View.OnClickListener clickLike = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Like(holder.plus,holder.bg_img_plus,id,holder.imageHert_dubelClick);
            }
        };

        holder.bg_img_plus.setOnClickListener(clickLike);
        holder.plus.setOnClickListener(clickLike);

        holder.dubelClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (duble){
                    Like(holder.plus,holder.bg_img_plus,id,holder.imageHert_dubelClick);
                }
                duble = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        duble = false;
                    }
                }, 200);
            }
        });

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    private void Like(TextView plus, ImageView bg_img_plus, final String id, final ImageView imgHertDubelClick){
        final String url = SaveManager.get(context).getstring_appINFO().get(SaveManager.apiV6_URL)+ com.jibres.android.Static.url.like_del;

        SQLiteDatabase databases = new Database(context).getReadableDatabase();
        @SuppressLint("Recycle") Cursor checkID_del = databases.rawQuery(Database.select_del(id),null);
        if (checkID_del.getCount() == 0 ){
            final String getPlusApp = plus.getText().toString();
            int plusApp = Integer.valueOf(getPlusApp);
            ++plusApp;
            plus.setText(String.valueOf(plusApp));
            bg_img_plus.setColorFilter(Color.RED);
            /**/
            imageHertDubelClick_anim(imgHertDubelClick);

            String apikey = SaveManager.get(context).getstring_appINFO().get(SaveManager.apiKey);
            apiV6.like_del(url,apikey, id, new apiV6.likeListener() {
                @Override
                public void liked(String respone) {
                    try {
                        JSONArray array = new JSONArray(respone);
                        SQLiteDatabase database = new Database(context).getWritableDatabase();
                        database.execSQL(Database.insetTo_del(id,"true"));
                        database.close();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            String text = object.getString("text");
                            Log.d("like", "liked: "+text);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void filed(String error) {

                }
            });
        }
        databases.close();
        checkID_del.close();
    }

    private void imageHertDubelClick_anim(final ImageView imgHertDubelClick){
        imgHertDubelClick.animate().setDuration(100).alpha(0.7f);
        imgHertDubelClick.animate().setDuration(300).translationY(0);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                imgHertDubelClick.animate().setDuration(300).alpha(0.0f);
                imgHertDubelClick.animate().setDuration(300).translationY(150);

            }
        }, 700);
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,text,plus;
        ImageView avatar,bg_img_plus,imageHert_dubelClick;
        RelativeLayout dubelClick;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_del);
            text = itemView.findViewById(R.id.text_del);
            plus = itemView.findViewById(R.id.plus_del);
            bg_img_plus = itemView.findViewById(R.id.img_like);
            avatar = itemView.findViewById(R.id.imgSex_del);
            dubelClick = itemView.findViewById(R.id.outSide_itemDelneveshte);
            imageHert_dubelClick = itemView.findViewById(R.id.imageHeart_dubelClick);
        }

    }


}