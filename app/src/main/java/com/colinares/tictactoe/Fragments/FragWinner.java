package com.colinares.tictactoe.Fragments;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.colinares.tictactoe.Adapters.WinnerAdapter;
import com.colinares.tictactoe.Model.Winner;
import com.colinares.tictactoe.R;
import com.colinares.tictactoe.Utils.SpaceDecorator;
import com.colinares.tictactoe.Utils.ThemeUtils;
import com.colinares.tictactoe.ViewModel.WinnerViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Colinares on 9/14/2018.
 */

public class FragWinner extends Fragment {

    @BindView(R.id.winner_recycler)
    RecyclerView winnerRecycler;
    Unbinder unbinder;

    public FragWinner() {
    }

    private View mView;

    private ThemeUtils themeUtils;

    private WinnerViewModel mWinnerViewModel;
    private WinnerAdapter winnerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.frag_layout_scores, null, false);

        themeUtils = new ThemeUtils(getContext());
        unbinder = ButterKnife.bind(this, mView);

        winnerRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        // create new notes adapter
        winnerAdapter = new WinnerAdapter();
        // set adapter to recyclerview
        winnerRecycler.setAdapter(winnerAdapter);
        // add decoration to recyclerview
        winnerRecycler.addItemDecoration(new SpaceDecorator(20));
        // get ViewModel of this activity using ViewModelProviders
        mWinnerViewModel = ViewModelProviders.of(this).get(WinnerViewModel.class);

        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (themeUtils.loadNightMode()) {
            getActivity().setTheme(R.style.DarkTheme);
        } else {
            getActivity().setTheme(R.style.AppTheme);
        }

        // observe for winner data changes
        mWinnerViewModel.getmAllWinners().observe(this, new Observer<List<Winner>>() {
            @Override
            public void onChanged(@Nullable List<Winner> winners) {
                //add winners to adapter
                winnerAdapter.addWinner(winners);
            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
