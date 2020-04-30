package com.colinares.tictactoe.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colinares.tictactoe.Constants.AppConstants;
import com.colinares.tictactoe.Fragments.FragAbout;
import com.colinares.tictactoe.Fragments.FragGame;
import com.colinares.tictactoe.Fragments.FragWinner;
import com.colinares.tictactoe.Fragments.FragSettings;
import com.colinares.tictactoe.R;
import com.colinares.tictactoe.Utils.MusicController;
import com.colinares.tictactoe.Utils.ThemeUtils;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;

    @BindView(R.id.left_menu)
    Button leftMenu;
    @BindView(R.id.txt_fragment_title)
    TextView txtTitle;
    @BindView(R.id.layout_top)
    LinearLayout layoutTop;
    @BindView(R.id.main_container)
    FrameLayout mainContainer;

    private ResideMenu resideMenu;

    private ResideMenuItem itemGame;
    private ResideMenuItem itemScore;
    private ResideMenuItem itemSettings;
    private ResideMenuItem itemFeedback;
    private ResideMenuItem itemAbout;
    private ResideMenuItem itemExit;

    private ThemeUtils themeUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //MusicController.startPlayMusic(this);
        themeUtils = new ThemeUtils(this);
        if (themeUtils.loadNightMode()){
            setTheme(R.style.DarkTheme);
        }
        else setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = MainActivity.this;

        setUpMenu();

        if (savedInstanceState == null) {
            changeFragment(new FragGame());
        }
    }

    private void setUpMenu() {
        // attach to current activity;
        resideMenu = new ResideMenu(mContext);

        resideMenu.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        resideMenu.attachToActivity(this);
        //valid scale factor is between 0.0f and 1.0f. leftmenu width is 150dip.
        resideMenu.setScaleValue(0.6f);

        // create menu items.
        itemGame = new ResideMenuItem(mContext, R.drawable.ic_play, AppConstants.MENU_PLAY);
        itemScore = new ResideMenuItem(mContext, R.drawable.ic_score, AppConstants.MENU_SCORE);
        itemSettings = new ResideMenuItem(mContext, R.drawable.ic_settings, AppConstants.MENU_SETTINGS);
        itemAbout = new ResideMenuItem(mContext, R.drawable.ic_about, AppConstants.MENU_ABOUT);
        itemFeedback = new ResideMenuItem(mContext, R.drawable.ic_feedback, AppConstants.MENU_FEEDBACK);
        itemExit = new ResideMenuItem(mContext, R.drawable.ic_exit, AppConstants.MENU_EXIT);

        itemGame.setOnClickListener(this);
        itemScore.setOnClickListener(this);
        itemSettings.setOnClickListener(this);
        itemAbout.setOnClickListener(this);
        itemFeedback.setOnClickListener(this);
        itemExit.setOnClickListener(this);

        resideMenu.addMenuItem(itemGame, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemScore, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemSettings, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemAbout, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemFeedback, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemExit, ResideMenu.DIRECTION_LEFT);

        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

        leftMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    private void changeFragment(Fragment targetFragment) {
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    @Override
    public void onClick(View view) {
        if (view == itemGame) {
            txtTitle.setText(getResources().getString(R.string.app_name));
            changeFragment(new FragGame());
        } else if (view == itemScore) {
            txtTitle.setText(AppConstants.MENU_SCORE);
            changeFragment(new FragWinner());
        } else if (view == itemSettings) {
            txtTitle.setText(AppConstants.MENU_SETTINGS);
            changeFragment(new FragSettings());
        } else if (view == itemAbout) {
            txtTitle.setText(AppConstants.MENU_ABOUT);
            changeFragment(new FragAbout());
        } else if (view == itemExit) {
            showWarningMessage();
        }

        resideMenu.closeMenu();
    }


    @Override
    protected void onStart() {
        super.onStart();
        MusicController.startPlayMusic(mContext);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MusicController.stopPlayMusic(mContext);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MusicController.startPlayMusic(mContext);
    }

    /*@Override
    protected void onDestroy() {
        super.onDestroy();
        MusicController.stopPlayMusic(mContext);
    }*/

    public void onBackPressed() {
        showWarningMessage();

    }

    private void showWarningMessage() {
        final SweetAlertDialog sDialog = new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE);
        sDialog.setTitleText(getResources().getString(R.string.app_name));
        sDialog.setContentText("Are you sure you want to exit?");
        sDialog.setConfirmText("YES");
        sDialog.setCancelText("NO");
        sDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sDialog.dismissWithAnimation();
                finish();
            }
        });
        sDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sDialog.dismissWithAnimation();
            }
        });
        sDialog.show();
    }

}
