package com.farrirs.Questionaire2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class Login_pad extends AppCompatActivity{
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pad);
        initView();

        spinner = findViewById(R.id.spinner);

        enter_button = findViewById(R.id.enter_button);
        enter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rb_gender1.isChecked())gender="man";
                else if(rb_gender2.isChecked())gender="woman";
                name="默认姓名";
                age=spinner.getSelectedItem().toString();;

                if(gender.equals("")||name.equals("")||age.equals(""))
                {
                    Toast.makeText(Login_pad.this, "Please accomplish your input", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Login_pad.this, Login_pad.class);
                    startActivity(intent);
                    return;
                }
                Info.setGender(gender);
                Info.setAge(age);
                Info.setName(name);
                Intent intent = new Intent(Login_pad.this, question1.class);
                startActivity(intent);
            }
        });

    }

    public void initView(){
        rg_gender = findViewById(R.id.rg_gender);
        rb_gender1 = findViewById(R.id.rb_man);
        rb_gender2 = findViewById(R.id.rb_woman);
    }
}