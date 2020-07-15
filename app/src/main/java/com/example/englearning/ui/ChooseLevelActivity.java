package com.example.englearning.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.englearning.R;
import com.example.englearning.database.MainDatabase;
import com.example.englearning.database.RanksDatabase;

import static com.example.englearning.ui.MainActivity.KEYWORDS;

public class ChooseLevelActivity extends AppCompatActivity {

    Button easyButton;
    Button mediumButton;
    Button hardButton;
    SharedPreferences sharedPreferences;
    MainDatabase mainDatabase;
    int level;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooselevel);
        mainDatabase = new MainDatabase(this);
        easyButton = findViewById(R.id.easyButton);
        mediumButton = findViewById(R.id.mediumButton);
        hardButton = findViewById(R.id.hardButton);
        sharedPreferences = getSharedPreferences("my prefs" , MODE_PRIVATE);
        setOnClicks();
        level = sharedPreferences.getInt("level_state", 1);





    }
    private void startGame(int level) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);

    }
    boolean isThereMoreWords(int level){
        if(mainDatabase.getCountedByLevel(KEYWORDS, level) > 0){
            return true;
        }else{
            return false;
        }

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
                Intent intent = new Intent(this, MainActivity.class);
                finish();
                startActivity(intent);


        }
        return true;
    }

    public void setOnClicks(){
        easyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("level_state", 1);
                editor.commit();
                level = sharedPreferences.getInt("level_state", 1);
                if (isThereMoreWords(level)) {
                    startGame(level);
                }else{
                    Toast.makeText(ChooseLevelActivity.this, "To już wszystkie hasła z tego poziomu, które mieliśmy do zaoferowania!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        mediumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("level_state", 2);
                editor.commit();
                Log.d("Level: ", " "+ level);
                level = sharedPreferences.getInt("level_state", 1);
                if (isThereMoreWords(level)) {
                    startGame(level);
                }else{
                    Toast.makeText(ChooseLevelActivity.this, "To już wszystkie hasła z tego poziomu, które mieliśmy do zaoferowania!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        hardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("level_state", 3);
                editor.commit();
                level = sharedPreferences.getInt("level_state", 1);
                if (isThereMoreWords(level)) {
                    startGame(level);
                }else{
                    Toast.makeText(ChooseLevelActivity.this, "To już wszystkie hasła z tego poziomu, które mieliśmy do zaoferowania!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
