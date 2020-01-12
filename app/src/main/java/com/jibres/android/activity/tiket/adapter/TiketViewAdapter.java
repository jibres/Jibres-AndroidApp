package com.jibres.android.activity.tiket.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jibres.android.R;
import com.jibres.android.activity.tiket.model.TiketViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TiketViewAdapter extends RecyclerView.Adapter<TiketViewAdapter.MyViewHolder> {

  private List<TiketViewModel> itemList;
  private Context mContext;
  private ItemClickListener mlistener;


  public TiketViewAdapter(List<TiketViewModel> itemList, Context mContext, ItemClickListener listener) {
    this.itemList = itemList;
    this.mContext = mContext;
    this.mlistener = listener;
  }

  @NotNull
  @Override
  public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {

    View aView = LayoutInflater.from(mContext).inflate(R.layout.item_massage_tiket, parent, false);
    return new MyViewHolder(aView);

  }

  @Override
  public void onBindViewHolder(final MyViewHolder holder, final int position) {

    final TiketViewModel item = itemList.get(position);
    if (item.getAvatar()!=null){
      Glide.with(mContext).load(item.getAvatar()).into(holder.avatar);
    }else {
      holder.avatar.setImageResource(R.drawable.logo_xml);
    }
    if (item.getTitle()!=null){
      holder.title.setVisibility(View.VISIBLE);
      holder.title.setText(item.getTitle());
    }else {
      holder.title.setVisibility(View.GONE);
    }
    holder.massage.setText(Html.fromHtml(item.getMassage()));
    if (item.getTime()!=null){
      holder.time.setVisibility(View.VISIBLE);
      holder.time.setText(item.getTime());
    }else {
      holder.time.setVisibility(View.GONE);
    }
  }

  @Override
  public int getItemCount() {
    return itemList.size();
  }

  class MyViewHolder extends RecyclerView.ViewHolder {
    View view;
    ImageView avatar;
    TextView title,massage,time;

    MyViewHolder(View itemView) {
      super(itemView);
      view = itemView;
      avatar = itemView.findViewById(R.id.avatar);
      title = itemView.findViewById(R.id.title);
      massage = itemView.findViewById(R.id.massage);
      time = itemView.findViewById(R.id.time);
    }
  }

  public interface ItemClickListener{
    void onClick(int onClick);
  }

}
