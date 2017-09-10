package com.example.administrator.tanmu.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.tanmu.R;

/**
 * Created by andrew on 2017/8/10.
 */

public class AdapterScrollItem extends RecyclerView.Adapter<AdapterScrollItem.ScrollItemHolder> {

    private int[] mList;

    public AdapterScrollItem(int[] mList) {
        super();
        this.mList = mList;
    }

    @Override
    public ScrollItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scroll_recycler_item, parent, false);
        return new ScrollItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ScrollItemHolder holder, int position) {
        holder.scrollImage.setImageResource(mList[position % 4]);
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public int getItemRawCount() {
        return mList == null ? 0 : mList.length;
    }

    public class ScrollItemHolder extends RecyclerView.ViewHolder {
        public ImageView scrollImage;

        public ScrollItemHolder(View itemView) {
            super(itemView);
            scrollImage = (ImageView) itemView.findViewById(R.id.scrollImage);
        }
    }
}
