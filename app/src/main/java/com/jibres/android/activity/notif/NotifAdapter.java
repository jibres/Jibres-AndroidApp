package com.jibres.android.activity.notif;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jibres.android.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NotifAdapter extends RecyclerView.Adapter<NotifAdapter.MyViewHolder> {

  private List<NotifModel> itemList;
  private Context mContext;
  private ItemClickListener mlistener;


  public NotifAdapter(List<NotifModel> itemList, Context mContext, ItemClickListener listener) {
    this.itemList = itemList;
    this.mContext = mContext;
    this.mlistener = listener;
  }

  @NotNull
  @Override
  public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {

    View aView = LayoutInflater.from(mContext).inflate(R.layout.item_notif, parent, false);
    return new MyViewHolder(aView);

  }

  @Override
  public void onBindViewHolder(final MyViewHolder holder, final int position) {

    final NotifModel item = itemList.get(position);

    holder.name.setText(item.getName());
    holder.title.setText(item.getTitle());
    holder.desc.setText(item.getDesc());
    holder.date.setText(item.getDate());

    if (item.isReadUser()){
      holder.status.setBackgroundColor(Color.parseColor("#FFD100"));
    }else {
      holder.status.setBackgroundColor(Color.parseColor("#7A7A7A"));
    }

  }

  @Override
  public int getItemCount() {
    return itemList.size();
  }

  class MyViewHolder extends RecyclerView.ViewHolder {
    View view;
    ImageView status;
    TextView name,title,desc,date;

    MyViewHolder(View itemView) {
      super(itemView);
      view = itemView;
      status = itemView.findViewById(R.id.status);
      name = itemView.findViewById(R.id.name);
      title = itemView.findViewById(R.id.title);
      desc = itemView.findViewById(R.id.desc);
      date = itemView.findViewById(R.id.date);
    }
  }

  public interface ItemClickListener{
    void onClick(int onClick);
  }

}
