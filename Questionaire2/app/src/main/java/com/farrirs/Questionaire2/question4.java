package com.farrirs.Questionaire2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;

public class question4 extends AppCompatActivity implements View.OnClickListener {
    private VideoView videoView; //主菜单

    Button btnReplay,btnBack,btnNext,butAdd;
    TextView abandon,export;
    RadioButton[] choices=new RadioButton[4];
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question4);
        choices[1]=findViewById(R.id.radioButton1);
        choices[2]=findViewById(R.id.radioButton2);
        choices[3]=findViewById(R.id.radioButton3);

        if(Info.getIndex(3)!=0)choices[Info.getIndex(3)].setChecked(true);

        videoView = (VideoView)findViewById(R.id.videoView1);

//        Button btnPlay = (Button)findViewById(R.id.btnPlay);
        btnReplay = findViewById(R.id.btnReplay);
        btnReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.resume();
            }
        });

        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=1;i<4;i++)
                {
                    if(choices[i].isChecked())
                    {
                        Info.setIndex(3,i);
                    }
                }

                Intent intent = new Intent(question4.this, question5.class);
                startActivity(intent);
            }
        });

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=1;i<4;i++)
                {
                    if(choices[i].isChecked())
                    {
                        Info.setIndex(3,i);
                    }
                }

                Intent intent = new Intent(question4.this, question3.class);
                startActivity(intent);
            }
        });

        butAdd = findViewById(R.id.btnAdd);
        butAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<6;i++)
                {
                    if(Info.getIndex(i)==0)
                    {
                        Toast.makeText(question4.this, "问卷第"+String.valueOf(i+1)+"题未填写", Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        MySQLiteOpenHelper myDB = new MySQLiteOpenHelper(question4.this);
                        myDB.addItem(Info.getName(),Info.getGender(),Info.getAge(),
                                Info.getIndex(0),Info.getIndex(1),Info.getIndex(2),
                                Info.getIndex(3), Info.getIndex(4),Info.getIndex(5));
                    }

                }
            }
        });




        abandon= findViewById(R.id.abandon);
        abandon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Info.setGender("");
                Info.setAge("");
                Info.setName("");
                for(int i=0;i<6;i++){
                    Info.setIndex(i,0);
                }
                Intent intent = new Intent(question4.this, Login.class);
                startActivity(intent);
                return;
            }
        });
        export=findViewById(R.id.export);
        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(question4.this, MainActivity.class);
                startActivity(intent);
                return;
            }
        });

        String url="android.resource://"+getPackageName()+"/"+R.raw.qq4;
        videoView.setVideoURI(Uri.parse(url));
        videoView.start();
    }

    @Override
    //示用对权限的取得结果进行判断，并针对性操作。获得权限，执行初始化；如果没有获得权限，提户。
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(this, "拒绝权限，无法使用程序。", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }

    @Override
    //统一处理Play(播放)、Pause(暂停)、Replay(重新播放)的逻辑
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnReplay:
                if(videoView.isPlaying()){
                    videoView.resume();//重新播放
                }
                break;
        }
    }
    @Override
    //执行完毕，释放所有资源
    protected void onDestroy() {
        super.onDestroy();
        if(videoView != null){
            videoView.suspend();
        }
    }

}