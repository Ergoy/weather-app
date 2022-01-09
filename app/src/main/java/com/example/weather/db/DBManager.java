package com.example.weather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBManager {
    public static SQLiteDatabase database;

    //    初始化数据库信息
    public static void initDB(Context context){
        DBHelper dbHelper = new DBHelper(context);
        database=dbHelper.getWritableDatabase();
    }
//    查找数据库当中城市列表
    public static List<String> queryAllCityName(){
       Cursor cursor= database.query("info",null,null,null,null,null,null);
       List<String> cityList=new ArrayList<>();
       while (cursor.moveToNext()){
           String city=cursor.getString(cursor.getColumnIndex("city"));
           cityList.add(city);
       }
       return cityList;
    }
//    根据城市名称，替换信息内容
    public static int updateInfoByCity(String city,String content) {
        ContentValues values = new ContentValues();
        values.put("content", content);
        return database.update("info", values, "city=?", new String[]{city});
//新增一条城市记录
    }
        public static long addCityInfo(String city,String content) {
            ContentValues values = new ContentValues();
            values.put("city",city);
            values.put("content",content);
            return database.insert("info",null,values);
    }
//    根据城市名查询数据库当中的内容
    public static String queryInfoByCity(String city){
        Cursor cursor=database.query("info",null,"city=?",new String[]{city},null,null,null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            String content=cursor.getString(cursor.getColumnIndex("content"));
            return content;
        }
        return null;
    }
//    存储城市天气要求最多存储五个城市的天气，超过五个不能存储，获取目前存储数量
    public static int getCityCount(){
        Cursor cursor=database.query("info",null,null,null,null,null,null);
        int count=cursor.getCount();
        return count;
    }
//    查询数据库中全部数据
    public static List<DatabaseBean> queryAllInfo(){
        Cursor cursor = database.query("info", null, null, null, null, null, null);
        List<DatabaseBean> list=new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String city = cursor.getString(cursor.getColumnIndex("city"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            DatabaseBean bean = new DatabaseBean(id, city, content);
            list.add(bean);
        }
        return list;
    }
//      根据城市名称删除这个城市在数据库当中的数据
    public static int deleteInfoByCity(String city){
        return database.delete("info","city=?",new String[]{city});
    }
//    删除表当中所有的数据信息
    public static void deleteAllInfo(){
        String sql="delete from info";
        database.execSQL(sql);
    }
}
