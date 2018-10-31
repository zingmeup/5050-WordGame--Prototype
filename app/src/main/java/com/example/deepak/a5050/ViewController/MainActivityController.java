package com.example.deepak.a5050.ViewController;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.deepak.a5050.MainActivity;
import com.example.deepak.a5050.R;
import com.example.deepak.a5050.datamodels.DailyWord;
import com.example.deepak.a5050.network.NetworkOperations;

import java.util.ArrayList;
import java.util.EventListener;

public class MainActivityController {
    MainActivity activity;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ArrayAdapter<String> completedWordsListAdapter;
    View.OnClickListener splashBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!(activity.getSplashUsername().getText().toString().trim()).equals("")) {
                editor.putString("playername", activity.getSplashUsername().getText().toString().trim());
                editor.putBoolean("first_launch", false);
                editor.commit();
                activity.displayLoadingSplash();
                activity.getSplashLoading().setOnShowListener(loadingSplashShowListener);
                activity.getSplashLoading().show();
                activity.getSplashFirstTime().dismiss();
            } else {
                Toast.makeText(activity, "Enter player name", Toast.LENGTH_SHORT).show();
            }
        }
    };
    Dialog.OnShowListener loadingSplashShowListener = new DialogInterface.OnShowListener() {
        @Override
        public void onShow(DialogInterface dialog) {
            Thread nop = new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.e("NetworkOperations", "running");
                    NetworkOperations.getInstance(activity).checkDailyWord(activity, sharedPreferences.getString("wordofTheDay", "xnullx"));
                }
            });
            nop.start();
        }
    };
    View.OnClickListener progressClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Thread nop = new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.e("NetworkOperations", "running");
                    NetworkOperations.getInstance(activity).checkDailyWord(activity, sharedPreferences.getString("wordofTheDay", "xnullx"));
                }
            });
            nop.start();
        }
    };

    public MainActivityController(MainActivity activity) {
        this.activity = activity;
        sharedPreferences = activity.getSharedPreferences(activity.getPackageName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        splash();
        setEventListeners();
        completedWordsListAdapter=new ArrayAdapter<String>(activity, R.layout.row_completed_list, DailyWord.getDailyWord().getCompletedList());
        activity.getCompletedList().setAdapter(completedWordsListAdapter);
        completedWordsListAdapter.notifyDataSetChanged();
    }

    private void splash() {
        if (sharedPreferences.getBoolean("first_launch", true)) {
            activity.displayFirstTimeSplash();
            activity.getSplashFirstTime().show();
            activity.getSplashBtn().setOnClickListener(splashBtnListener);
        } else {
            activity.displayLoadingSplash();
            activity.getSplashProgress().setOnClickListener(progressClickListener);
            activity.getSplashLoading().setOnShowListener(loadingSplashShowListener);
            activity.getSplashLoading().show();
        }
    }

    View.OnClickListener infoViewListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(activity, DailyWord.getDailyWord().getHint(), Toast.LENGTH_SHORT).show();
        }
    };
    View.OnClickListener refreshListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(activity, "Refreshing", Toast.LENGTH_SHORT).show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    NetworkOperations.getInstance(activity).checkDailyWord(activity, sharedPreferences.getString("wordofTheDay","xnullx"));

                }
            }).start();
            completedWordsListAdapter.notifyDataSetChanged();
        }
    };
    TextWatcher dailyWordViewTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.e("main-onTextChanged", activity.getInputDailyWordView().getText().toString());
            if (before < count) {
                checkAndDoTheFuckLogic();
            }


        }

        private void checkAndDoTheFuckLogic() {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    if (DailyWord.getDailyWord().getTempList().contains(activity.getInputDailyWordView().getText().toString())) {

                        final int indexOnWordList = DailyWord.getDailyWord().getWordList().indexOf(activity.getInputDailyWordView().getText().toString().toLowerCase());
                        int score = ((DailyWord.getDailyWord().getWordList().size() - indexOnWordList) / (DailyWord.getDailyWord().getWordList().size() / 10)) + 1;
                        final int newScore = score + Integer.valueOf(activity.getScoreCountView().getText().toString());
                        DailyWord.getDailyWord().getCompletedList().add(0,DailyWord.getDailyWord().getWordList().get(indexOnWordList));
                        DailyWord.getDailyWord().getTempList().remove(activity.getInputDailyWordView().getText().toString());
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                activity.getScoreCountView().setText(String.valueOf(newScore));
                                activity.getWordCountView().setText(String.valueOf(DailyWord.getDailyWord().getCompletedList().size()));
                                activity.getJustTypedView().setText(DailyWord.getDailyWord().getWordList().get(indexOnWordList));
                                activity.getJustTypedView().setVisibility(View.INVISIBLE);
                                activity.getJustTypedView().setAlpha(0f);
                                activity.getJustTypedView().setVisibility(View.VISIBLE);
                                activity.getJustTypedView().animate().alpha(1f).setDuration(1000).setListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        /*activity.getJustTypedView().setAlpha(1f);
                                        activity.getJustTypedView().animate().alphaBy(05f).setDuration(500).setListener(null);
                                        */
                                        activity.getJustTypedView().setVisibility(View.INVISIBLE);
                                        completedWordsListAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {

                                    }
                                });
                                activity.getJustTypedView().setVisibility(View.VISIBLE);
                            }
                        });

                        activity.getInputDailyWordView().setText("");
                    }
                }
            }).start();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void setEventListeners() {
        Log.e("eventsLiseners", "set");
        activity.getInfoView().setOnClickListener(infoViewListener);
        activity.getRefreshView().setOnClickListener(refreshListener);
        activity.getInputDailyWordView().addTextChangedListener(dailyWordViewTextWatcher);
    }
}
