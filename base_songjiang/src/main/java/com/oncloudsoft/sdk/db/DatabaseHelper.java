package com.oncloudsoft.sdk.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by JX on 2018/5/30.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "recognizer.db";//数据库名称
    private static final int SCHEMA_VERSION = 2;//版本号,则是升级之后的,升级方法请看onUpgrade方法里面的判断

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLString.LASTMESSAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO 2019/3/5 数据库升级待测试
        //数据库升级
        if (newVersion - oldVersion == 2) {
            db.execSQL("alter table lastMessage add column lastName char(16)");
            db.execSQL("alter table lastMessage add column excludeNumber char(16)");
        }else if(newVersion - oldVersion == 1){
            db.execSQL("alter table lastMessage add column excludeNumber char(16)");
        }
    }
}
