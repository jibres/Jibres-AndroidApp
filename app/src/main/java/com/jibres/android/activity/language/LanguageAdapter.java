package com.jibres.android.activity.language;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jibres.android.R;
import com.jibres.android.managers.AppManager;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.MyViewHolder> {

  private List<LanguageModel> itemList;
  private Context mContext;
  private ItemClickListener mlistener;


  LanguageAdapter(List<LanguageModel> itemList, Context mContext, ItemClickListener listener) {
    this.itemList = itemList;
    this.mContext = mContext;
    this.mlistener = listener;
  }

  @NotNull
  @Override
  public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {

    View aView = LayoutInflater.from(mContext).inflate(R.layout.item_language, parent, false);
    return new MyViewHolder(aView);

  }

  @Override
  public void onBindViewHolder(final MyViewHolder holder, final int position) {

    final LanguageModel aItem = itemList.get(position);

    holder.icChoosed.setVisibility(View.GONE);
    holder.titleCountry.setText(aItem.getTitle());
    holder.titleCountry.setTag(aItem.getTag());

    if (aItem.isChBoxVisibel()){
      holder.icChoosed.setVisibility(View.VISIBLE);
    }
    Glide.with(mContext)
            .load(mContext.getString(R.string.link_flag_contry,aItem.getTag()))
            .into(holder.imgCountry);

    holder.view.setOnClickListener(view -> {
      AppManager.get(mContext).setAppLanguage(aItem.getTag());
      mlistener.onClick();
    });

  }

  @Override
  public int getItemCount() {
    return itemList.size();
  }

  class MyViewHolder extends RecyclerView.ViewHolder {
    View view;
    ImageView imgCountry, icChoosed;
    TextView titleCountry;
    MyViewHolder(View itemView) {
      super(itemView);
      view = itemView;
      imgCountry = itemView.findViewById(R.id.img_country);
      icChoosed = itemView.findViewById(R.id.icChoosed);
      titleCountry = itemView.findViewById(R.id.title_country);
    }
  }

  public interface ItemClickListener{
    void onClick();
  }

}
