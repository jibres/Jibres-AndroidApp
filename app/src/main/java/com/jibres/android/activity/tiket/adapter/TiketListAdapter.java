package com.jibres.android.activity.tiket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jibres.android.R;
import com.jibres.android.activity.tiket.model.TiketListModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TiketListAdapter extends RecyclerView.Adapter<TiketListAdapter.MyViewHolder> {

  private List<TiketListModel> itemList;
  private Context mContext;
  private ItemClickListener mlistener;


  public TiketListAdapter(List<TiketListModel> itemList, Context mContext, ItemClickListener listener) {
    this.itemList = itemList;
    this.mContext = mContext;
    this.mlistener = listener;
  }

  @NotNull
  @Override
  public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
    View aView = LayoutInflater.from(mContext).inflate(R.layout.item_tiket_list, parent, false);
    return new MyViewHolder(aView);
  }

  @Override
  public void onBindViewHolder(final MyViewHolder holder, final int position) {

    final TiketListModel item = itemList.get(position);

    if (item.getAvatar()!=null) {
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
    holder.desc.setText(item.getContent());
    if (item.isSolved()){
      holder.status.setText(textStatus("solved"));
    }else {
      holder.status.setText(textStatus(item.getStatus()));
    }

    holder.view.setOnClickListener(view -> mlistener.onClick(item.getId(),false));
    holder.view.setOnLongClickListener(view -> {
      mlistener.onClick(item.getId(),true);
      return false;
    });

  }

  @Override
  public int getItemCount() {
    return itemList.size();
  }

  class MyViewHolder extends RecyclerView.ViewHolder {
    View view;
    ImageView avatar;
    TextView title,desc,displyname,status;

    MyViewHolder(View itemView) {
      super(itemView);
      view = itemView;
      avatar = itemView.findViewById(R.id.image);
      title = itemView.findViewById(R.id.title);
      desc = itemView.findViewById(R.id.desc);
//      displyname = itemView.findViewById(R.id.displyname);
      status = itemView.findViewById(R.id.status);

    }
  }

  public interface ItemClickListener{
    void onClick(String id,boolean isOnclickLong);
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

}
