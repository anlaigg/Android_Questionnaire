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
        int grade=0;
        if(Info.getIndex(0)==1)grade+=3;
        if(Info.getIndex(0)==3)grade+=1;
        if(Info.getIndex(1)==2)grade+=3;
        if(Info.getIndex(1)==3)grade+=1;
        if(Info.getIndex(2)==1)grade+=3;
        if(Info.getIndex(2)==3)grade+=1;
        if(Info.getIndex(3)==1)grade+=3;
        if(Info.getIndex(3)==2)grade+=1;
        if(Info.getIndex(4)==2)grade+=3;
        if(Info.getIndex(4)==3)grade+=1;
        if(Info.getIndex(5)==1)grade+=3;
        if(Info.getIndex(5)==3)grade+=1;

        Toast.makeText(result.this, "问卷最终得分为"+grade+".", Toast.LENGTH_SHORT).show();


        if(grade<1){
            img.setImageDrawable(getDrawable(R.drawable.excellent));
        }else if(grade<6){
            img.setImageDrawable(getDrawable(R.drawable.pass));
        }else{
            img.setImageDrawable(getDrawable(R.drawable.fail));
        }

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Info.setGender("");
                Info.setAge("");
                Info.setName("");
                for(int i=0;i<6;i++){
                    Info.setIndex(i,0);
                }

                Intent intent = new Intent(result.this, Login.class);
                startActivity(intent);
                return;
            }
        });


    }


}