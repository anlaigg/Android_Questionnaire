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
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;

public class question2 extends AppCompatActivity implements View.OnClickListener {
    private VideoView videoView; //主菜单

    Button btnReplay,btnBack,btnNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_question2);
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

                Intent intent = new Intent(question2.this, question3.class);
                startActivity(intent);
            }
        });

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(question2.this, question1.class);
                startActivity(intent);
            }
        });

        String url="android.resource://"+getPackageName()+"/"+R.raw.qq2;
        videoView.setVideoURI(Uri.parse(url));
        videoView.start();
    }

    private void initVideoPath() {
        File file = new File(Environment.getExternalStorageDirectory(), "/raw/qq1.mp4");//指定视频文件路径
        Toast.makeText(question2.this, file.getPath(), Toast.LENGTH_SHORT).show();
        videoView.setVideoPath(file.getPath());//加载path文件代表的视频
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);//让视频循环播放
            }
        });
    }

    @Override
    //示用对权限的取得结果进行判断，并针对性操作。获得权限，执行初始化；如果没有获得权限，提户。
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initVideoPath();
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