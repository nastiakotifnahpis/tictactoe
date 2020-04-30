package com.colinares.tictactoe.Utils;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import androidx.annotation.Nullable;

import com.colinares.tictactoe.R;

/**
 * Created by Colinares on 9/15/2018.
 */

public class MusicService extends Service {

    static MediaPlayer mPlayer;

    public MusicService() {
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mPlayer = MediaPlayer.create(this, R.raw.main_music);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mPlayer.start();
        mPlayer.setLooping(true);
        //isPlaying = true;
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPlayer.isPlaying()){
            mPlayer.stop();
        }
    }

    public static boolean isMusicPlaying(){
        return mPlayer.isPlaying();
    }

}
