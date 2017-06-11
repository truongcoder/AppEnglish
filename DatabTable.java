package com.example.pvtruong.appenglishlock;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by PVTruong on 05/04/2017.
 */

public class DatabTable extends SQLiteOpenHelper {
    private final static  String database_Name="dulieusqlite.sqlite";
    Context context;
    String duongDanDatabase = "";
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public DatabTable(Context context) {
        super(context, database_Name, null, 1);
        this.context = context;
        duongDanDatabase = context.getFilesDir().getParent() + "/databases/" + database_Name;
    }



    public SQLiteDatabase openDatabase(){
        return SQLiteDatabase.openDatabase(duongDanDatabase, null, SQLiteDatabase.OPEN_READWRITE);

    }

    public void copyDatabase(){
        try {
            InputStream is =  context.getAssets().open(database_Name);
            OutputStream os = new FileOutputStream(duongDanDatabase);
            byte[] buffer = new byte[1024];
            int lenght = 0;
            while((lenght = is.read(buffer)) > 0){
                os.write(buffer, 0, lenght);
            }

            os.flush();
            os.close();
            is.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void createDatabase(){
        boolean kt = KiemTraDB();
        if(kt){
            Log.d("KetNoi", "Máy đã có database");
        }else{
            Log.d("KetNoi", "Máy chưa có database tiến hành copy dữ liệu");
            this.getWritableDatabase();
            copyDatabase();
        }
    }


    public boolean KiemTraDB(){
        SQLiteDatabase kiemTraDB = null;
        try{
            kiemTraDB = SQLiteDatabase.openDatabase(duongDanDatabase, null, SQLiteDatabase.OPEN_READONLY);
        }catch(Exception e){
            e.printStackTrace();
        }

        if(kiemTraDB !=null){
            kiemTraDB.close();
        }
        return kiemTraDB !=null ? true : false;
    }

}

