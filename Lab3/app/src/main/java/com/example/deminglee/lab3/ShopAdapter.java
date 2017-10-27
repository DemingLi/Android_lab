package com.example.deminglee.lab3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deming Lee on 2017/10/23.
 */

//ListView逻辑
public class ShopAdapter extends BaseAdapter {
    private Context context;
    private List<ShoppingItem> mDatas;

    public ShopAdapter(Context context, List<ShoppingItem> mDatas){
        this.context = context;
        this.mDatas = mDatas;
    }
    @Override
    public int getCount(){
        if(mDatas == null){
            return 0;
        }
        return mDatas.size();
    }
    @Override
    public Object getItem(int i){
        if(mDatas == null){
            return 0;
        }
        return mDatas.get(i);
    }
    @Override
    public long getItemId(int i){
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        View convertView;
        ViewHolder viewHolder; //新声明一个View变量和ViewHolder变量
        //当view为空时才加载布局，并且创建一个ViewHolder，获得布局中的两个控件。
        if(view == null){
            //通过inflate方法加载布局，context这个参数需要使用adapter的Activity传入
            convertView = LayoutInflater.from(context).inflate(R.layout.shoplist,null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView)convertView.findViewById(R.id.name);
            viewHolder.first_letter = (TextView)convertView.findViewById(R.id.first_letter);
            viewHolder.price = (TextView)convertView.findViewById(R.id.price);
            convertView.setTag(viewHolder);//用setTag方法将处理好的viewHolder放入view中
        } else{ //否则，让convertView等于view，然后从中取出ViewHolder即可。
            convertView = view;
            viewHolder = (ViewHolder)convertView.getTag();
        }
        //从ViewHolder中取出对应的对象，然后赋值
        viewHolder.name.setText(mDatas.get(i).getCommodity());

        viewHolder.first_letter.setText(mDatas.get(i).getCommodity().substring(0,1));

        viewHolder.price.setText(mDatas.get(i).getPrice());

        return convertView;
    }

    private class ViewHolder{
        public TextView name;
        public TextView first_letter;
        public TextView price;
    }

    public void removeData(int position)
    {
        mDatas.remove(position);
        notifyDataSetChanged();
    }
}

