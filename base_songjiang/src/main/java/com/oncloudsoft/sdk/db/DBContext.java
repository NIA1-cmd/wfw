package com.oncloudsoft.sdk.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JX on 2018/5/31.
 */

public class DBContext {
    private final DatabaseHelper databaseHelper;
    private final SQLiteDatabase db;
    public DBContext(Context context) {
        //构建DBHelper对象(借助此对象操作数据库)
        //此对象构建时不会构建数据库
        databaseHelper = new DatabaseHelper(context);
        //获得数据库(数据库不存在则创建，存在则打开)
        db = databaseHelper.getWritableDatabase();
    }

  /*  public boolean createTable(String tableName){
        *//**
         * 判断数据库中某张表是否存在
         *//*
            boolean result = false;
            if (tableName == null) {
                return false;
            }
            SQLiteDatabase db = null;
            Cursor cursor = null;
            try {
                //search.db数据库的名字
                db = openOrCreateDatabase("recognizer.db", Context.MODE_PRIVATE, null);
                String sql = "select count(*) as c from Sqlite_master  where type ='table' and name ='" + tableName.trim() + "' ";
                cursor = db.rawQuery(sql, null);
                if (cursor.moveToNext()) {
                    int count = cursor.getInt(0);
                    if (count > 0) {
                        result = true;
                    }
                }

            } catch (Exception e) {
                // TODO: handle exception
            }
            return result;
    }*/
    /**
     * 借助此方法向表中写入数据
     */
    public long insert(ContentValues values, String tableName) {
        //执行插入操作
        long rowId = db.insert(tableName,//表名
                null,//默认传null就可以
                values);//值
        return rowId;
    }

    /**
     * 借助此方法修改数据
     */
    public long update(ContentValues values, String tableName, String Id) {
        //执行更改操作

        long result = db.update(tableName, values,//值
                "id=?",//条件
                new String[]{Id});
        return result;
    }

    //删除数据
    public void delete(String tableName, String id) {
        //2删除数据
        db.delete(tableName, "_id=?", new String[]{id});
    }

    /**
     * 插入最后一条数据
     */
    public void insertLastMessage(String tableName, JSONArray jsonArray) {
        //获得数据库(数据库不存在则创建，存在则打开)
        String sql = "insert into " + tableName + " (`id`,`lastTime`,`sendType`,`lastMessage`,`lastMessageType`,`unreadCount`,`lastName`,`excludeNumber`) values(?,?,?,?,?,?,?,?)";
        SQLiteStatement stat = db.compileStatement(sql);
        db.beginTransaction();  //手动设置开始事务
        try {
            //批量处理操作
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                stat.bindString(1, jsonObject.getString("id"));
                stat.bindString(2, jsonObject.getString("lastTime"));
                stat.bindString(3, jsonObject.getString("sendType"));
                stat.bindString(4, jsonObject.getString("lastMessage"));
                stat.bindString(5, jsonObject.getString("lastMessageType"));
                stat.bindString(6, jsonObject.getString("unreadCount"));
                stat.bindString(7, jsonObject.getString("lastName"));
                stat.bindString(8, jsonObject.getString("excludeNumber"));
                stat.executeInsert();
            }
            db.setTransactionSuccessful(); //设置事务处理成功，不设置会自动回滚不提交。
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public List<DBData> whereQuery(String columnName, String tableName,String whereKey, String whereValue) {
        String sqString = "select " + columnName + " from " + tableName + " where "+ whereKey +"=?";
        Cursor c = db.rawQuery(sqString, new String[]{whereValue});
        List<DBData> listDbData = new ArrayList<>();
        //迭代数据
        while (c.moveToNext()) {//一行一个map
            DBData dbData = new DBData();
            dbData.setWhiteId(c.getString(0));
            dbData.setId(c.getString(1));
            dbData.setLastTime(c.getString(2));
            dbData.setSendType(c.getString(3));
            dbData.setLastMessage(c.getString(4));
            dbData.setLastMessageType(c.getString(5));
            dbData.setUnreadCount(c.getInt(6));
            dbData.setLastName(c.getString(7));
            dbData.setExcludeNumber(c.getInt(8));
            listDbData.add(dbData);
        }
        c.close();
        return listDbData;
    }


    public int whereQueryCount(String columnName, String tableName,String whereKey, String whereValue) {
        int count = 0;
        String sqString = "select " + columnName + " from " + tableName + " where "+ whereKey +"=?";
        Cursor c = db.rawQuery(sqString, new String[]{whereValue});
        //迭代数据
        while (c.moveToNext()) {//一行一个map
            int index = c.getColumnIndex("unreadCount");
            count = c.getInt(index);
        }
        c.close();
        return count;
    }


    /**
     * 多条件查询
     */
    public boolean queryIsUnread(String columnName, String tableName, List<String> list) throws Exception {
        Cursor c = db.query("lastMessage", new String[]{"id", "unreadCount"}, null, null, null, null, null);
        //迭代数据
        while (c.moveToNext()) {
            //一行一个map
            boolean b = list.contains(c.getString(0));
            if (b == true) {
                String unreadCount = c.getString(1);
                if (!unreadCount.equals("0") && !unreadCount.equals("null")) {
                    c.close();
                    return true;
                }
            }
        }
        c.close();
        return false;
    }

    /**
     * 多条件查询
     */
    public JSONObject queryLastTime(String columnName, String tableName, String id) throws Exception {
        String sqString = "select " + columnName + " from " + tableName + " where id=?";
        //quert  查询条件  要有占位符id=?
        Cursor c = db.query("lastMessage", new String[]{"id", "lastTime"}, "id=?", new String[]{id}, null, null, null);
        //迭代数据
        JSONObject jsonObject = new JSONObject();
        while (c.moveToNext()) {
            //一行一个map
            String id1 = c.getString(0);
            String lastTime = c.getString(1);
            jsonObject.put("id",id1);
            jsonObject.put("lastTime",lastTime);
        }
        c.close();
        return jsonObject;
    }


    /**
     * 删除表
     */
    public void deleteTable() {
        db.execSQL("delete from lastMessage");
    }


    public void tableName() {
        Cursor cursor = db.rawQuery("select name from sqlite_master where type='table';", null);
        while (cursor.moveToNext()) {
            //遍历出表名
            String name = cursor.getString(0);
        }
    }
}
