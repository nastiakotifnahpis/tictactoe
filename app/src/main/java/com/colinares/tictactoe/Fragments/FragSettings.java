package com.colinares.tictactoe.Fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatDelegate;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.Switch;

import com.colinares.tictactoe.Activities.MainActivity;
import com.colinares.tictactoe.R;
import com.colinares.tictactoe.Utils.MusicController;
import com.colinares.tictactoe.Utils.ThemeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Colinares on 9/14/2018.
 */

public class FragSettings extends Fragment {

    @BindView(R.id.settings_music)
    Switch settingsMusic;
    @BindView(R.id.settings_theme)
    Switch settingsTheme;
    @BindView(R.id.settings_help)
    ImageButton settingsHelp;
    Unbinder unbinder;

    public FragSettings() {
    }

    private View mView;

    private ThemeUtils themeUtils;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.frag_layout_settings, null, false);
        unbinder = ButterKnife.bind(this, mView);

        themeUtils = new ThemeUtils(getContext());
        if (themeUtils.loadNightMode()){
            settingsTheme.setChecked(true);
        }

        if(MusicController.isMusicPlaying()){
            settingsMusic.setChecked(true);
        }

        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        settingsMusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    MusicController.startPlayMusic(getContext());
                }else {
                    MusicController.stopPlayMusic(getContext());
                }
            }
        });

        settingsTheme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    themeUtils.setNightMode(true);
                    restartApp();
                }else {
                    themeUtils.setNightMode(false);
                    restartApp();
                }
            }
        });

    }

    @OnClick(R.id.settings_help)
    public void setViewOnClick(View v){
        switch (v.getId()){
            case R.id.settings_help:
                showHelp();
                break;
        }
    }

    private void showHelp() {
        final View viewPopup = getLayoutInflater().inflate(R.layout.help_message, null);

        final Button btnOk = viewPopup.findViewById(R.id.settings_help_ok);

        final PopupWindow popupWindow = new PopupWindow(viewPopup, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.setAnimationStyle(R.style.popup_window_animation_slide);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();
        popupWindow.showAtLocation(settingsHelp, Gravity.CENTER, 0, 0);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

    }

    private void restartApp(){
        startActivity(new Intent(getActivity(), MainActivity.class));
        getActivity().finish();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
