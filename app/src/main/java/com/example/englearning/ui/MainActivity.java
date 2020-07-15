package com.example.englearning.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.englearning.R;
import com.example.englearning.database.MainDatabase;


public class MainActivity extends AppCompatActivity {

    private Button startButton;
    Button showStatsButton;

    MainDatabase mainDatabase;
    Button showDictionaryButton;
    GameActivity gameActivity = new GameActivity();
    Button resetButton;
    Button exitButton;
    SharedPreferences sharedPreferences;
    TutorialActivity tutorialActivity = new TutorialActivity();
    boolean pressedTwice = false;
    public static final String KEYWORDS = "keywords_table";
boolean hasStartedTut;
    int pageCounter;

    public boolean isHasStarted() {
        return hasStarted;
    }

    public void setHasStarted(boolean hasStarted) {
        this.hasStarted = hasStarted;
    }

    boolean hasStarted;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("my prefs" , MODE_PRIVATE);
        hasStarted = sharedPreferences.getBoolean("Is_started_state", false);
        hasStartedTut = sharedPreferences.getBoolean("Is_startedTut_state", false);

        resetButton = findViewById(R.id.resetButton);
        resetButton.setVisibility(View.INVISIBLE);

        showStatsButton = findViewById(R.id.showStatsButton);
        exitButton = findViewById(R.id.exitButton);
        mainDatabase = new MainDatabase(this);

        showDictionaryButton = findViewById(R.id.showDictionaryButton);

        startButton = findViewById(R.id.startButton);
        if(isHasStarted()){
            startButton.setText("Kontynuuj");

                startButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        continueGame();
                    }
                });

        }else{
            startButton.setText("Start");
            startButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("x", 0);
                    editor.putInt("y", 7);
                    editor.commit();
                    startGame();
                }
            });
        }
        if(hasStartedTut){


            showDictionaryButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    showDictionary();
                }
            });

        }else{
            showDictionaryButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("x", 8);
                    editor.putInt("y", 9);
                    editor.commit();
                    startGame();
                }
            });


        }


        exitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                        if(pressedTwice){

                            finish();
                            System.exit(1);


                        }else{
                            pressedTwice = true;
                            Toast.makeText(MainActivity.this, "Naciśnij ponownie, aby wyjść z gry", Toast.LENGTH_SHORT).show();

                            new Handler().postDelayed(new Runnable() {

                                @Override
                                public void run() {
                                    pressedTwice =false;
                                }
                            }, 2000);
                        }





                }


        });
        showStatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             showStats();
            }
        });
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putLong(gameActivity.getPointsToStore(), 0);
                editor.putString(gameActivity.getRankToStore(), "Świeżak");
                editor.putBoolean("Is_started_state", false);
                editor.putBoolean("Is_startedTut_state", false);

                mainDatabase.clearDictionary();
                editor.commit();


            }
        });
    }

    boolean isThereMoreWords(){
        if(mainDatabase.getCounted(KEYWORDS) > 0){
            return true;
        }else{
            return false;
        }

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(pressedTwice){
                finish();
                System.exit(1);



            }else{
                this.pressedTwice = true;
                Toast.makeText(this, "Naciśnij ponownie, aby wyjść z gry", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        pressedTwice =false;
                    }
                }, 2000);
            }




        }
        return true;
    }
    private void showDictionary(){
        Intent intent = new Intent(this, DictionaryActivity.class);
        startActivity(intent);

    }

    private void continueGame() {
        if(isThereMoreWords()){
            Intent intent = new Intent(this, ChooseLevelActivity.class);

            startActivity(intent);
        }else{
            Toast.makeText(MainActivity.this, "To już wszystkie hasła które mieliśmy do zaoferowania!", Toast.LENGTH_LONG).show();
        }


    }
    private void startGame() {
        Intent intent = new Intent(this, TutorialActivity.class);
        startActivity(intent);

    }
    private void showStats() {
        Intent intent = new Intent(this, StatsActivity.class);
        startActivity(intent);

    }



}
