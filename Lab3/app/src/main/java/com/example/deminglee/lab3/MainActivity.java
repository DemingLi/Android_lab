package com.example.deminglee.lab3;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRcyclerView;
    private ListView shopListView;
    private FloatingActionButton change;
    private HomeAdapter homeAdapter;
    private ShopAdapter shopAdapter;
    private List<ShoppingItem> mDatas;
    private List<ShoppingItem> shopDatas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatas = new ArrayList<>();
        final String[] goods_name = new String[] {"Enchated Forest","Arla Milk","Devondale Milk","Kindle Oasis",
                "waitrose 早餐麦片","Mcvitie's 饼干","Ferrero Rocher","Maltesers","Lindt","Borggreve"};
        final String[] goods_price = new String[] {"¥ 5.00", "¥ 59.00", "¥ 79.00", "¥ 2399.00", "¥ 179.00", "¥ 14.90", "¥ 132.59", "¥ 141.43", "¥ 139.43", "¥ 28.90"};
        final String[] goods_info = new String[] {"作者 Johanna Basford", "产地 德国", "产地 澳大利亚", "版本 8GB", "重量 2Kg", "产地 英国", "重量 300g", "重量 118g", "重量 249g", "重量 640g"};

        for(int i = 0; i < goods_name.length; i++) {
            ShoppingItem shop = new ShoppingItem(goods_name[i], goods_price[i], goods_info[i]);
            mDatas.add(shop);
        }

        mRcyclerView = (RecyclerView) findViewById(R.id.mRecycleView);
        mRcyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRcyclerView.setAdapter(homeAdapter = new HomeAdapter(mDatas, this));

        DefaultItemAnimator animator = new DefaultItemAnimator();
        //设置动画时间
        animator.setAddDuration(300);
        animator.setRemoveDuration(300);
        mRcyclerView.setItemAnimator(animator);

        homeAdapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {

            @Override
            public void onClick(int position) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("name", mDatas.get(position).getCommodity());
                intent.putExtra("price", mDatas.get(position).getPrice());
                intent.putExtra("Info", mDatas.get(position).getInfo());
                startActivityForResult(intent, 0);
            }

            @Override
            public void onLongClick(int position) {
                Toast.makeText(MainActivity.this, "移除第" + (position+1) + "个商品", Toast.LENGTH_SHORT).show();
                homeAdapter.removeData(position);
            }
        });


        //购物车点击事件
        shopDatas = new ArrayList<>();

        final LinearLayout secondView = (LinearLayout) findViewById(R.id.secondview);
        secondView.setVisibility(View.INVISIBLE);
        shopListView = (ListView) findViewById(R.id.mListView);
        shopListView.setAdapter(shopAdapter = new ShopAdapter(MainActivity.this, shopDatas));
        shopListView.setVisibility(View.INVISIBLE);

        shopListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("name", shopDatas.get(position).getCommodity());
                intent.putExtra("price", shopDatas.get(position).getPrice());
                intent.putExtra("Info", shopDatas.get(position).getInfo());
                startActivityForResult(intent, 0);
            }
        });

        shopListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                ShoppingItem temp = (ShoppingItem) parent.getItemAtPosition(position);
                String name = "从购物车移除" + temp.getCommodity();

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("移除商品").setMessage(name + "?").setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try{
                                    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                                    field.setAccessible(true);
                                    field.set(dialog, true);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                shopAdapter.removeData(position);
                            }
                        }).show();
                return true;


            }
        });


        //Activity切换
        change = (FloatingActionButton) findViewById(R.id.fab);
        change.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(mRcyclerView.getVisibility() == View.VISIBLE && shopListView.getVisibility() == View.INVISIBLE){
                    mRcyclerView.setVisibility(View.INVISIBLE);
                    shopListView.setVisibility(View.VISIBLE);
                    secondView.setVisibility(View.VISIBLE);
                    change.setImageResource(R.drawable.mainpage);
                }
                else if(mRcyclerView.getVisibility() == View.INVISIBLE && shopListView.getVisibility() == View.VISIBLE){
                    mRcyclerView.setVisibility(View.VISIBLE);
                    shopListView.setVisibility(View.INVISIBLE);
                    secondView.setVisibility(View.INVISIBLE);
                    change.setImageResource(R.drawable.shoplist);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        if(requestCode == 0 && resultCode == 1){
            //添加到购物车
            Bundle extras = intent.getExtras();
            String name = extras.getString("name");
            String price = extras.getString("price");
            String info = extras.getString("Info");
            ShoppingItem shop = new ShoppingItem(name, price, info);
            shopDatas.add(shop);
            shopAdapter.notifyDataSetChanged();
        }
    }
}