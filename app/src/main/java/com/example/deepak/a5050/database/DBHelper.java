package com.example.deepak.a5050.database;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import com.example.deepak.a5050.datamodels.DailyWord;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "5050wordgame";
    public static final String DAILY_TABLE_NAME = "daily_word";
    public static final String DAILY_COLUMN_ID = "id";
    public static final String DAILY_COLUMN_DATE = "date";
    public static final String DAILY_COLUMN_WORD = "word";
    public static final String DAILY_COLUMN_WORDLIST = "wordlist";
    public static final String DAILY_COLUMN_HINT = "hint";
    public static final String DAILY_COLUMN_WORDLIST_COMPLETED = "wordlist_comp";
    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL("create table contacts " +
                        "("+DAILY_COLUMN_ID+" integer primary key," +
                        DAILY_COLUMN_DATE+" date," +
                        DAILY_COLUMN_WORD+" text," +
                        DAILY_COLUMN_WORDLIST+" text," +
                        DAILY_COLUMN_WORDLIST_COMPLETED+" text," +
                        DAILY_COLUMN_WORDLIST+" text"+")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS "+DAILY_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertWord (String word, String date, String wordlist, String hint, String compList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DAILY_COLUMN_WORD, word);
        contentValues.put(DAILY_COLUMN_DATE, date);
        contentValues.put(DAILY_COLUMN_WORDLIST, wordlist);
        contentValues.put(DAILY_COLUMN_WORDLIST_COMPLETED, compList);
        contentValues.put(DAILY_COLUMN_HINT, hint);
        db.insert(DAILY_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getData(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+DAILY_TABLE_NAME+" where date="+date+"", null );
        return res;
    }



    public boolean updateCompletedWordList (String word, String date, String wordlist, String hint, String compList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DAILY_COLUMN_WORDLIST_COMPLETED, compList);
        db.update(DAILY_TABLE_NAME, contentValues, "date = ? ", new String[] { date } );
        return true;
    }



    public void getDailyWordFromDB(String date){
        Cursor cursor=getData(date);
        cursor.moveToFirst();
        DailyWord.getDailyWord().setId(cursor.getInt(cursor.getColumnIndex(DAILY_COLUMN_ID)));
        DailyWord.getDailyWord().setDate(cursor.getString(cursor.getColumnIndex(DAILY_COLUMN_DATE)));
        DailyWord.getDailyWord().setHint(cursor.getString(cursor.getColumnIndex(DAILY_COLUMN_HINT)));
        DailyWord.getDailyWord().setWord(cursor.getString(cursor.getColumnIndex(DAILY_COLUMN_WORD)));
        DailyWord.getDailyWord().setWordlistStr(cursor.getString(cursor.getColumnIndex(DAILY_COLUMN_WORDLIST)));
        DailyWord.getDailyWord().setCompletedListStr(cursor.getString(cursor.getColumnIndex(DAILY_COLUMN_WORDLIST_COMPLETED)));
    }
}
/*    public ArrayList<String> getAllCotacts() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }*/
/*    public Integer deleteContact (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("contacts",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }*/
/*    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }*/