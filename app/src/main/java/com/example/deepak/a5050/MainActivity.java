package com.example.deepak.a5050;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.deepak.a5050.ViewController.MainActivityController;
import com.example.deepak.a5050.network.NetworkOperations;

import java.util.EventListener;

public class MainActivity extends AppCompatActivity {
    private TextView rankCountView,scoreCountView,wordCountView,dailyWordView,splashTime,splashWord,justTypedView;
    private ImageView refreshView,infoView;
    private EditText inputDailyWordView;
    private ListView completedList;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_hourly:
                    return true;
                case R.id.navigation_daily:
                    return true;
                case R.id.navigation_me:
                    return true;
            }
            return false;
        }
    };
    MainActivityController controller;

    private Dialog splashFirstTime,splashLoading;
    private EditText splashUsername;
    private Button splashBtn;
    private ProgressBar splashProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_daily);
        setViews();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                controller=new MainActivityController(MainActivity.this);
            }
        });
    }

    public void displayFirstTimeSplash(){
        splashFirstTime=new Dialog(this, R.style.AppTheme);
        splashFirstTime.setContentView(R.layout.splash_firsttime);
        splashUsername=splashFirstTime.findViewById(R.id.splash_username);
        splashBtn=splashFirstTime.findViewById(R.id.splash_btn);
        splashFirstTime.setCancelable(false);
    }
    public void displayLoadingSplash(){
        splashLoading=new Dialog(this, R.style.AppTheme);
        splashLoading.setContentView(R.layout.splash_loading);
        splashProgress=splashLoading.findViewById(R.id.splash_progressbar);
        splashLoading.setCancelable(false);
    }

    private void setViews(){
        rankCountView=findViewById(R.id.rankCountView);
        scoreCountView=findViewById(R.id.scoreCountView);
        wordCountView=findViewById(R.id.wordCountView);
        dailyWordView=findViewById(R.id.dailyWordView);
        splashTime=findViewById(R.id.splashtimerView);
        splashWord=findViewById(R.id.spalshWordView);
        refreshView=findViewById(R.id.refreshView);
        infoView=findViewById(R.id.infoView);
        justTypedView=findViewById(R.id.just_typed);
        inputDailyWordView=findViewById(R.id.inputDailyWordView);
        completedList=findViewById(R.id.completedListView);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public TextView getJustTypedView() {
        return justTypedView;
    }

    public Dialog getSplashFirstTime() {
        return splashFirstTime;
    }

    public Dialog getSplashLoading() {
        return splashLoading;
    }

    public TextView getRankCountView() {
        return rankCountView;
    }

    public TextView getScoreCountView() {
        return scoreCountView;
    }

    public TextView getWordCountView() {
        return wordCountView;
    }

    public TextView getDailyWordView() {
        return dailyWordView;
    }

    public TextView getSplashTime() {
        return splashTime;
    }

    public TextView getSplashWord() {
        return splashWord;
    }

    public ImageView getRefreshView() {
        return refreshView;
    }

    public ImageView getInfoView() {
        return infoView;
    }

    public EditText getInputDailyWordView() {
        return inputDailyWordView;
    }

    public ListView getCompletedList() {
        return completedList;
    }

    public EditText getSplashUsername() {
        return splashUsername;
    }

    public Button getSplashBtn() {
        return splashBtn;
    }

    public ProgressBar getSplashProgress() {
        return splashProgress;
    }
}
