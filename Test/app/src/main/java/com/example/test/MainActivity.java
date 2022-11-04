package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;



import android.content.ContentValues;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.os.Environment;

        import org.apache.poi.hssf.usermodel.HSSFRow;
        import org.apache.poi.hssf.usermodel.HSSFSheet;
        import org.apache.poi.hssf.usermodel.HSSFWorkbook;

        import java.io.File;
        import java.util.ArrayList;
        import java.util.List;

/**
 * 本例数据样本容量很小，简单期间放到UI主线程中直接读写SQLite数据库。
 * 实际的开发中不允许这样在UI主线程读写SQLite数据库，应该把读写数据库操作的代码后台线程化。
 *
 *
 * 本例出于演示期间，把写文件操作放到UI主线了。事实上读写文件操作也应该放到非UI线程中处理。
 *
 *
 * 另外：
 * HSSFWorkbook:操作Excel 97-2003版本，Excel扩展名是.xls。
 * XSSFWorkbook:操作Excel 2007以后版本，Excel扩展名是.xlsx。
 * 从POI 3.8开始，提供了一种基于XSSF的低内存占用的SXSSF。
 *
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase sqLiteDatabase = MySQLiteOpenHelper.getInstance(this).getWritableDatabase();

        ContentValues contentValues1 = getContentValues("zhang", "男", 18);
        ContentValues contentValues2 = getContentValues("phil", "男", 19);

        //往SQLite数据库中插入两条数据。
        sqLiteDatabase.insert(MySQLiteOpenHelper.TABLE_NAME, null, contentValues1);
        sqLiteDatabase.insert(MySQLiteOpenHelper.TABLE_NAME, null, contentValues2);
        sqLiteDatabase.close();

        //从SQLite数据库中读出数据。
        List<Student> students = query(MySQLiteOpenHelper.getInstance(this).getReadableDatabase());

        HSSFWorkbook mWorkbook = new HSSFWorkbook();
        HSSFSheet mSheet = mWorkbook.createSheet(MySQLiteOpenHelper.TABLE_NAME);
        createExcelHead(mSheet);

        for (Student student : students) {
            //System.out.println(student.id + "," + student.name + "," + student.gender + "," + student.age);
            createCell(student.id, student.name, student.gender, student.age, mSheet);
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
    }

    private ContentValues getContentValues(String name, String gender, int age) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MySQLiteOpenHelper.STUDENT_NAME, name);
        contentValues.put(MySQLiteOpenHelper.STUDENT_GENDER, gender);
        contentValues.put(MySQLiteOpenHelper.STUDENT_AGE, age);
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

                student.id = cursor.getInt(cursor.getColumnIndex(MySQLiteOpenHelper.STUDENT_ID));
                student.name = cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.STUDENT_NAME));
                student.gender = cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.STUDENT_GENDER));
                student.age = cursor.getInt(cursor.getColumnIndex(MySQLiteOpenHelper.STUDENT_AGE));

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
        public int age;
    }

    // 创建Excel标题行，第一行。
    private void createExcelHead(HSSFSheet mSheet) {
        HSSFRow headRow = mSheet.createRow(0);
        headRow.createCell(0).setCellValue(MySQLiteOpenHelper.STUDENT_ID);
        headRow.createCell(1).setCellValue(MySQLiteOpenHelper.STUDENT_NAME);
        headRow.createCell(2).setCellValue(MySQLiteOpenHelper.STUDENT_GENDER);
        headRow.createCell(3).setCellValue(MySQLiteOpenHelper.STUDENT_AGE);
    }

    // 创建Excel的一行数据。
    private static void createCell(int id, String name, String gender, int age, HSSFSheet sheet) {
        HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);

        dataRow.createCell(0).setCellValue(id);
        dataRow.createCell(1).setCellValue(name);
        dataRow.createCell(2).setCellValue(gender);
        dataRow.createCell(3).setCellValue(age);
    }
}