package com.farrirs.Questionaire2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class Login extends AppCompatActivity{
    public Context  context;
    String name="";
    String gender="";
    String age="";

    EditText input_name,input_age;
    RadioGroup rg_gender;
    RadioButton rb_gender1;
    RadioButton rb_gender2;
    Button enter_button;
    Spinner spinner;

    private void checkNeedPermissions(){
        //6.0以上需要动态申请权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //多个权限一起申请
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, 1);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.e("接收广播", "onReceive: 启动了。。。");
        checkNeedPermissions();
        initView();
        enter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rb_gender1.isChecked())gender="man";
                else if(rb_gender2.isChecked())gender="woman";
                name="默认姓名";
                age=spinner.getSelectedItem().toString();;

                if(gender.equals("")||name.equals("")||age.equals(""))
                {
                    Toast.makeText(Login.this, "Please accomplish your input", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Login.this, Login.class);
                    startActivity(intent);
                    return;
                }
                Info.setSex(gender);
                Info.setAge(age);
                Intent intent = new Intent(Login.this, question.class);
                startActivity(intent);
            }
        });


    }

    public void initView(){
        enter_button = findViewById(R.id.enter_button);
        spinner = findViewById(R.id.spinner);
        rg_gender = findViewById(R.id.rg_gender);
        rb_gender1 = findViewById(R.id.rb_man);
        rb_gender2 = findViewById(R.id.rb_woman);
    }
}