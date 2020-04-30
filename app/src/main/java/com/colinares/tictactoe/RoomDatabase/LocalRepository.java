package com.colinares.tictactoe.RoomDatabase;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;
import androidx.annotation.NonNull;

import com.colinares.tictactoe.Model.Winner;

import java.util.List;

/**
 * Created by Colinares on 9/15/2018.
 */

public class LocalRepository {

    //Live Data of List of all score
    private LiveData<List<Winner>> mAllWinners;
    //Define Local Dao
    LocalDao mLocalDao;

    public LocalRepository(@NonNull Application application){
        LocalDatabase localDatabase = LocalDatabase.getDatabase(application);

        //init Local Dao
        mLocalDao = localDatabase.localDao();
        //get all score
        mAllWinners = mLocalDao.getAllWinner();
    }

    //method to get all scores
    public LiveData<List<Winner>> getAllScore() {
        return mAllWinners;
    }

    //method to add score
    public void addWinner(Winner winner) {
        new AddWinner().execute(winner);
    }

    //Async task to add score
    public class AddWinner extends AsyncTask<Winner, Void, Void> {
        @Override
        protected Void doInBackground(Winner... winners) {
            mLocalDao.insertWinner(winners[0]);
            return null;
        }
    }


}
