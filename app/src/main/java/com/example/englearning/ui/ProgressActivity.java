package com.example.englearning.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.englearning.R;

import com.example.englearning.database.MainDatabase;
import com.example.englearning.database.RanksDatabase;

import static com.example.englearning.ui.MainActivity.KEYWORDS;

public class ProgressActivity extends AppCompatActivity {

    ImageView rankImgView;
    TextView rankNameView;
    Button nextButton;


    String rankName;
    RanksDatabase ranksDatabase;
    MainDatabase mainDatabase;
    int imageId;
    GameActivity gameActivity;
    Animation fromtop;
     MediaPlayer mp;
     boolean pressedTwice = false;
     SharedPreferences sharedPreferences;
     int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        mp = MediaPlayer.create(this, R.raw.upgrade);
        mainDatabase = new MainDatabase(this);
        ranksDatabase = new RanksDatabase(this);
        sharedPreferences = getSharedPreferences("my prefs" , MODE_PRIVATE);
        level = sharedPreferences.getInt("level_state", 1);
        rankNameView = findViewById(R.id.rankNameView);
        nextButton = findViewById(R.id.nextButton);
        rankImgView = findViewById(R.id.rankImgView);
        loadData();
        Drawable image =getResources().getDrawable(imageId);
        rankImgView.setImageDrawable(image);
        fromtop = AnimationUtils.loadAnimation(this,R.anim.fromtop);
        rankImgView.setAnimation(fromtop);


        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
        gameActivity = new GameActivity();
nextButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
backToGame();
    }
});
rankNameView.setText(rankName);
    }
    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("my prefs", MODE_PRIVATE);

        rankName = sharedPreferences.getString(gameActivity.getRankToStore(), "Newbie");
        imageId = ranksDatabase.displayRankImg(rankName);



    }
    public void backToGame(){

        if (mainDatabase.getCountedByLevel(KEYWORDS, level) > 0){
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
        }else{
            isThereMore();
        }


    }
    protected boolean isThereMore(){

        final Intent intent = new Intent(this, ChooseLevelActivity.class);
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.prize2)
                .setTitle("Gratulacje!")
                .setMessage("To już wszystkie hasła które mieliśmy do zaoferowania!")


                .setNeutralButton("Wróć do menu głównego", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        startActivity(intent);
                    }
                })



                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();


        return true;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(pressedTwice){
                Intent intent = new Intent(this, MainActivity.class);
                finish();
                startActivity(intent);
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


}
