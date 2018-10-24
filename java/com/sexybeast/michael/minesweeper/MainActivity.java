package com.sexybeast.michael.minesweeper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.sexybeast.michael.minesweeper.view.MinesweeperView;

import static com.sexybeast.michael.minesweeper.R.raw.bombsound;

public class MainActivity extends AppCompatActivity {
    private static ToggleButton toggleFlag;
    private static TextView textView;
    private static MediaPlayer sound;
    private static ConstraintLayout constraintLayout;

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.context = getApplicationContext();
        setContentView(R.layout.activity_main);

        final MinesweeperView gameView = (MinesweeperView) findViewById(R.id.gameView);

        final Button btnReset = findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                gameView.resetGame();
            }
        });

        constraintLayout = (ConstraintLayout) findViewById(R.id.activity_main);

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.transfixed);
        final Button btnSound = findViewById(R.id.btnSound);

        btnSound.setOnClickListener(new View.OnClickListener() {
            boolean play = false;

            public void onClick(View v) {
                if (play == false) {
                    mp.setLooping(true);
                    mp.start();
                    play = true;
                } else if (play == true) {
                    mp.pause();
                    play = false;
                }
            }
        });

        toggleFlag = (ToggleButton) findViewById(R.id.toggleFlag);
        toggleFlag.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                gameView.setMode(toggleFlag.isChecked());
            }
        });

        textView = (TextView) findViewById(R.id.textView);
    }

    public static ToggleButton getToggleFlag() {
        return toggleFlag;
    }

    public static void setText(String text, int color) {
        textView.setAllCaps(true);
        textView.setText(text);
        textView.setTextColor(color);
    }

    public static void playBombSound(boolean p){
        sound = MediaPlayer.create(getContext(), R.raw.bombsound);
        if(p==true)
            sound.start();
        else if(p==false) {
            sound.stop();
        }
    }

    public static void playVictorySound(boolean p){
        sound = MediaPlayer.create(getContext(), R.raw.victory);
        sound.start();
        if(p==true) {
            sound.start();
        }
        else if(p==false) {
            sound.stop();
        }
    }

    //returns this class as a context to be passed in the mediaplayer thing
    public static Context getContext(){
        return MainActivity.context;
    }

    public static void setMessage(int i){
        if(i==1)
            Snackbar.make(constraintLayout, "You Hit a Bomb", Snackbar.LENGTH_LONG).show();
        if(i==2)
            Snackbar.make(constraintLayout, "Victory!!", Snackbar.LENGTH_LONG).show();

    }

}
