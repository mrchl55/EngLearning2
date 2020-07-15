package com.example.englearning.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.englearning.R;
import com.example.englearning.database.MainDatabase;
import com.example.englearning.model.Tutorial;
import com.example.englearning.model.Tutorials;

public class TutorialActivity extends AppCompatActivity {

    public static void setIsStartedState(String stateToStore) {
        IS_STARTED_STATE = stateToStore;
    }

    public static String getIsStartedState() {
        return IS_STARTED_STATE;
    }

    public static String IS_STARTED_STATE = "Is_started_state";
    SharedPreferences sharedPreferences;
    TextView titleTextView;
    TextView descTextView;
    Tutorials tutorials;
    ImageView tutImageView;
    MainDatabase mainDatabase;
    Button nextButton;
    int counter;
    Button previousPageButton;
    private int pageNumber;
    int x;
    int y;
    boolean pressedTwice = false;
    int previousPageCounter = x;
    public static final String KEYWORDS = "keywords_table";
    @Override
    protected void onCreate(Bundle saveOnInstanceState){
        super.onCreate(saveOnInstanceState);
        setContentView(R.layout.activity_tutorial);
        sharedPreferences = getSharedPreferences("my prefs" , MODE_PRIVATE);
        x = sharedPreferences.getInt("x", 0);
        y = sharedPreferences.getInt("y", 7);
        mainDatabase = new MainDatabase(this);


    previousPageButton = findViewById(R.id.previousPageButton);
        counter = x;
        tutorials = new Tutorials();
        nextButton = findViewById(R.id.nextButton);
        titleTextView = findViewById(R.id.titleTextView);
        descTextView = findViewById(R.id.descTextView);
        tutImageView = findViewById(R.id.tutImageView);

        loadPage(tutorials.getTutorial(counter));
        nextButton.setText("Dalej");


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
    protected void loadPage(Tutorial tutorial) {
      final  Context context = this;
    Drawable image = getResources().getDrawable(tutorial.getId());
    tutImageView.setImageDrawable(image);
    String title = getResources().getString(tutorial.getTitle());
    titleTextView.setText(title);
    String desc = getResources().getString(tutorial.getDesc());
    descTextView.setText(desc);
        if(counter > x){
            previousPageCounter =  counter -1;
            previousPageButton.setVisibility(View.VISIBLE);
            previousPageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    counter = previousPageCounter;
                    loadPage(tutorials.getTutorial(previousPageCounter));

                }
            });
        }else{
            previousPageButton.setVisibility(View.VISIBLE);
            previousPageButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                     Intent intent = new Intent(context, MainActivity.class);
                    finish();
                    startActivity(intent);
                }
            });
        }

    if(counter<=y){
        counter++;
    }

        if(counter<=y) {
            nextButton.setText("Dalej");
    nextButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            loadPage(tutorials.getTutorial(counter));
        }
    });
}else if(counter == 8){
            nextButton.setText("Rozumiem. Zaczynajmy!");
   final Intent intent = new Intent(this, ChooseLevelActivity.class);


    nextButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mainDatabase.getCounted(KEYWORDS) > 0){
                startActivity(intent);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(IS_STARTED_STATE, true);

                editor.commit();
            }else{
                Toast.makeText(TutorialActivity.this, "Gratulacje! Odgadłeś już wszystkie hasła!", Toast.LENGTH_LONG).show();
            }


        }
    });
}else if(counter == 10){
            nextButton.setText("Rozumiem. Zaczynajmy!");
            final Intent intent = new Intent(this, DictionaryActivity.class);


            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        startActivity(intent);

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("Is_startedTut_state", true);

                        editor.commit();

                }
            });
        }




    }
}
