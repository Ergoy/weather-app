package com.example.weather.city_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.weather.R;
import com.example.weather.db.DBManager;

import java.util.ArrayList;
import java.util.List;

public class DeleteCityActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView errorIv,rightIv;
    ListView deleteLv;
    List<String> mDatas;
    List<String> deleteCitys;//表示存储了删除的城市信息
    private DeleteCityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_city);
        errorIv=findViewById(R.id.delete_iv_error);
        rightIv=findViewById(R.id.delete_iv_right);
        deleteLv=findViewById(R.id.delete_lv);
        mDatas=DBManager.queryAllCityName();
        deleteCitys=new ArrayList<>();
        errorIv.setOnClickListener(this);
        rightIv.setOnClickListener(this);
        adapter = new DeleteCityAdapter(this, mDatas, deleteCitys);
        deleteLv.setAdapter(adapter);
    }

  /*  @Override
    protected void onResume() {
        super.onResume();
        List<String> cityList= DBManager.queryAllCityName();
        mDatas.addAll(cityList);
        adapter.notifyDataSetChanged();
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delete_iv_error:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示信息").setMessage("您确定要舍弃更改吗？").setPositiveButton("舍弃更改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.setNegativeButton("取消",null);
                builder.create().show();
                break;
            case R.id.delete_iv_right:
                for (int i=0;i<deleteCitys.size();i++){
                    String city=deleteCitys.get(i);
                    DBManager.deleteInfoByCity(city);
                }
//                删除成功返回上一界面
                finish();
                break;
        }



                }
        }


