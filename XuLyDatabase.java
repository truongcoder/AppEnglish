package com.example.pvtruong.appenglishlock;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by PVTruong on 05/04/2017.
 */

public class XuLyDatabase {
    Context context;
    DatabTable dt;
    SQLiteDatabase db;

    public XuLyDatabase(Context context) {
        dt=new DatabTable(context);
        dt.createDatabase();
        db=dt.openDatabase();
    }
    public Word[] getRandomTwoWordMusic() {

        Cursor cursor = db.rawQuery("SELECT * FROM Music ORDER BY Random() LIMIT 2", null);
        cursor.moveToFirst();
        Word[] word = new Word[2];// mảng tĩnh
        int i = 0;
        while (!cursor.isAfterLast()) {
            String en = cursor.getString(cursor.getColumnIndex("EnglishMean"));
            String vn = cursor.getString(cursor.getColumnIndex("VietNamMean"));
            word[i] = new Word(en, vn);
            i++;
            cursor.moveToNext();
        }
        return word;

    }

    public Word[] getRandomTwoWordSport() {

        Cursor cursor = db.rawQuery("SELECT * FROM Sport ORDER BY Random() LIMIT 2", null);
        cursor.moveToFirst();
        Word[] word = new Word[2];// mảng tĩnh
        int i = 0;
        while (!cursor.isAfterLast()) {
            String en = cursor.getString(cursor.getColumnIndex("EnglishMean"));
            String vn = cursor.getString(cursor.getColumnIndex("VietNameMean"));
            word[i] = new Word(en, vn);
            i++;
            cursor.moveToNext();
        }
        return word;

    }
}
