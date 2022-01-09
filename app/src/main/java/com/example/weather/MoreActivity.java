package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weather.db.DBManager;

public class MoreActivity extends AppCompatActivity implements View.OnClickListener {
    TextView bgTv,cacheTv,versionTv,shareTv;
    RadioGroup exbgRG;
    ImageView backIv;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        bgTv=findViewById(R.id.more_tv_exchangebg);
        cacheTv=findViewById(R.id.more_tv_cache);
        versionTv=findViewById(R.id.more_tv_version);
        shareTv=findViewById(R.id.more_tv_share);
        exbgRG=findViewById(R.id.more_rg);
        backIv=findViewById(R.id.more_iv_back);
        cacheTv.setOnClickListener(this);
        bgTv.setOnClickListener(this);
        shareTv.setOnClickListener(this);
        backIv.setOnClickListener(this);
        pref = getSharedPreferences("bg_pref", MODE_PRIVATE);

        String versionName=getVersionName();
        versionTv.setText("当前版本：  v"+versionName);
        setRGListener();
    }

    private void setRGListener() {
//        设置改变图片单选按钮的监听
        exbgRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                获取目前的默认地址
                int bg = pref.getInt("bg", 0);
                SharedPreferences.Editor editor=pref.edit();
                Intent intent=new Intent(MoreActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                switch (checkedId) {
                    case R.id.more_rb_green:
                        if (bg==0) {
                            Toast.makeText(MoreActivity.this,"你选择的为当前背景无需改变！",Toast.LENGTH_SHORT).show();
                        }
                        editor.putInt("bg",0);
                        editor.commit();
                        break;
                    case R.id.more_rb_pink:
                        if (bg==1) {
                            Toast.makeText(MoreActivity.this,"你选择的为当前背景无需改变！",Toast.LENGTH_SHORT).show();
                        }
                        editor.putInt("bg",1);
                        editor.commit();
                        break;
                    case R.id.more_rb_blue:
                        if (bg==2) {
                            Toast.makeText(MoreActivity.this,"你选择的为当前背景无需改变！",Toast.LENGTH_SHORT).show();
                        }
                        editor.putInt("bg",2);
                        editor.commit();
                        break;
                }
                startActivity(intent);
            }
        });
    }

    public String getVersionName() {
//        获取应用的版本
        PackageManager manager = getPackageManager();
        String versionName=null;
        try {
            PackageInfo info=manager.getPackageInfo(getPackageName(),0);
            versionName=info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.more_tv_exchangebg:
                if (exbgRG.getVisibility()== View.VISIBLE) {
                    exbgRG.setVisibility(View.GONE);
                } else {
                    exbgRG.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.more_tv_cache:
                clearCache();
                break;
            case R.id.more_tv_share:
                shareSoftwareMsg("说天气App是一款超萌的天气预报软件，画面简约，播报天气非常精准，快来下载吧！");
                break;
            case R.id.more_iv_back:
                finish();
                break;
        }
    }

    private void shareSoftwareMsg(String s) {
//        分享软件的函数
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,s);
        startActivity(Intent.createChooser(intent,"说天气"));
    }

    private void clearCache() {
//        清楚缓存
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示信息").setMessage("确定要删除所有缓存么？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DBManager.deleteAllInfo();
                Toast.makeText(MoreActivity.this,"已清除全部缓存！",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MoreActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        }).setNegativeButton("取消",null);
        builder.create().show();

    }

}
