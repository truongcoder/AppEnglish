package com.example.pvtruong.appenglishlock;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * Created by PVTruong on 29/03/2017.
 */

public class EnglockService extends Service {
    // khai báo thuộc tính
    private ScreenStateReceiver screenStateReceiver;
    private WindowManager windowManager;
    private Lock_ScreenView lock_screenView;
    // truyền và nhận tin nhắn
    private Handler handler;
    private boolean isLockScreeenAdded;
    @Override
    public void onCreate() {
        super.onCreate();
        registerScreenStateReceiver();
        disableKeyguar();
        handler=new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==100){
                    hideLockScreenView();
                }

            }
        };
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // phương thức tự động gọi
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY; // chạy liên tục
    }

    @Override
    public void onDestroy() {
        unregisterScreenStateReceiver();
        enableKeyguar();
        super.onDestroy();
    }

    private void showLockScreenView() {
        if(isLockScreeenAdded){
            lock_screenView.bindData();
            lock_screenView.bindDataSport();
            return;
        }

        lock_screenView = new Lock_ScreenView(this,handler);

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);// đối tượng trong hệ thống k dùng new để lấy đối tượng
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.gravity= Gravity.CENTER;
        layoutParams.type=WindowManager.LayoutParams.TYPE_PHONE;
        layoutParams.format= PixelFormat.TRANSPARENT ; // trong suất để hiển thị
        windowManager.addView(lock_screenView, layoutParams);
        isLockScreeenAdded=true;

    }
    // Mở khóa
    private void hideLockScreenView(){
        if(isLockScreeenAdded) {
            windowManager.removeView(lock_screenView);
            isLockScreeenAdded=false;
        }
    }

    private void disableKeyguar(){
        // hủy bảo vệ ta mới có thể hiển thị được customview
            KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
            KeyguardManager.KeyguardLock lock = keyguardManager.newKeyguardLock("IN");
            lock.disableKeyguard();


    }
    private void enableKeyguar(){
        // hủy bảo vệ ta mới có thể hiển thị được customview
        KeyguardManager keyguardManager= (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock lock=keyguardManager.newKeyguardLock("IN");
        lock.reenableKeyguard();

    }
    // lắng nghe các sự kiện được phát ra bời hệ thống ( tắt màn hình)
    private class ScreenStateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // đoạn code bên dưới chạy khi màn hình tắt
            // hiển thị UI câu hỏi
            showLockScreenView();

        }
    }

    private void registerScreenStateReceiver() {
        // B1 : khởi tạo đối tượng
        screenStateReceiver = new ScreenStateReceiver();
        // B2 : tạo bộ lọc
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);// gọi Action
        //  filter.addAction(Intent.ACTION_HEADSET_PLUG);// tai nghe

        // B3 : đăng kí
        registerReceiver(screenStateReceiver, filter);


    }

    private void unregisterScreenStateReceiver() {
        // TODO :
        unregisterReceiver(screenStateReceiver);
    }
}
