package com.farrirs.Questionaire2;

import static java.util.Collections.shuffle;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class question extends AppCompatActivity{

        private VideoView videoView; //主菜单
        Button btnBack,btnNext;
        TextView abandon,export;
        RadioGroup rg_gender;
        RadioButton[] choices=new RadioButton[3];
        TextView text;
        List questions=new ArrayList();
        int numQuestions=6;
        int currentQuestion=0;
        int myAnswers[]=new int[]{-1,-1,-1,-1,-1,-1};
        List index=new ArrayList();

        void setQuestionIndex(int idx)
        {
            currentQuestion=idx;
            if(idx==numQuestions-1)
            {
                btnNext.setText("提交");
            }
            else
            {
                btnNext.setText("下一题");
            }

            questionRepository tmp= new questionRepository((questionRepository) questions.get((int) index.get(currentQuestion)));
            text.setText(String.valueOf(currentQuestion+1)+". "+tmp.body);
            for(int i=0;i<3;i++) choices[i].setText(tmp.choices[i]);
            btnNext.setAlpha(0);
            btnBack.setAlpha(0);
            text.setAlpha(0);
            rg_gender.setAlpha(0);
            int currentIndex= (int) index.get(currentQuestion);
            for(int i=0 ;i< rg_gender.getChildCount();i++){
                rg_gender.getChildAt(i).setEnabled(false);
                if(i!=myAnswers[currentIndex])
                    choices[i].setChecked(false);
                else
                    choices[i].setChecked(true);
            }
            String url=new String();
            switch (currentIndex)
            {
                case 0:
                    url="android.resource://"+getPackageName()+"/"+R.raw.qq1;
                    break;
                case 1:
                    url="android.resource://"+getPackageName()+"/"+R.raw.qq2;
                    break;
                case 2:
                    url="android.resource://"+getPackageName()+"/"+R.raw.qq3;
                    break;
                case 3:
                    url="android.resource://"+getPackageName()+"/"+R.raw.qq4;
                    break;
                case 4:
                    url="android.resource://"+getPackageName()+"/"+R.raw.qq5;
                    break;
                case 5:
                    url="android.resource://"+getPackageName()+"/"+R.raw.qq6;
                    break;
            }
            videoView.setVideoURI(Uri.parse(url));
            videoView.start();
        }
        int computeScore()
        {
            int result=0;
            for(int i=0;i<myAnswers.length;i++)
            {
                if(myAnswers[i]==((questionRepository) questions.get(i)).answerThreePoints) result+=3;
                else if(myAnswers[i]==((questionRepository) questions.get(i)).answerOnePoint) result+=1;
            }
            return result;
        }
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
        void writeResult(int grade) throws IOException {
            String sex=Info.getSex();
            String age=Info.getAge();
            File sdPath = Environment.getExternalStorageDirectory();
            if (!sdPath.exists()) {
                Toast.makeText(question.this,"不存在SD卡目录",Toast.LENGTH_SHORT).show();
                return;
            }
            File  myFile= new File(sdPath, "record.csv");
            if(myFile.exists())
            {
                RandomAccessFile  raf = new RandomAccessFile(myFile, "rw");
                raf.seek(myFile.length());
                String content=age+","+sex+","+String.valueOf(grade);
                raf.write(content.getBytes());
                raf.write("\n".getBytes());
                if (raf != null) {
                    raf.close();
                }
            }
            else
            {
                myFile.createNewFile();
                FileOutputStream out = new FileOutputStream(myFile);
                out.write("age,gender,grade\n".getBytes());
                String content=age+","+sex+","+String.valueOf(grade);
                out.write(content.getBytes());
                out.write("\n".getBytes());
                out.flush();
                if (out != null) {
                    out.close();
                }
            }
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            checkNeedPermissions();
            setContentView(R.layout.activity_question);
            questions.add(new questionRepository("当同伴让你保存奇怪的药品时，你会怎么办？", "帮忙保存", "拒绝保存", "不知所措", 0, 2));
            questions.add(new questionRepository("当同伴带你去KTV时，发现有人吸食奇怪的气体，你会怎么办？", "报警", "跟着吸一口", "视而不见", 1, 2));
            questions.add(new questionRepository("当你出游时，有人强迫你吸食笑气，你会怎么办？", "跟着吸一口", "坚决不吸", "悄悄地逃离", 0, 2));
            questions.add(new questionRepository("当你在校门口遇到禁毒宣传时，你会怎么办？", "将传单丢掉", "婉言拒绝", "一起加入宣传", 0, 1));
            questions.add(new questionRepository("如果你是场景中的同学，你会怎么办？", "仅了解毒品信息，但不会选择去购买", "会尝试购买毒品", "把链接分享给别人", 1, 2));
            questions.add(new questionRepository("当有民警走进课堂进行禁毒宣传时，你会怎么办？", "不关心，悄悄复习其他功课", "认真听讲", "坐着发呆", 0, 2));
            for(int i=0;i<numQuestions;i++) index.add(i);
            shuffle(index);

            text=findViewById(R.id.textView);
            rg_gender=findViewById(R.id.radioGroup);

            choices[0]=findViewById(R.id.radioButton1);
            choices[1]=findViewById(R.id.radioButton2);
            choices[2]=findViewById(R.id.radioButton3);

            videoView = (VideoView)findViewById(R.id.videoView1);

            btnNext = findViewById(R.id.btnNext);
            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for(int i=0;i<3;i++)
                    {
                        if(choices[i].isChecked())
                        {
                            myAnswers[(int) index.get(currentQuestion)]=i;
                            break;
                        }
                    }
                    if(currentQuestion+1<numQuestions)
                        setQuestionIndex(currentQuestion+1);
                    else {
                        Info.setScore(computeScore());
                        Intent intent = new Intent(question.this, result.class);
                        startActivity(intent);
                    }
                }
            });

            btnBack = findViewById(R.id.btnBack);
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for(int i=0;i<3;i++)
                    {
                        if(choices[i].isChecked())
                        {
                            myAnswers[(int) index.get(currentQuestion)]=i;
                            break;
                        }
                    }
                    if(currentQuestion-1>=0)
                        setQuestionIndex(currentQuestion-1);
                }
            });


            abandon= findViewById(R.id.abandon);
            abandon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(question.this, Login.class);
                    startActivity(intent);
                    return;
                }
            });

            export=findViewById(R.id.export);
            export.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int grade=computeScore();
                    try {
                        writeResult(grade);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });


            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
            {
                @Override
                public void onCompletion(MediaPlayer mp)
                {
                    text.setAlpha(1);
                    rg_gender.setAlpha(1);
                    btnBack.setAlpha(1);
                    btnNext.setAlpha(1);
                    for(int i=0 ;i< rg_gender.getChildCount();i++){
                        rg_gender.getChildAt(i).setEnabled(true);
                    }
                }
            });
            setQuestionIndex(currentQuestion);
        }

        private void initVideoPath(String path) {
            File file = new File(Environment.getExternalStorageDirectory(), path);//指定视频文件路径
            Toast.makeText(question.this, file.getPath(), Toast.LENGTH_SHORT).show();
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
                        initVideoPath("/raw/qq1.mp4");
                        initVideoPath("/raw/qq2.mp4");
                        initVideoPath("/raw/qq3.mp4");
                        initVideoPath("/raw/qq4.mp4");
                        initVideoPath("/raw/qq5.mp4");
                        initVideoPath("/raw/qq6.mp4");
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
        //执行完毕，释放所有资源
        protected void onDestroy() {
            super.onDestroy();
            if(videoView != null){
                videoView.suspend();
            }
        }



}