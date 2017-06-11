package com.example.pvtruong.appenglishlock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    Switch aSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resquestPreMission();
        initview();
        initListener();



    }

    private void resquestPreMission() {
        // dành cho android API 23 trở lên
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // nếu chưa được cấp quyền
            if (!Settings.canDrawOverlays(this)) {
                // xin quyền bằng cách bật giao diện
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                startActivityForResult(intent, 200);

            }
        } else {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    finish();

                }
            }
        }

    }

    private void initview() {
        aSwitch = (Switch) findViewById(R.id.on_off_switch);
        SharedPreferences pre = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        if(pre.getBoolean("status",false)==true){
            aSwitch.setChecked(true);
            Intent intent = new Intent(MainActivity.this, EnglockService.class);
            startService(intent);
        }
        else {
            aSwitch.setChecked(false);
        }
    }

    private void initListener() {
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // kích hoạt service gọi startComand
                    Intent intent = new Intent(MainActivity.this, EnglockService.class);
                    startService(intent);

                    SharedPreferences pre = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                    SharedPreferences.Editor editor = pre.edit();// tương đương cái bút ghi dữ liệu vào file Pre
                    editor.putBoolean("status",true);
                    editor.apply();

                } else {
                    // hủy  service
                    Intent intent = new Intent(MainActivity.this, EnglockService.class);
                    stopService(intent);

                    SharedPreferences pre = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                    SharedPreferences.Editor editor = pre.edit();// tương đương cái bút ghi dữ liệu vào file Pre
                    editor.putBoolean("status",false);
                    editor.apply();

                }
            }
        });
    }
}
