package com.example.deepak.a5050.datamodels;

import android.support.annotation.NonNull;

public class Word2{
    private String word;
    private int score;

    public Word2(String word, int score) {
        this.word = word;
        this.score = score;
    }

    public String getWord() {
        return word;
    }

    public int getScore() {
        return score;
    }
}
