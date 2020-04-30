package com.colinares.tictactoe.Adapters;

import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.colinares.tictactoe.Model.Winner;
import com.colinares.tictactoe.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Colinares on 9/15/2018.
 */

public class WinnerAdapter extends RecyclerView.Adapter{

    //Create list of winners
    List<Winner> winners = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Get layout inflater
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        //Inflate layout
        View row = inflater.inflate(R.layout.custom_winner_item, parent, false);
        //return notes holder and pass row inside
        return new WinnerHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //Get current winner
        Winner currentWinner = winners.get(position);
        //cast winner holder
        WinnerHolder winnerHolder = (WinnerHolder) holder;
        //set message description and created at
        winnerHolder.mNoteTitle.setText(currentWinner.getContent());
        winnerHolder.createdAt.setText(currentWinner.getTimeCreated() + " " + currentWinner.getDateCreated());
        //create random color and set it
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        winnerHolder.backStrip.setBackgroundColor(color);
    }

    @Override
    public int getItemCount() {
        return winners.size();
    }

    public class WinnerHolder extends RecyclerView.ViewHolder {
        TextView mNoteTitle, createdAt;
        FrameLayout backStrip;

        public WinnerHolder(View itemView) {
            super(itemView);
            mNoteTitle = itemView.findViewById(R.id.message_title);
            createdAt = itemView.findViewById(R.id.createdAt);
            backStrip = itemView.findViewById(R.id.backStrip);
        }
    }

    public void addWinner(List<Winner> winners) {
        this.winners = winners;
        notifyDataSetChanged();
    }


}
