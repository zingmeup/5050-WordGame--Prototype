package com.example.deepak.a5050.datamodels;

import android.support.annotation.NonNull;

public class Word implements Comparable{
    private String word;
    private int score;

    public Word(String word, int score) {
        this.word = word;
        this.score = score;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return Integer.compare(this.score, ((Word)o).score);
    }

    @Override
    public String toString() {
        return word;
    }

    public String getWord() {
        return word;
    }

    public int getScore() {
        return score;
    }
}
