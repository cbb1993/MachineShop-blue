package com.huanhong.mashineshop;

import android.app.Application;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;

import java.io.IOException;

import controller.VendApplication;

public class AppApplication extends VendApplication {
    private static final String TAG = "AppApplication";
    public static AppApplication mInstance;
    public static MediaPlayer mediaPlayer;
    private static AudioManager mAudioManager ;
    private static int currentVolume ;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initPushService(this);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    public static void resetVolume(){
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 1);
    }

    public static void addVolume(){
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 1);
    }


    public static AppApplication instance() {
        return mInstance;
    }

    /**
     * 初始化云推送通道
     * @param applicationContext
     */private void initPushService(final Context applicationContext) {
        PushServiceFactory.init(applicationContext);
        final CloudPushService pushService = PushServiceFactory.getCloudPushService();
        pushService.register(applicationContext, new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                Log.i(TAG, "init cloudchannel success");
                //setConsoleText("init cloudchannel success");
            }
            @Override
            public void onFailed(String errorCode, String errorMessage) {

                Log.e(TAG, "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
                //setConsoleText("init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
            }
        });
    }
}
