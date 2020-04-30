package com.colinares.tictactoe.RoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.colinares.tictactoe.Constants.AppConstants;
import com.colinares.tictactoe.Model.Winner;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

/**
 * Created by Colinares on 9/15/2018.
 */

@Dao
public interface LocalDao {
    // Dao method to get all score
    @Query("SELECT * FROM " + AppConstants.TBL_WINNER + " ORDER BY id DESC")
    LiveData<List<Winner>> getAllWinner();

    // Dao method to insert score
    @Insert(onConflict = REPLACE)
    void insertWinner(Winner winner);

}
