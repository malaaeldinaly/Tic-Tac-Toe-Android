package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    // 0:empty, 1:yellow, 2:red
    int[] cellState = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    int activePlayer = 1;
    int[][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    boolean gameActive = true;

    //Selecting a cell (counter)
    @SuppressLint("SetTextI18n")
    public void dropIn(View view){
            ImageView counter = (ImageView) view;
            int tappedCounter =  Integer.parseInt(counter.getTag().toString());

            if (gameActive){
                if(cellState[tappedCounter]==0) {               //cell is empty
                    cellState [tappedCounter]=activePlayer;     //Assigning player to the cell
                    if (activePlayer == 1) {
                        counter.setImageResource(R.drawable.yellow);
                        TextView winnerTextView = findViewById(R.id.winnerTextView);
                        winnerTextView.setVisibility(View.VISIBLE);
                        winnerTextView.setText("RED's turn");
                        activePlayer = 2;
                    }else {
                        counter.setImageResource(R.drawable.red);
                        TextView winnerTextView = findViewById(R.id.winnerTextView);
                        winnerTextView.setText("YELLOWS's turn");
                        winnerTextView.setVisibility(View.VISIBLE);
                        activePlayer = 1;
                    }
                    //Drawing
                    counter.setTranslationY(-1500);
                    counter.animate().rotation(360).translationYBy(1500).setDuration(500);
                    winnerDetector(winningPositions, cellState);
                }else{
                    Toast.makeText(this, "Choose empty cell", Toast.LENGTH_SHORT).show();
                }
            }
            if (occupied(cellState)){
                gameActive=false;
                gameOver("DRAW");
            }
    }

    public void winnerDetector(int[][] winningPositions, int[] cellState){
        for (int[] winningPosition:winningPositions){
            if (cellState[winningPosition[0]] == cellState[winningPosition[1]] && cellState[winningPosition[0]] == cellState[winningPosition[2]] && cellState[winningPosition[0]]!=0)
            {
                gameActive=false;
                String winner;
                if (cellState[winningPosition[0]]==1)
                    winner = "YELLOW";
                else
                    winner = "RED";
                gameOver(winner);
            }
        }
    }

    public void gameOver(String winner){
        TextView winnerTextView = findViewById(R.id.winnerTextView);
        winner += " is the winner";
        winnerTextView.setText(winner);
        winnerTextView.setVisibility(View.VISIBLE);
        Button playAgainButton = findViewById(R.id.playAgainButton);
        playAgainButton.setVisibility(View.VISIBLE);
    }

    public boolean occupied(int[] cellState){
        boolean occupied = true;
        for (int counter : cellState) {
            if (counter == 0) {
                occupied = false;
                break;
            }
        }
        return occupied;
    }

    public void playAgain(View view){
        Button playAgainButton = findViewById(R.id.playAgainButton);
        TextView winnerTextView = findViewById(R.id.winnerTextView);
        playAgainButton.setVisibility(View.INVISIBLE);
        winnerTextView.setVisibility(View.INVISIBLE);
        androidx.gridlayout.widget.GridLayout gridLayout = findViewById(R.id.gridLayout);
        for (int i=0;i<gridLayout.getChildCount();i++){
            ImageView counter = (ImageView) gridLayout.getChildAt(i);
            counter.setImageDrawable(null);
        }
        Arrays.fill(cellState, 0);
        activePlayer = 2;
        gameActive = true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
