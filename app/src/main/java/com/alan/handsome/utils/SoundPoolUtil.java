package com.alan.handsome.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.text.TextUtils;

import java.io.IOException;

public class SoundPoolUtil {
    private static SoundPool sp;
    private static volatile SoundPoolUtil soundPoolUtils;
    private SoundPoolUtil(){
        sp = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
    }

    public static int soundId = -1;
    public static int soundId2 = -1;

    public static SoundPoolUtil getInstacnde(){
        if (soundPoolUtils==null){
            synchronized (SoundPoolUtil.class){
                soundPoolUtils = new SoundPoolUtil();
            }
        }
        return soundPoolUtils;
    }

    public  void loadMusic(Context context, String fileName) {
        if (TextUtils.isEmpty(fileName)){
            throw new NullPointerException("res is null");
        }
        try {
            soundId = sp.load(context.getAssets().openFd(fileName), 1);
            //return soundId;
        } catch (IOException e) {
            e.printStackTrace();
            soundId = -1;
        }
        //return -1;
    }
    public void playMusic(int id){
        soundId2 = playMusic(id,-1);
    }
    public int playMusic(int id, int loop){
        return sp.play(id, 0.8f, 0.8f,1, 0, 1.0f);
    }

    public void setRate(float rate) {
        if (rate >= 0)
            sp.setRate(soundId2, rate);
    }

    public void stopMusic(int id){
        sp.stop(id);
    }

}
