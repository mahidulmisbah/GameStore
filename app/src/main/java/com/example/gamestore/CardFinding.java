package com.example.gamestore;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardFinding extends AppCompatActivity {

    //**************Variable From Layout View *********************************************************************
    DatabaseHelperActivity mydatabase;
    Animation bounceAnimation;
    Button startGame,retry,done;
    ImageView hate,love,tryAgain,neutral,cover;
    TextView select,playerName,highScore,average,gameName,score,gameCount,personalBest;

    //**************Variable For Process Completion *********************************************************************
    private String lid,gid,boardPlayerName,boardGameName;
    private int first_selection,flagFinal,second_selection,resetAll,d,r,s,scoreCount;
    private int boardGameCount,boardHighScore,boardPersonalBest,boardTotalScore;
    private double boardAverageScore;
    String strDouble;
    List<Integer> image ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_finding);

        mydatabase = new DatabaseHelperActivity(this);
        SQLiteDatabase sqLiteDatabase = mydatabase.getWritableDatabase();


        flagFinal = -3;
        first_selection = -1;
        second_selection = -2;
        resetAll = 0;
        r = 0;
        s = 0;
        boardTotalScore = 0;
        scoreCount = 0;
        boardGameCount = 0;
        image = new ArrayList<>();

        image.add(0);   // hate
        image.add(1);   // love
        image.add(2);   // neutral
        image.add(3);   // try

        // ********************************Animation portion*************************************
        bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce);


        //********************************Declaration Portion************************************
        playerName = (TextView)findViewById(R.id.player_name);
        gameName = (TextView)findViewById(R.id.current_game);
        score = (TextView)findViewById(R.id.score);
        average = (TextView)findViewById(R.id.overall_score);
        highScore = (TextView)findViewById(R.id.game_best);
        personalBest = (TextView)findViewById(R.id.personal_best);
        gameCount = (TextView)findViewById(R.id.game_count);

        hate = (ImageView) findViewById(R.id.ishka);
        love = (ImageView) findViewById(R.id.love);
        neutral = (ImageView) findViewById(R.id.dice);
        tryAgain = (ImageView) findViewById(R.id.clubs);
        cover = (ImageView) findViewById(R.id.cover);
        select = (TextView) findViewById(R.id.select);

//****************************************** Score board setup**********************************************************************
        Intent cardfinding = getIntent();
        lid = cardfinding.getStringExtra("LID");
        gid = cardfinding.getStringExtra("GID");
        Cursor gameBoardData = mydatabase.readFromLoginJoinGameJoinProgress(lid,gid);
        Log.d("man",lid+" "+gid);
        while(gameBoardData.moveToNext()) {
            Log.d("logValue","Data");
            boardPlayerName = gameBoardData.getString(0);
            boardGameName = gameBoardData.getString(2);
            boardPersonalBest = gameBoardData.getInt(3);
            boardAverageScore = gameBoardData.getDouble(4);
            boardHighScore = gameBoardData.getInt(5);
            boardTotalScore = gameBoardData.getInt(6);
            boardGameCount = gameBoardData.getInt(7);
        }

        //***************set previous value*******************************************************************************
        playerName.setText(boardPlayerName);
        gameName.setText(boardGameName);
        highScore.setText( "Game Best Score : " + boardHighScore);
        personalBest.setText("Personal Best: "+boardPersonalBest);
        boardAverageScore = (boardTotalScore*100.0)/boardGameCount;
        strDouble = String.format("%.2f", boardAverageScore);
        average.setText("Winning Rate: "+strDouble+"%");
        gameCount.setText("Game Count : "+boardGameCount);


        Log.d("logValue", "retrieve: "+lid+" "+gid+" "+boardPlayerName + " " + boardGameName + " HighScore: " + boardPersonalBest + " AvSc:" + boardAverageScore + " total:" + boardTotalScore+" GameMx"+boardHighScore+" "+boardGameCount);








        hate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if (flagFinal < 0) {

                    hate.startAnimation(bounceAnimation);
                    love.clearAnimation();
                    neutral.clearAnimation();
                    tryAgain.clearAnimation();


                    first_selection = 0;
                    cover.setImageResource(R.drawable.ishka);
                }

                if (flagFinal >= 0 && s + d + r == 0) {
                    hate.startAnimation(bounceAnimation);
                    love.clearAnimation();
                    neutral.clearAnimation();
                    tryAgain.clearAnimation();

                    second_selection = image.get(0);

                    if (image.get(0) == 0) hate.setImageResource(R.drawable.ishka);
                    else if (image.get(0) == 1) hate.setImageResource(R.drawable.love);
                    else if (image.get(0) == 2) hate.setImageResource(R.drawable.dice);
                    else if (image.get(0) == 3) hate.setImageResource(R.drawable.clubs);
                    reStartGame();
                }
            }
        });


        love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (flagFinal < 0) {
                    love.startAnimation(bounceAnimation);
                    hate.clearAnimation();
                    neutral.clearAnimation();
                    tryAgain.clearAnimation();
                    cover.setImageResource(R.drawable.love);
                    first_selection = 1;
                }
                if (flagFinal >= 0 && s + d + r == 0) {
                    love.startAnimation(bounceAnimation);
                    hate.clearAnimation();
                    neutral.clearAnimation();
                    tryAgain.clearAnimation();
                    second_selection = image.get(1);
                    if (image.get(1) == 0) love.setImageResource(R.drawable.ishka);
                    else if (image.get(1) == 1) love.setImageResource(R.drawable.love);
                    else if (image.get(1) == 2) love.setImageResource(R.drawable.dice);
                    else if (image.get(1) == 3) love.setImageResource(R.drawable.clubs);
                    reStartGame();
                }
            }
        });

        neutral.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if (flagFinal < 0) {
                    neutral.startAnimation(bounceAnimation);
                    hate.clearAnimation();
                    love.clearAnimation();
                    tryAgain.clearAnimation();
                    first_selection = 2;
                    cover.setImageResource(R.drawable.dice);
                }
                if (flagFinal >= 0 && s + d + r == 0) {
                    neutral.startAnimation(bounceAnimation);
                    hate.clearAnimation();
                    love.clearAnimation();
                    tryAgain.clearAnimation();
                    second_selection = image.get(2);
                    if (image.get(2) == 0) neutral.setImageResource(R.drawable.ishka);
                    else if (image.get(2) == 1) neutral.setImageResource(R.drawable.love);
                    else if (image.get(2) == 2) neutral.setImageResource(R.drawable.dice);
                    else if (image.get(2) == 3) neutral.setImageResource(R.drawable.clubs);
                    reStartGame();
                }
            }
        });


        tryAgain.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if (flagFinal < 0) {
                    tryAgain.startAnimation(bounceAnimation);
                    hate.clearAnimation();
                    love.clearAnimation();
                    neutral.clearAnimation();
                    first_selection = 3;
                    cover.setImageResource(R.drawable.clubs);
                }

                if (flagFinal >= 0 && s + d + r == 0) {
                    tryAgain.startAnimation(bounceAnimation);
                    hate.clearAnimation();
                    love.clearAnimation();
                    neutral.clearAnimation();

                    second_selection = image.get(3);
                    Log.d("card", "try: " + d + " " + s + " " + r);
                    if (image.get(3) == 0) tryAgain.setImageResource(R.drawable.ishka);
                    else if (image.get(3) == 1) tryAgain.setImageResource(R.drawable.love);
                    else if (image.get(3) == 2) tryAgain.setImageResource(R.drawable.dice);
                    else if (image.get(3) == 3) tryAgain.setImageResource(R.drawable.clubs);
                    reStartGame();
                }
            }

        });


        done = (Button) findViewById(R.id.done);
        startGame = (Button) findViewById(R.id.startGame);
        retry = (Button) findViewById(R.id.retry);

        done.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (first_selection < 0) {
                    Toast.makeText(CardFinding.this, "Please select a card first", Toast.LENGTH_LONG).show();
                } else {
                    startGame.setVisibility(Button.VISIBLE);
                    s = 1;
                    done.setVisibility(Button.INVISIBLE);
                    d = 0;
                    retry.setVisibility(Button.INVISIBLE);
                    r = 0;
                    select.setText("Now Press The Start Button");
                    flagFinal = first_selection;
                    boardGameCount++;
                    gameCount.setText("Game Count : "+boardGameCount);
                }
            }
        });


        startGame.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame.setVisibility(Button.INVISIBLE);
                s = 0;
                done.setVisibility(Button.INVISIBLE);
                d = 0;
                select.setText(R.string.find_card);

                hate.setImageResource(R.drawable.cover);
                love.setImageResource(R.drawable.cover);
                tryAgain.setImageResource(R.drawable.cover);
                neutral.setImageResource(R.drawable.cover);
                Collections.shuffle(image);    // shuffling those card;
            }
        });


        retry.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                done.setVisibility(Button.VISIBLE);
                d = 1;
                retry.setVisibility(Button.INVISIBLE);
                r = 0;
                startGame.setVisibility(Button.INVISIBLE);
                s = 0;
                select.setText(R.string.select);
                resetAll = 1;
                resetAllData();
            }
        });




    }

    private void reStartGame()
    {
        if(flagFinal == second_selection )
        {
            resetAll = 0;
            first_selection = -1;
            second_selection = -2;
            retry.setVisibility(Button.VISIBLE);
            r = 1;
            select.setText( "Congratulations");
            scoreCount++;
            boardTotalScore++;
            score.setText("Current Score: "+scoreCount);
        }
        else if( second_selection >= 0 )
        {
            resetAll = 0;
            first_selection = -1;
            second_selection = -2;
            retry.setVisibility(Button.VISIBLE);
            r = 1;
            select.setText("Mistake!");
        }
        boardAverageScore = (boardTotalScore*100.0)/boardGameCount;
        strDouble = String.format("%.2f", boardAverageScore);
        average.setText("Winning Rate: "+strDouble+"%");
    }

    private void resetAllData()
    {
        if(resetAll == 1)
        {
            image.clear();
            image.add(0);   // hate
            image.add(1);   // love
            image.add(2);   // neutral
            image.add(3);   // try
            flagFinal = -3;
            resetAll = 0;
            cover.setImageResource(R.drawable.cover);
            hate.setImageResource(R.drawable.ishka);
            love.setImageResource(R.drawable.love);
            neutral.setImageResource(R.drawable.dice);
            tryAgain.setImageResource(R.drawable.clubs);
        }
    }


    public void onBackPressed() {
        AlertDialog.Builder display_back = new AlertDialog.Builder(CardFinding.this);
        display_back.setIcon(R.drawable.logout);
        display_back.setCancelable(false);
        display_back.setTitle("Sign Out Alert");
        display_back.setMessage("Do you want to exit from game?");
        display_back.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(boardPersonalBest<scoreCount)
                {
                    boardPersonalBest = scoreCount;
                }
                boardAverageScore = (boardTotalScore*100.0)/boardGameCount;
                strDouble = String.format("%.2f", boardAverageScore);
                boardAverageScore = Double.parseDouble(strDouble);
                mydatabase.updateProgressTable(lid,gid,boardAverageScore,boardPersonalBest,boardTotalScore,boardGameCount);
                if(boardHighScore<scoreCount)
                {
                    boardHighScore = scoreCount;
                }
                mydatabase.updateGameTable(gid,boardGameName,boardHighScore);
                Log.d("logValue", "update: "+lid+" "+gid+" "+boardPlayerName + " " + boardGameName + " HighScore: " + boardPersonalBest + " AvSc:" + boardAverageScore + " total:" + boardTotalScore+" GameMx"+boardHighScore+" "+boardGameCount);

                finish();

            }
        });

        display_back.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        display_back.create();
        display_back.show();
    }
}