package com.farrirs.Questionaire2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Login extends AppCompatActivity {
    String name;
    String gender;
    String age;
    RadioGroup rg_gender;
    RadioButton rb_gender1;
    RadioButton rb_gender2;
    Button enter_button;
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        enter_button = findViewById(R.id.enter_button);
        enter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, question1.class);
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