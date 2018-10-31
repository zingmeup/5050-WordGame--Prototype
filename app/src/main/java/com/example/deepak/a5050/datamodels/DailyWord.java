package com.example.deepak.a5050.datamodels;

import android.util.Log;

import java.util.ArrayList;

public class DailyWord {
    private static DailyWord dailyWord = null;
    private int id, numWords;
    private String date, word, wordlistStr, hint, completedListStr;
    private ArrayList<String> wordList, tempList, completedList, suggestionList;

    public DailyWord() {
        wordList = new ArrayList<>();
        tempList = new ArrayList<>();
        completedList = new ArrayList<>();
        suggestionList = new ArrayList<>();
        completedListStr = "";
    }

    public void populateLists() {
        Log.e("populateLists()", "populating Lists");
        populateWordLists();
        populateCompletedList();
        populateTempList();
    }

    public synchronized void populateWordLists() {
        Log.e("populateWordLists()", "populating Lists");
        Log.e("wordlistStr", wordlistStr);
        String[] wordListArray = wordlistStr.split(";");
        for (String a : wordListArray) {
            if (!a.equals("|"))
                wordList.add(a.trim());
        }

        Log.e("populateWordLists()", "Size: " + wordList.size());
        Log.e("populateWordLists()", "Size: " + wordList);
        Log.e("populateWordLists()", "Size: " + wordList.get(1));
        Log.e("populateWordLists()", "Size: " + wordList.get(2));
    }

    public synchronized void populateCompletedList() {
        Log.e("populateCompletedList()", "populating Lists");
        try {
            String[] completedListArray = completedListStr.split(";");
            for (String a : completedListArray) {
                if (!wordList.contains(a.trim())) {
                    if (!a.equals(""))
                        wordList.add(a.trim());
                }
            }

        } catch (NullPointerException npe) {

        }
        Log.e("populateCompletedList()", "Size: " + completedList.size());
    }

    public synchronized void populateTempList() {
        Log.e("populateTempList()", "populating Lists");
        for (String a : wordList) {
            if (!completedList.contains(a.trim())) {
                if (!a.equals(""))
                    tempList.add(a.trim());
            }
        }
        Log.e("populateTempList()", "Size: " + tempList.size());
    }

    public ArrayList<String> getSuggestionList() {
        return suggestionList;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setWordlistStr(String wordlistStr) {
        this.wordlistStr = wordlistStr;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public void setCompletedListStr(String completedListStr) {
        this.completedListStr = completedListStr;
    }

    public void setWordList(ArrayList<String> wordList) {
        this.wordList = wordList;
    }

    public void setTempList(ArrayList<String> tempList) {
        this.tempList = tempList;
    }

    public void setCompletedList(ArrayList<String> completedList) {
        this.completedList = completedList;
    }

    public String getCompletedListStr() {
        return completedListStr;
    }

    public ArrayList<String> getCompletedList() {
        return completedList;
    }

    public ArrayList<String> getTempList() {
        return tempList;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getWord() {
        return word;
    }

    public String getWordlistStr() {
        return wordlistStr;
    }

    public String getHint() {
        return hint;
    }

    public ArrayList<String> getWordList() {
        return wordList;
    }

    public static DailyWord getDailyWord() {
        if (dailyWord == null) {
            dailyWord = new DailyWord();
        }
        return dailyWord;
    }

}
