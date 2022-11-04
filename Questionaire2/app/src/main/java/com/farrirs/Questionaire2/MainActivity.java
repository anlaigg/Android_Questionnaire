package com.farrirs.Questionaire2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;



import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.farrirs.Questionaire2.MySQLiteOpenHelper;
import com.farrirs.Questionaire2.R;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase sqLiteDatabase = MySQLiteOpenHelper.getInstance(this).getWritableDatabase();

        //从SQLite数据库中读出数据。
        List<Student> students = query(MySQLiteOpenHelper.getInstance(this).getReadableDatabase());

        HSSFWorkbook mWorkbook = new HSSFWorkbook();
        HSSFSheet mSheet = mWorkbook.createSheet(MySQLiteOpenHelper.TABLE_NAME);
        createExcelHead(mSheet);

        for (Student student : students) {
            //System.out.println(student.id + "," + student.name + "," + student.gender + "," + student.age);
            createCell(student.id, student.name, student.gender, student.age, student.q1, student.q2, student.q3, student.q4, student.q5, student.q6, mSheet);
        }

        File xlsFile = new File(getFilesDir(), "excel.xls");
        try {
            if (!xlsFile.exists()) {
                xlsFile.createNewFile();
            }
            mWorkbook.write(xlsFile);// 或者以流的形式写入文件 mWorkbook.write(new FileOutputStream(xlsFile));
            mWorkbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Info.setGender("");
        Info.setAge("");
        Info.setName("");
        for(int i=0;i<6;i++){
            Info.setIndex(i,0);
        }
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
    }

    private ContentValues getContentValues(String name, String gender, int age,int q1,int q2,int q3,int q4,int q5,int q6) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MySQLiteOpenHelper.COLUMN_NAME, name);
        contentValues.put(MySQLiteOpenHelper.COLUMN_GENDER, gender);
        contentValues.put(MySQLiteOpenHelper.COLUMN_AGE, age);
        contentValues.put(MySQLiteOpenHelper.COLUMN_Q1, q1);
        contentValues.put(MySQLiteOpenHelper.COLUMN_Q2, q2);
        contentValues.put(MySQLiteOpenHelper.COLUMN_Q3, q3);
        contentValues.put(MySQLiteOpenHelper.COLUMN_Q4, q4);
        contentValues.put(MySQLiteOpenHelper.COLUMN_Q5, q5);
        contentValues.put(MySQLiteOpenHelper.COLUMN_Q6, q6);

        return contentValues;
    }

    //查询SQLite数据库。读出所有数据内容。
    @SuppressLint("Range")
    private List<Student> query(SQLiteDatabase db) {
        List<Student> students = null;

        Cursor cursor = db.rawQuery("SELECT * FROM " + MySQLiteOpenHelper.TABLE_NAME, null);
        if (cursor != null && cursor.getCount() > 0) {

            students = new ArrayList<>();

            while (cursor.moveToNext()) {
                Student student = new Student();

                student.id = cursor.getInt(cursor.getColumnIndex(MySQLiteOpenHelper.COLUMN_ID));
                student.name = cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.COLUMN_NAME));
                student.gender = cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.COLUMN_GENDER));
                student.age = cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.COLUMN_AGE));
                student.q1 = cursor.getInt(cursor.getColumnIndex(MySQLiteOpenHelper.COLUMN_Q1));
                student.q2 = cursor.getInt(cursor.getColumnIndex(MySQLiteOpenHelper.COLUMN_Q2));
                student.q3 = cursor.getInt(cursor.getColumnIndex(MySQLiteOpenHelper.COLUMN_Q3));
                student.q4 = cursor.getInt(cursor.getColumnIndex(MySQLiteOpenHelper.COLUMN_Q4));
                student.q5 = cursor.getInt(cursor.getColumnIndex(MySQLiteOpenHelper.COLUMN_Q5));
                student.q6 = cursor.getInt(cursor.getColumnIndex(MySQLiteOpenHelper.COLUMN_Q6));

                students.add(student);
            }
            cursor.close();
        }
        db.close();
        return students;
    }

    //数据容器，装载从数据库中读出的数据内容。
    private class Student {
        public int id;
        public String name;
        public String gender;
        public String age;
        public int q1,q2,q3,q4,q5,q6;

    }

    // 创建Excel标题行，第一行。
    private void createExcelHead(HSSFSheet mSheet) {
        HSSFRow headRow = mSheet.createRow(0);
        headRow.createCell(0).setCellValue(MySQLiteOpenHelper.COLUMN_ID);
        headRow.createCell(1).setCellValue(MySQLiteOpenHelper.COLUMN_NAME);
        headRow.createCell(2).setCellValue(MySQLiteOpenHelper.COLUMN_GENDER);
        headRow.createCell(3).setCellValue(MySQLiteOpenHelper.COLUMN_AGE);
        headRow.createCell(4).setCellValue(MySQLiteOpenHelper.COLUMN_Q1);
        headRow.createCell(5).setCellValue(MySQLiteOpenHelper.COLUMN_Q2);
        headRow.createCell(6).setCellValue(MySQLiteOpenHelper.COLUMN_Q3);
        headRow.createCell(7).setCellValue(MySQLiteOpenHelper.COLUMN_Q4);
        headRow.createCell(8).setCellValue(MySQLiteOpenHelper.COLUMN_Q5);
        headRow.createCell(9).setCellValue(MySQLiteOpenHelper.COLUMN_Q6);

    }

    // 创建Excel的一行数据。
    private static void createCell(int id, String name, String gender, String age,int q1,int q2,int q3,int q4,int q5,int q6, HSSFSheet sheet) {
        HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);

        dataRow.createCell(0).setCellValue(id);
        dataRow.createCell(1).setCellValue(name);
        dataRow.createCell(2).setCellValue(gender);
        dataRow.createCell(3).setCellValue(age);
        dataRow.createCell(4).setCellValue(q1);
        dataRow.createCell(5).setCellValue(q2);
        dataRow.createCell(6).setCellValue(q3);
        dataRow.createCell(7).setCellValue(q4);
        dataRow.createCell(8).setCellValue(q5);
        dataRow.createCell(9).setCellValue(q6);

    }
}