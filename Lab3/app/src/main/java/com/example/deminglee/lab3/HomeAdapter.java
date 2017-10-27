package com.example.deminglee.lab3;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.View;

import java.util.List;

/**
 * Created by Deming Lee on 2017/10/23.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    private Context context;
    private List<ShoppingItem> mDatas;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }
    public HomeAdapter(List<ShoppingItem> mDatas, Context context){
        this.mDatas = mDatas;
        this.context = context;
    }
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.home,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position){

        holder.name.setText(mDatas.get(position).getCommodity());
        holder.first_letter.setText(mDatas.get(position).getCommodity().substring(0,1));

        if(mOnItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    mOnItemClickListener.onClick(holder.getAdapterPosition());
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v){
                    mOnItemClickListener.onLongClick(holder.getAdapterPosition());
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount(){
        return mDatas.size();
    }

    class MyViewHolder extends ViewHolder{
        TextView name;
        TextView first_letter;
        public MyViewHolder(View view){
            super(view);
            name = (TextView)view.findViewById(R.id.name);
            first_letter = (TextView)view.findViewById(R.id.first_letter);
        }
    }

    public void removeData(int position)
    {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }
}
