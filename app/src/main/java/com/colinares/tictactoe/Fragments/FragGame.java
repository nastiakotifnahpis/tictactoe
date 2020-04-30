package com.colinares.tictactoe.Fragments;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.colinares.tictactoe.Model.Winner;
import com.colinares.tictactoe.R;
import com.colinares.tictactoe.Utils.DateTimeUtils;
import com.colinares.tictactoe.Utils.ThemeUtils;
import com.colinares.tictactoe.ViewModel.WinnerViewModel;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Colinares on 9/14/2018.
 */

public class FragGame extends Fragment implements View.OnClickListener {

    public FragGame() {}

    @BindView(R.id.player_1)
    TextView txtPlayer1;
    @BindView(R.id.player_2)
    TextView txtPlayer2;
    @BindView(R.id.btn_game_reset)
    Button btnReset;
    Unbinder unbinder;

    private WinnerViewModel mWinnerViewModel;

    private View mView;

    private ThemeUtils themeUtils;

    private int MAX_LENGTH = 3;

    private Button[][] mButtons = new Button[MAX_LENGTH][MAX_LENGTH];

    private boolean player1Turn = true;

    private int roundCount;

    private int player1points;
    private int player2points;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        themeUtils = new ThemeUtils(getContext());
        mView = inflater.inflate(R.layout.frag_layout_tictactoe, null, false);
        unbinder = ButterKnife.bind(this, mView);

        mWinnerViewModel = ViewModelProviders.of(this).get(WinnerViewModel.class);

        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setCurrentTheme();

        for (int i = 0; i < MAX_LENGTH; i++) {
            for (int j = 0; j < MAX_LENGTH; j++) {
                String btnId = "btn_" + i + j;
                int resID = getResources().getIdentifier(btnId, "id", getActivity().getPackageName());
                mButtons[i][j] = mView.findViewById(resID);
                mButtons[i][j].setOnClickListener(this);
            }
        }

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayResetWarning();
            }
        });
    }

    private void setCurrentTheme() {
        if (themeUtils.loadNightMode()) {
            getActivity().setTheme(R.style.DarkTheme);
        } else {
            getActivity().setTheme(R.style.AppTheme);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        if (!((Button) view).getText().toString().equals("")) {
            return;
        }

        if (player1Turn) {
            ((Button) view).setText("X");
        } else {
            ((Button) view).setText("O");
        }

        roundCount++;

        if (checkForWin()) {
            if (player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            player1Turn = !player1Turn;
        }
    }

    private boolean checkForWin() {
        String[][] field = new String[MAX_LENGTH][MAX_LENGTH];

        for (int i = 0; i < MAX_LENGTH; i++) {
            for (int j = 0; j < MAX_LENGTH; j++) {
                field[i][j] = mButtons[i][j].getText().toString();
            }
        }

        //horizontal
        for (int i = 0; i < MAX_LENGTH; i++) {
            if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].equals("")) {
                return true;
            }
        }

        //vertical
        for (int i = 0; i < MAX_LENGTH; i++) {
            if (field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals("")) {
                return true;
            }
        }

        //diagonal from top left to bottom right
        if (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals("")) {
            return true;
        }
        //diagonal from top right to bottom left
        if (field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals("")) {
            return true;
        }

        return false;
    }

    private void player1Wins() {
        player1points++;
        displayWinner("Player 1 Wins.");
        insertScore("Player 1 wins against Player 2");
        updatePointsText();
        resetBoard();
    }

    private void player2Wins() {
        player2points++;
        displayWinner("Player 2 Wins.");
        insertScore("Player 2 wins against Player 1");
        updatePointsText();
        resetBoard();
    }

    private void draw() {
        displayDrawMessage("The Game is Draw.");
        resetBoard();
    }

    private void resetBoard() {
        for (int i = 0; i < MAX_LENGTH; i++) {
            for (int j = 0; j < MAX_LENGTH; j++) {
                mButtons[i][j].setText("");
            }
        }

        roundCount = 0;
        player1Turn = true;
    }

    private void updatePointsText() {
        txtPlayer1.setText("Player 1: " + player1points);
        txtPlayer2.setText("Player 2: " + player2points);
    }

    private void resetGame() {
        player1points = 0;
        player2points = 0;
        updatePointsText();
        resetBoard();
    }

    private void insertScore(String message) {
        Winner winner = new Winner();
        winner.setContent(message);
        winner.setTimeCreated(DateTimeUtils.getTime());
        winner.setDateCreated(DateTimeUtils.getDate());

        mWinnerViewModel.addWinner(winner);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1points", player1points);
        outState.putInt("player2points", player2points);
        outState.putBoolean("player1Turn", player1Turn);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null){
            roundCount = savedInstanceState.getInt("roundCount");
            player1points = savedInstanceState.getInt("player1points");
            player2points = savedInstanceState.getInt("player2points");
            player1Turn = savedInstanceState.getBoolean("player1Turn");
        }

    }

    private void displayDrawMessage(String message){
        final SweetAlertDialog sDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.CUSTOM_IMAGE_TYPE);
        sDialog.setTitleText(getResources().getString(R.string.app_name));
        sDialog.setContentText(message);
        sDialog.setCustomImage(R.drawable.tic_tac_toe);
        sDialog.setCancelable(false);
        sDialog.show();
        sDialog.setConfirmText("OK");
        sDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sDialog.dismissWithAnimation();
            }
        });

    }
    private void displayWinner(String message){
        final SweetAlertDialog sDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE);
        sDialog.setTitleText(getResources().getString(R.string.app_name));
        sDialog.setContentText(message);
        sDialog.setCancelable(false);
        sDialog.show();
        sDialog.setConfirmText("OK");
        sDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sDialog.dismissWithAnimation();
            }
        });

    }
    private void displayResetWarning(){
        final SweetAlertDialog sDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE);
        sDialog.setTitleText(getResources().getString(R.string.app_name));
        sDialog.setContentText("Are you sure you want to reset?");
        sDialog.setCancelable(false);
        sDialog.show();
        sDialog.setConfirmText("YES");
        sDialog.setCancelText("NO");
        sDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sDialog.dismissWithAnimation();
                resetGame();
            }
        });
        sDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sDialog.dismissWithAnimation();
            }
        });
    }

}
