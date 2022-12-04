package com.farrirs.Questionaire2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class result extends AppCompatActivity {

    ImageView img;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        img = findViewById(R.id.img_result);
        int grade=Info.getScore();
        if(grade<1){
            img.setImageDrawable(getDrawable(R.drawable.pass));
        }else if(grade<6){
            img.setImageDrawable(getDrawable(R.drawable.pass));
        }else{
            img.setImageDrawable(getDrawable(R.drawable.fail));
        }

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(result.this, Login.class);
                startActivity(intent);
                return;
            }
        });


    }


}