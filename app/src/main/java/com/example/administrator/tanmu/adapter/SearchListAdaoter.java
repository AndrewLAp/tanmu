package com.example.administrator.tanmu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.tanmu.R;
import com.example.administrator.tanmu.object.SearchImformation;

import java.util.List;

/**
 * Created by andrew on 2017/9/6.
 */

public class SearchListAdaoter extends BaseAdapter {

    private Context context;
    private List<SearchImformation> list;
    private LayoutInflater layoutInflater;


    public SearchListAdaoter(Context context, List<SearchImformation> list) {
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        SearchImformation searchInformation = list.get(position);
        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.layout_searchlist_item, null);
            viewHolder = new ViewHolder();
            viewHolder.iconName = (TextView) view.findViewById(R.id.iconName);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.iconName.setText(searchInformation.getName());
        return view;
    }

    private class ViewHolder {
        TextView iconName;
    }
}
