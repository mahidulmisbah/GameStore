package com.example.gamestore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class GameOptionActivity extends AppCompatActivity {

    Button cardFinding,dart,dxBall,snake,caram;
    private String name,lid;
     Animation bounceAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_option);

        bounceAnimation = AnimationUtils.loadAnimation(this,R.anim.bounce);

        //**************************Game Option initialization********************************************************
        cardFinding = (Button)findViewById(R.id.card_finding);
        dart = (Button)findViewById(R.id.dart);
        dxBall = (Button)findViewById(R.id.dx_ball);
        snake = (Button)findViewById(R.id.snake);
        caram = (Button)findViewById(R.id.caram);

        // onclick option **********************************************************************************************

        cardFinding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardFinding.startAnimation(bounceAnimation);
                Intent loginInfo = getIntent();
                name = loginInfo.getStringExtra("Name");
                lid = loginInfo.getStringExtra("LID");
                Log.d("logValue", "option: "+ " "+name);

                Intent cardFindingIntent = new Intent(GameOptionActivity.this,CardFinding.class);
                cardFindingIntent.putExtra("GID","G001");
                cardFindingIntent.putExtra("LID",lid);
                cardFindingIntent.putExtra("Name",name);
                startActivity(cardFindingIntent);
            }
        });
    }
}