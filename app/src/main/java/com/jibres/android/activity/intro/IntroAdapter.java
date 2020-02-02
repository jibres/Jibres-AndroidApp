package com.jibres.android.activity.intro;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jibres.android.R;
import com.jibres.android.utility.ColorUtil;

import java.util.List;

public class IntroAdapter extends RecyclerView.Adapter<IntroAdapter.ViewHolder>{

  private List<IntroModel> itemIntroList;
  private LayoutInflater mInflater;
  private Context context;

  // data is passed into the constructor
  public IntroAdapter(Context context, List<IntroModel> itemIntroList) {
    this.context = context;
    this.mInflater = LayoutInflater.from(context);
    this.itemIntroList = itemIntroList;
  }

  // inflates the row layout from xml when needed
  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = mInflater.inflate(R.layout.item_intro, parent, false);
    return new ViewHolder(view);
  }

  // binds the data to the TextView in each row
  @Override
  public void onBindViewHolder(ViewHolder holder, final int position) {
    IntroModel item = itemIntroList.get(position);

    int style = item.getStyle();

    if (style == 2 && urlIsTrue(item.getImage())){
      holder.view_1.setVisibility(View.GONE);
      holder.view_2.setVisibility(View.VISIBLE);
      Glide.with(context).load(item.getImage()).into(holder.v2_image);
      holder.v2_title.setText(item.getTitle());
      holder.v2_desc.setText(item.getDesc());
    }else {
      holder.view_1.setVisibility(View.VISIBLE);
      holder.view_2.setVisibility(View.GONE);
      holder.v1_title.setText(item.getTitle());
      holder.v1_desc.setText(item.getDesc());
    }
  }

  // total number of rows
  @Override
  public int getItemCount() {
    return itemIntroList.size();
  }


  // stores and recycles views as they are scrolled off screen
  public class ViewHolder extends RecyclerView.ViewHolder{
    View view_1,view_2;
    ImageView v2_image;
    TextView v1_title,v1_desc,v2_title,v2_desc;

    ViewHolder(View itemView) {
      super(itemView);
//      item_intro_1
      view_1 = itemView.findViewById(R.id.style_1);
      v1_title = view_1.findViewById(R.id.title);
      v1_desc = view_1.findViewById(R.id.desc);
//      item_intro_2
      view_2 = itemView.findViewById(R.id.style_2);
      v2_image = view_2.findViewById(R.id.intro_img);
      v2_title = view_2.findViewById(R.id.title);
      v2_desc = view_2.findViewById(R.id.desc);
    }

  }


  boolean urlIsTrue(String url){
    if (url!=null &&
        url.length()>3 &&
        url.startsWith("http")){
      return true;
    }
    return false;
  }
}
