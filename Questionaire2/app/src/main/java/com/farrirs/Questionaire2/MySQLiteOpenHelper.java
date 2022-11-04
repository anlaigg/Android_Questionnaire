package com.farrirs.Questionaire2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    public Context context;
    //数据库名称。
    public static final String DATABASE_NAME = "Questionnair.db";

    //数据库版本号。
    public static int DATABASE_VERSION = 1;

    private static MySQLiteOpenHelper helper;

    //表名。
    public static final String TABLE_NAME = "Result";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_Q1 = "answer1";
    public static final String COLUMN_Q2 = "answer2";
    public static final String COLUMN_Q3 = "answer3";
    public static final String COLUMN_Q4 = "answer4";
    public static final String COLUMN_Q5 = "answer5";
    public static final String COLUMN_Q6 = "answer6";

    //创建数据库表的SQL语句。
    private String sql_create_table = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
            + COLUMN_ID + " integer primary key autoincrement,"
            + COLUMN_NAME + " varchar(60),"
            + COLUMN_GENDER + " varchar(1),"
            + COLUMN_AGE + "int"
            +COLUMN_Q1+"int"
            +COLUMN_Q2+"int"
            +COLUMN_Q3+"int"
            +COLUMN_Q4+"int"
            +COLUMN_Q5+"int"
            +COLUMN_Q6+" int)";

    public MySQLiteOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    public static MySQLiteOpenHelper getInstance(Context context) {
        if (helper == null) {
            helper = new MySQLiteOpenHelper(context);
        }

        return helper;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据库的表，如果不存在。
        db.execSQL(sql_create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    void addItem(String name,String gender,int age,int q1,int q2,int q3,int q4,int q5,int q6){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME,name);
        cv.put(COLUMN_GENDER,gender);
        cv.put(COLUMN_AGE,age);
        cv.put(COLUMN_Q1,q1);
        cv.put(COLUMN_Q2,q2);
        cv.put(COLUMN_Q3,q3);
        cv.put(COLUMN_Q4,q4);
        cv.put(COLUMN_Q5,q5);
        cv.put(COLUMN_Q6,q6);

        long result = db.insert(TABLE_NAME,null,cv);
        if(result == -1){
            Toast.makeText(context,"Faild",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context,"Added successfully",Toast.LENGTH_SHORT).show();

        }
    }
}