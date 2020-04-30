package com.colinares.tictactoe.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.colinares.tictactoe.Model.Winner;
import com.colinares.tictactoe.RoomDatabase.LocalRepository;

import java.util.List;

/**
 * Created by Colinares on 9/15/2018.
 */

public class WinnerViewModel extends AndroidViewModel {

    private LiveData<List<Winner>> mAllWinners;
    LocalRepository mLocalRepository;


    public WinnerViewModel(@NonNull Application application) {
        super(application);
        mLocalRepository = new LocalRepository(application);
        mAllWinners = mLocalRepository.getAllScore();
    }

    public LiveData<List<Winner>> getmAllWinners() {
        return mAllWinners;
    }

    public void addWinner(Winner winner) {
        mLocalRepository.addWinner(winner);
    }

}
