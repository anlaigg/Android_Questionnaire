package com.farrirs.Questionaire2;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class questionRepository{
    public String body=new String();
    public String[] choices=new String[3];
    public int answerThreePoints=-1;
    public int answerOnePoint=-1;
    questionRepository(String _body,String _choice0,String _choice1,String _choice2,int _answerThreePoints, int _answerOnePoint)
    {
        body=_body;
        choices[0]=_choice0;
        choices[1]=_choice1;
        choices[2]=_choice2;
        answerThreePoints=_answerThreePoints;
        answerOnePoint=_answerOnePoint;
    }
    questionRepository(questionRepository base)
    {
        body=base.body;
        choices[0]=base.choices[0];
        choices[1]=base.choices[1];
        choices[2]=base.choices[2];
        answerThreePoints=base.answerThreePoints;
        answerOnePoint=base.answerOnePoint;
    }
}