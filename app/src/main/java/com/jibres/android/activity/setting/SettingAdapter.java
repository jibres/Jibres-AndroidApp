package com.jibres.android.activity.setting;

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

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.MyViewHolder> {

  private List<SettingModel> itemList;
  private Context mContext;
  private ItemClickListener mlistener;


  public SettingAdapter(List<SettingModel> itemList, Context mContext, ItemClickListener listener) {
    this.itemList = itemList;
    this.mContext = mContext;
    this.mlistener = listener;
  }

  @NotNull
  @Override
  public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {

    View aView = LayoutInflater.from(mContext).inflate(R.layout.item_setting, parent, false);
    return new MyViewHolder(aView);

  }

  @Override
  public void onBindViewHolder(final MyViewHolder holder, final int position) {

    final SettingModel aItem = itemList.get(position);

    if (aItem.getImageDrawble()!=0){
      holder.imageView.setVisibility(View.VISIBLE);
      holder.imageView.setImageResource(aItem.getImageDrawble());
    }else if (aItem.getImage()!=null){
      holder.imageView.setVisibility(View.VISIBLE);
      Glide.with(mContext)
              .load(aItem.getImage())
              .into(holder.imageView);
    }else {
      holder.imageView.setVisibility(View.GONE);
    }

    if (aItem.getTitle()!=null){
      holder.title.setVisibility(View.VISIBLE);
      holder.title.setText(aItem.getTitle());
    }else {
      holder.title.setVisibility(View.GONE);
    }
    if (aItem.getSummery()!=null){
      holder.summery.setVisibility(View.VISIBLE);
      holder.summery.setText(aItem.getSummery());
    }else {
      holder.summery.setVisibility(View.GONE);
    }
    if (aItem.getDesc()!=null){
      holder.desc.setVisibility(View.VISIBLE);
      holder.desc.setText(aItem.getDesc());
    }else {
      holder.desc.setVisibility(View.GONE);
    }
    if (aItem.isShowFlesh()){
      holder.felash.setVisibility(View.VISIBLE);
    }else {
      holder.felash.setVisibility(View.GONE);
    }

    if (aItem.getOnClick() !=0){
      holder.view.setOnClickListener(view -> mlistener.onClick(aItem.getOnClick()));
    }

    if (!aItem.isShowFlesh() && aItem.getDesc()==null && aItem.getSummery()==null){
      holder.background.setBackgroundColor(Color.parseColor("#7CDDDDDD"));
    }else {
      holder.background.setBackgroundResource(R.drawable.row_background);
    }

  }

  @Override
  public int getItemCount() {
    return itemList.size();
  }

  class MyViewHolder extends RecyclerView.ViewHolder {
    View view,background;
    ImageView imageView,felash;
    LinearLayout boxTextView;
    TextView title,summery,desc;

    MyViewHolder(View itemView) {
      super(itemView);
      view = itemView;
      background = itemView.findViewById(R.id.background);
      imageView = itemView.findViewById(R.id.image);
      boxTextView = itemView.findViewById(R.id.box_text);
      title = itemView.findViewById(R.id.title);
      summery = itemView.findViewById(R.id.summery);
      desc = itemView.findViewById(R.id.desc);
      felash = itemView.findViewById(R.id.felah);
    }
  }

  public interface ItemClickListener{
    void onClick(int onClick);
  }

}
