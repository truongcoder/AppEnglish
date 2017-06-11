package com.example.pvtruong.appenglishlock;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by PVTruong on 03/04/2017.
 */

public class Database {
    String path;
    private SQLiteDatabase sqLiteDatabase;

    public Database() {

    }

    // chuyển file dl từ assets vào bộ nhớ trong của ứng dụng
    public void copyDatabase(Context context) {
        // B1 : mở luồng để đọc dl
        try {
            // bước kiểm tra
            path = Environment.getDataDirectory().getAbsolutePath() + "/data/com.example.pvtruong.appenglishlock/dulieusqlite.sqlite";
            File file = new File(path);
            if (file.exists()) {
                return;
            }
            InputStream inputStream = context.getAssets().open("dulieusqlite.sqlite");
            // B2 : mở luồng ghi dữ liệu
            FileOutputStream outputStream = new FileOutputStream(file);

            //B3 đọc ghi
            byte[] b = new byte[1024];
            int length;
            while ((length = inputStream.read()) != -1) {

                outputStream.write(b, 0, length);
            }
            inputStream.close();
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void open() {
        sqLiteDatabase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
    }

    private void close() {
        sqLiteDatabase.close();
    }

    public Word[] getRandomTwoWord() {
        open();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Sport ORDER BY Random() LIMIT 2", null);
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
        cursor.close();
        close();
        return word;

    }
}
