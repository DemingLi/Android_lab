package com.example.deminglee.lab3;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Deming Lee on 2017/10/23.
 */

public class DetailAdapter extends BaseAdapter {
    private Context context;
    private List<String> text;

    public DetailAdapter(Context context, List<String> text){
        this.context = context;
        this.text = text;
    }
    @Override
    public int getCount(){
        if(text == null){
            return 0;
        }
        return text.size();
    }
    @Override
    public Object getItem(int i){
        if(text == null){
            return 0;
        }
        return text.get(i);
    }
    @Override
    public long getItemId(int i){
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View convertView;
        DetailAdapter.ViewHolder viewHolder;
        if(view == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.information,null);
            viewHolder = new DetailAdapter.ViewHolder();
            viewHolder.name = (TextView)convertView.findViewById(R.id.more);
            convertView.setTag(viewHolder);
        } else {
            convertView = view;
            viewHolder = (DetailAdapter.ViewHolder)convertView.getTag();
        }
        viewHolder.name.setText(text.get(i));
        return convertView;
    }

    private class ViewHolder{
        public TextView name;
    }
}

