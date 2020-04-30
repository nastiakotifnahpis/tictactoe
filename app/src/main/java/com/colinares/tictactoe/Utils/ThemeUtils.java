package com.colinares.tictactoe.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Colinares on 9/14/2018.
 */

public class ThemeUtils {

    SharedPreferences sharedPref;

    public ThemeUtils(Context context){
        sharedPref = context.getSharedPreferences("Themes", Context.MODE_PRIVATE);
    }

    public void setNightMode(boolean state){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("Night", state);
        editor.commit();
    }

    public boolean loadNightMode(){
        boolean state = sharedPref.getBoolean("Night", false);
        return state;
    }

}
