package com.example.deminglee.lab3;

/**
 * Created by Deming Lee on 2017/10/23.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private ListView mProInfo;
    private ImageButton star;
    private TextView goodname;
    private TextView goodprice;
    private TextView goodInfo;
    private ImageButton back;
    private ImageView pic;
    private List<String> more;
    private DetailAdapter detailAdapter;
    private ImageButton car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mProInfo = (ListView) findViewById(R.id.mListView);
        String[] Information = new String[] {"一键下单","分享商品","不感兴趣","查看更多商品促销信息"};
        more = new ArrayList<>();
        for (int i=0; i<Information.length; i++){
            more.add(Information[i]);
        }
        mProInfo.setAdapter(detailAdapter = new DetailAdapter(DetailActivity.this, more));

        goodname = (TextView) findViewById(R.id.goodname);
        goodprice = (TextView) findViewById(R.id.goodprice);
        goodInfo = (TextView) findViewById(R.id.goodinfo);
        pic = (ImageView) findViewById(R.id.pic);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        //接受从MainActivity传来的信息
        String name = null;
        String price = null;
        String info = null;
        if(extras != null){
            name = extras.getString("name");
            price = extras.getString("price");
            info = extras.getString("Info");
        }
        goodname.setText(name);
        goodprice.setText(price);
        goodInfo.setText(info);

        switch (name){
            case "Enchated Forest":
                pic.setImageResource(R.drawable.enchatedforest);
                break;
            case "Arla Milk":
                pic.setImageResource(R.drawable.arla);
                break;
            case "Devondale Milk":
                pic.setImageResource(R.drawable.devondale);
                break;
            case "Kindle Oasis":
                pic.setImageResource(R.drawable.kindle);
                break;
            case "waitrose 早餐麦片":
                pic.setImageResource(R.drawable.waitrose);
                break;
            case "Mcvitie's 饼干":
                pic.setImageResource(R.drawable.mcvitie);
                break;
            case "Ferrero Rocher":
                pic.setImageResource(R.drawable.ferrero);
                break;
            case "Maltesers":
                pic.setImageResource(R.drawable.maltesers);
                break;
            case "Lindt":
                pic.setImageResource(R.drawable.lindt);
                break;
            case "Borggreve":
                pic.setImageResource(R.drawable.borggreve);
                break;
        }


        //addtoshoplist
        final Intent intent1 = new Intent();
        setResult(0,intent1);
        back = (ImageButton)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        car = (ImageButton) findViewById(R.id.car);
        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "商品已添加到购物车", Toast.LENGTH_SHORT).show();
                intent1.putExtra("name", goodname.getText());
                intent1.putExtra("price",goodprice.getText());
                intent1.putExtra("Info", goodInfo.getText());
                setResult(1, intent1);
            }
        });


        //starchange
        star = (ImageButton)findViewById(R.id.star);
        star.setTag("0");
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object tag = star.getTag();
                if(tag == "0"){
                    star.setImageResource(R.drawable.full_star);
                    star.setTag("1");
                }else {
                    star.setImageResource(R.drawable.empty_star);
                    star.setTag("0");
                }
            }
        });
    }
}
