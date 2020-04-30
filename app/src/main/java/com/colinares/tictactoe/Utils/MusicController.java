package com.colinares.tictactoe.Utils;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Colinares on 9/15/2018.
 */

public class MusicController {

    //start music
    public static void startPlayMusic(Context context) {
        Intent i = new Intent(context, MusicService.class);
        context.startService(i);
    }

    //stop music
    public static void stopPlayMusic(Context context) {
        Intent i = new Intent(context, MusicService.class);
        context.stopService(i);
    }

    //
    public static boolean isMusicPlaying(){
        return MusicService.isMusicPlaying();
    }


}
