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
import android.widget.Toast;

public class Login extends AppCompatActivity{
    public Context  context;
    String name="";
    String gender="";
    String age="";
    String school="";

    EditText input_name,input_age,input_school;
    RadioGroup rg_gender;
    RadioButton rb_gender1;
    RadioButton rb_gender2;
    Button enter_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        enter_button = findViewById(R.id.enter_button);
        enter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rb_gender1.isChecked())gender="man";
                else if(rb_gender2.isChecked())gender="woman";
                name=input_name.getText().toString().trim();
                age=input_age.getText().toString().trim();
                school=input_school.getText().toString().trim();


                if(gender.equals("")||name.equals("")||age.equals("")||school.equals(""))
                {
                    Toast.makeText(Login.this, "Please accomplish your input", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Login.this, Login.class);
                    startActivity(intent);
                    return;
                }
                Info.setGender(gender);
                Info.setAge(age);
                Info.setName(name);
                Info.setSchool(school);
                Intent intent = new Intent(Login.this, question1.class);
                startActivity(intent);
            }
        });

    }

    public void initView(){
        rg_gender = findViewById(R.id.rg_gender);
        rb_gender1 = findViewById(R.id.rb_man);
        rb_gender2 = findViewById(R.id.rb_woman);
        input_name = findViewById(R.id.input_name);
        input_age = findViewById(R.id.input_age);
        input_school=findViewById(R.id.input_school);
    }
}