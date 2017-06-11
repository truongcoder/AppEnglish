package com.example.pvtruong.appenglishlock;

import android.content.Context;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by PVTruong on 29/03/2017.
 */

public class Lock_ScreenView extends FrameLayout implements View.OnClickListener {
    // ánh xạ một xml thành 1 view
    private TextView txt_en, txt_vn1, txt_vn2;
    private LayoutInflater inflater;
    private XuLyDatabase database;
    private Random random;
    private Word[] words;
    private Handler handler;
    private Spinner spinner;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;
    private Vibrator vibrator;// đối tượng rung
    public Lock_ScreenView(Context context, Handler handler) {
        super(context);
        inflater = LayoutInflater.from(context);

        this.handler=handler;
        inflater.inflate(R.layout.view_lock_screen, this);
        txt_en = (TextView) findViewById(R.id.txt_en);
        txt_vn1 = (TextView) findViewById(R.id.txt_vn1);
        txt_vn2 = (TextView) findViewById(R.id.txt_vn2);
        spinner= (Spinner) findViewById(R.id.spinner);
        txt_vn1.setOnClickListener(this);
        txt_vn2.setOnClickListener(this);
        database = new XuLyDatabase(context);

        random = new Random();

        vibrator= (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        arrayList=new ArrayList<>();
        arrayList.add("Music");
        arrayList.add("Sport");
        bindData();
        adapter=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,arrayList);
        spinner.setAdapter(adapter);
       // String item= (String) spinner.getSelectedItem();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                   bindData();
                }else {
                    bindDataSport();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




    }

    public void bindData() {
        words = database.getRandomTwoWordMusic();
        txt_en.setText(words[0].getEn());
        if (random.nextInt(100) < 50) {
            txt_vn1.setText(words[0].getVn());
            txt_vn2.setText(words[1].getVn());
        } else {
            txt_vn1.setText(words[1].getVn());
            txt_vn2.setText(words[0].getVn());
        }
    }

   public void bindDataSport() {
        words = database.getRandomTwoWordSport();
        txt_en.setText(words[0].getEn());
        if (random.nextInt(100) < 50) {
            txt_vn1.setText(words[0].getVn());
            txt_vn2.setText(words[1].getVn());
        } else {
            txt_vn1.setText(words[1].getVn());
            txt_vn2.setText(words[0].getVn());
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_vn1:
                if (txt_vn1.getText().equals(words[0].getVn())) {
                    // mở khóa
                    openLockScreeen();
                } else {
                    vibrator.vibrate(200);
                   // vibrator.vibrate(new long[] {0 , 200 , 100},1); rung lặp lại
                    bindData();
                }
                break;
            case R.id.txt_vn2:
                if (txt_vn2.getText().equals(words[0].getVn())) {
                    // mở khóa
                    vibrator.vibrate(200);
                    openLockScreeen();
                } else {
                    vibrator.vibrate(new long[] {0 , 200 , 100},1);
                    bindData();
                }
                break;
        }
    }
    private void openLockScreeen(){
        handler.sendEmptyMessage(100);
    }
}
