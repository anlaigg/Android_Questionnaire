package com.example.test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    //数据库名称。
    public static final String DATABASE_NAME = "zhangphil.db";

    //数据库版本号。
    public static int DATABASE_VERSION = 1;

    private static MySQLiteOpenHelper helper;

    //表名。
    public static final String TABLE_NAME = "Student";

    public static final String STUDENT_ID = "id";
    public static final String STUDENT_NAME = "name";
    public static final String STUDENT_GENDER = "gender";
    public static final String STUDENT_AGE = "age";

    //创建数据库表的SQL语句。
    private String sql_create_table = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + STUDENT_ID + " integer primary key autoincrement," + STUDENT_NAME + " varchar(60)," + STUDENT_GENDER + " varchar(1)," + STUDENT_AGE + " int)";

    public static MySQLiteOpenHelper getInstance(Context context) {
        if (helper == null) {
            helper = new MySQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        return helper;
    }

    public MySQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据库的表，如果不存在。
        db.execSQL(sql_create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}