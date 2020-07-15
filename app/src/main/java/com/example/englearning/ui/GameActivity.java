package com.example.englearning.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.englearning.R;
import com.example.englearning.database.MainDatabase;
import com.example.englearning.database.RanksDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GameActivity extends AppCompatActivity {
    public static final String TAG = GameActivity.class.getSimpleName();
    MainActivity mainActivity;
    String keyHidden;
    TextView keyHiddenTextView;
    private String key;

    public int getCounter() {
        return counter;
    }

    private int counter;
    private int level;
    MainDatabase mainDatabase;
    RanksDatabase ranksDatabase;
    ArrayList<TextView> viewsList = new ArrayList<>();
    List<String> congratsList = new ArrayList<String>();
    List<String> wrongList = new ArrayList<String>();
    private long points;
    SharedPreferences sharedPreferences;
    public static final String KEYWORDS = "keywords_table";

    public static String getPointsToStore() {
        return POINTS_TO_STORE;
    }

    public static String POINTS_TO_STORE = "Points_to_store";

    public static String getRankToStore() {
        return RANK_TO_STORE;
    }

    public static String RANK_TO_STORE = "Rank_to_store";
    ConstraintLayout mainLayout;
        LinearLayout lettersLayout;
        LinearLayout lettersLayout2;
        LinearLayout lettersLayout3;
        LinearLayout lettersLayout4;
        TextView counterTextView;
        Button nextButton;
        ImageView pronounceIcon;
        TextView resultView;
        TextView resultView2;
        Button showAnswerButton;
        Button tryAgainButton;
        TextView pointsScored;
        TextView rankTextView;
        String lastKeyword;
        String lastKeyword1;
        String lastKeyword2;
        String lastKeyword3;
        int lastPron;
        int lastSound;
        Random rand;
    boolean pressedTwice;
    boolean isTriedAgain = false;







    ImageView mainImageView;
    boolean isLost=false;

     TextView letterA, letterB,letterC,letterD,letterE,letterF,letterG,letterH,
             letterI,letterJ,letterK,letterL,letterM,letterN,letterO,
    letterP,letterQ,letterR,letterS,letterT,letterU,letterV,letterW,letterX,letterY,letterZ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainDatabase = new MainDatabase(this);
        if (mainDatabase.getCounted(KEYWORDS) > 0){

            setContentView(R.layout.activity_game);


            ranksDatabase = new RanksDatabase(this);
            mainActivity = new MainActivity();
            counter = 3;


            setViews();


            loadData();

            restorePage(level);


            mainActivity.setHasStarted(true);
            saveData();
        }
        else{
            isThereMore();
        }

    }
    protected boolean isThereMore(){
            mainLayout.setVisibility(View.INVISIBLE);
           final Intent intent = new Intent(this, MainActivity.class);
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.prize2)
                    .setTitle("Gratulacje!")
                    .setMessage("To już wszystkie hasła które mieliśmy do zaoferowania na tym poziomie trudności!")

                    .setNeutralButton("Wróć do menu głównego", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            startActivity(intent);
                        }
                    })




                    .show();


        return true;
    }
    protected void restorePage(int level){
       final Context context = this;
        if (mainDatabase.getCountedByLevel(KEYWORDS, level) > 0){


            loadData();
if(level == 1){
    lastKeyword = lastKeyword1;
}else if(level == 2){
    lastKeyword = lastKeyword2;
}else if(level == 3){
    lastKeyword = lastKeyword3;
}
            if(mainDatabase.getTranslation(KEYWORDS, lastKeyword) != "") {
                counterTextView.setText(String.valueOf(counter));
                key = lastKeyword;
                Drawable image = getResources().getDrawable(mainDatabase.getImageID(KEYWORDS, key));
                mainImageView.setImageDrawable(image);



                setListeners();
                createKeyHidden(key);
                keyHiddenTextView.setVisibility(View.VISIBLE);
                if(mainDatabase.getThisSound(KEYWORDS, key) != 0){


                    lastSound = mainDatabase.getThisSound(KEYWORDS, key);
                    mainImageView.setOnClickListener(new View.OnClickListener(){

                        public void onClick(View v) {
                            final MediaPlayer mp = MediaPlayer.create(context, lastSound);
                            mp.start();
                            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    mp.release();
                                }
                            });
                        }
                    });
                }else{

                }
            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();


                if(level == 1){
                    editor.remove("KeywordL1_to_store");
                }else if(level == 2){
                    editor.remove("KeywordL2_to_store");
                }else if(level == 3){
                    editor.remove("Keyword3_to_store");
                }

                editor.commit();
                loadPage();
        }


}else{
            isThereMore();
        }
    }
    public void saveData(){
       sharedPreferences = getSharedPreferences("my prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("KeywordL1_to_store", lastKeyword1);
        editor.putString("KeywordL2_to_store", lastKeyword2);
        editor.putString("KeywordL3_to_store", lastKeyword3);
        editor.putInt("Sound_to_store", lastSound);

        editor.putBoolean("Is_started_state", mainActivity.isHasStarted());
        editor.putLong(POINTS_TO_STORE, points);
        editor.putString(RANK_TO_STORE, String.valueOf(rankTextView.getText()));

        editor.commit();
    }


    public void loadData(){

       sharedPreferences = getSharedPreferences("my prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        level = sharedPreferences.getInt("level_state", 1);
        lastKeyword1 = sharedPreferences.getString("KeywordL1_to_store" , mainDatabase.getWord(KEYWORDS, 1));

        lastKeyword2 = sharedPreferences.getString("KeywordL2_to_store" , mainDatabase.getWord(KEYWORDS, 2));
        lastKeyword3 = sharedPreferences.getString("KeywordL3_to_store" , mainDatabase.getWord(KEYWORDS, 3));
        if(level == 1){
            lastPron = sharedPreferences.getInt("Pronunciation_to_store", mainDatabase.getThisPro(KEYWORDS, lastKeyword1));
            lastSound = sharedPreferences.getInt("Sound_to_store", mainDatabase.getThisSound(KEYWORDS, lastKeyword1));
        }else if(level == 2){
            lastPron = sharedPreferences.getInt("Pronunciation_to_store", mainDatabase.getThisPro(KEYWORDS, lastKeyword2));
            lastSound = sharedPreferences.getInt("Sound_to_store", mainDatabase.getThisSound(KEYWORDS, lastKeyword2));
        }else if(level == 3){
            lastPron = sharedPreferences.getInt("Pronunciation_to_store", mainDatabase.getThisPro(KEYWORDS, lastKeyword3));
            lastSound = sharedPreferences.getInt("Sound_to_store", mainDatabase.getThisSound(KEYWORDS, lastKeyword3));
        }





        points = sharedPreferences.getLong(POINTS_TO_STORE, 0);
        rankTextView.setText(sharedPreferences.getString(RANK_TO_STORE, "Świeżak"));
        pointsScored.setText(String.valueOf(sharedPreferences.getLong(POINTS_TO_STORE, 0)));

    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){

        saveData();
        super.onSaveInstanceState(savedInstanceState);




    }


    protected void setViews(){
        mainImageView = findViewById(R.id.mainImageView);
        mainLayout = findViewById(R.id.mainLayout);
        mainLayout.setVisibility(View.VISIBLE);
        letterA = findViewById(R.id.letterA);
        letterB = findViewById(R.id.letterB);
        letterC = findViewById(R.id.letterC);
        letterD = findViewById(R.id.letterD);
        letterE = findViewById(R.id.letterE);
        letterF = findViewById(R.id.letterF);
        letterG = findViewById(R.id.letterG);
        letterH = findViewById(R.id.letterH);
        letterI = findViewById(R.id.letterI);
        letterJ = findViewById(R.id.letterJ);
        letterK = findViewById(R.id.letterK);
        letterL = findViewById(R.id.letterL);
        letterM = findViewById(R.id.letterM);
        letterN = findViewById(R.id.letterN);
        letterO = findViewById(R.id.letterO);
        letterP = findViewById(R.id.letterP);
        letterQ = findViewById(R.id.letterQ);
        letterR = findViewById(R.id.letterR);
        letterS = findViewById(R.id.letterS);
        letterT = findViewById(R.id.letterT);
        letterU = findViewById(R.id.letterU);
        letterV = findViewById(R.id.letterV);
        letterW = findViewById(R.id.letterW);
        letterX = findViewById(R.id.letterX);
        letterY = findViewById(R.id.letterY);
        letterZ = findViewById(R.id.letterZ);
        lettersLayout = findViewById(R.id.lettersLayout);
        lettersLayout2 = findViewById(R.id.lettersLayout2);
        lettersLayout3 = findViewById(R.id.lettersLayout3);
        lettersLayout4 = findViewById(R.id.lettersLayout4);
        showAnswerButton = findViewById(R.id.showAnswerButton);
        tryAgainButton = findViewById(R.id.tryAgainButton);
        pointsScored = findViewById(R.id.pointsScored);
        rankTextView = findViewById(R.id.rankTextView);
        ranksDatabase.upgradeStats(points, rankTextView);
        pointsScored.setText(String.valueOf(points));
        pronounceIcon = findViewById(R.id.pronounceIcon);
        pressedTwice = false;


        counterTextView = findViewById(R.id.counterTextView);

        nextButton = findViewById(R.id.nextButton);
        isLost = false;
        resultView = findViewById(R.id.resultView);
        resultView2 = findViewById(R.id.resultView2);
        rand = new Random();
        congratsList.add(getResources().getString(R.string.congrats1));
        congratsList.add(getResources().getString(R.string.congrats2));
        congratsList.add(getResources().getString(R.string.congrats3));
        congratsList.add(getResources().getString(R.string.congrats4));
        congratsList.add(getResources().getString(R.string.congrats5));
        wrongList.add(getResources().getString(R.string.wrong1));
        wrongList.add(getResources().getString(R.string.wrong2));
        wrongList.add(getResources().getString(R.string.wrong3));
        wrongList.add(getResources().getString(R.string.wrong4));
        wrongList.add(getResources().getString(R.string.wrong5));

    }
    // setting OnClickListeners
    protected void setListeners() {
        letterA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doesExist(letterA.getText().toString(), key.toUpperCase(), letterA);
            }
        });
        letterB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doesExist(letterB.getText().toString(), key.toUpperCase(), letterB);
            }
        });
        letterC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doesExist(letterC.getText().toString(), key.toUpperCase(), letterC);
            }
        });
        letterD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doesExist(letterD.getText().toString(), key.toUpperCase(), letterD);
            }
        });
        letterE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doesExist(letterE.getText().toString(), key.toUpperCase(), letterE);
            }
        });
        letterF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doesExist(letterF.getText().toString(), key.toUpperCase(), letterF);
            }
        });
        letterG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doesExist(letterG.getText().toString(), key.toUpperCase(), letterG);
            }
        });
        letterH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doesExist(letterH.getText().toString(), key.toUpperCase(), letterH);
            }
        });
        letterI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doesExist(letterI.getText().toString(), key.toUpperCase(), letterI);
            }
        });
        letterJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doesExist(letterJ.getText().toString(), key.toUpperCase(), letterJ);
            }
        });
        letterK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doesExist(letterK.getText().toString(), key.toUpperCase(), letterK);
            }
        });
        letterL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doesExist(letterL.getText().toString(), key.toUpperCase(), letterL);
            }
        });
        letterM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doesExist(letterM.getText().toString(), key.toUpperCase(), letterM);
            }
        });
        letterN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doesExist(letterN.getText().toString(), key.toUpperCase(), letterN);
            }
        });
        letterO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doesExist(letterO.getText().toString(), key.toUpperCase(), letterO);
            }
        });
        letterP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doesExist(letterP.getText().toString(), key.toUpperCase(), letterP);
            }
        });
        letterQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doesExist(letterQ.getText().toString(), key.toUpperCase(), letterQ);
            }
        });
        letterR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doesExist(letterR.getText().toString(), key.toUpperCase(), letterR);
            }
        });
        letterS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doesExist(letterS.getText().toString(), key.toUpperCase(), letterS);
            }
        });
        letterT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doesExist(letterT.getText().toString(), key.toUpperCase(), letterT);
            }
        });
        letterU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doesExist(letterU.getText().toString(), key.toUpperCase(), letterU);
            }
        });
        letterV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doesExist(letterV.getText().toString(), key.toUpperCase(), letterV);
            }
        });
        letterW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doesExist(letterW.getText().toString(), key.toUpperCase(), letterW);
            }
        });
        letterX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doesExist(letterX.getText().toString(), key.toUpperCase(), letterX);
            }
        });
        letterY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doesExist(letterY.getText().toString(), key.toUpperCase(), letterY);
            }
        });
        letterZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doesExist(letterZ.getText().toString(), key.toUpperCase(), letterZ);
            }
        });


    }

    protected void loadPage() {
        final Context context = this;
        sharedPreferences = getSharedPreferences("my prefs", MODE_PRIVATE);
        loadData();
        if (mainDatabase.getCountedByLevel(KEYWORDS, level) > 0) {

            key = mainDatabase.getWord(KEYWORDS, level);

            counterTextView.setText(String.valueOf(counter));
            Drawable image = getResources().getDrawable(mainDatabase.getImageID(KEYWORDS, key));
            mainImageView.setImageDrawable(image);


            if (mainDatabase.getThisSound(KEYWORDS, key) != 0) {

    lastSound = mainDatabase.getThisSound(KEYWORDS, key);
                mainImageView.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        final MediaPlayer mp = MediaPlayer.create(context, lastSound);
                        mp.start();
                        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                mp.release();
                            }
                        });
                    }
                });
            }


            setListeners();
            createKeyHidden(key);
            keyHiddenTextView.setVisibility(View.VISIBLE);



            if(level == 1){
                lastKeyword1 = key;
            }else if(level == 2){
                lastKeyword2 = key;
            }else if(level == 3){
                lastKeyword3 = key;
            }

            saveData();

        }else{
        isThereMore();
    }


    }


    protected void doesExist(String lett, final String keyword, TextView textView) {

        String guessedKey = keyHiddenTextView.getText().toString();
        char [] s = guessedKey.toCharArray();
        String guessedSoFar;

        boolean isThere = false;
for (int i = 0;i<keyword.length();i++){
    // if guessed
    if(lett.charAt(0) == keyword.charAt(i)){
        isThere = true;
    s[i] = keyword.charAt(i);


    textView.setOnClickListener(null);
    textView.setVisibility(View.INVISIBLE);
        viewsList.add(textView);
    guessedSoFar = new String(s);

        keyHiddenTextView.setText(guessedSoFar);
        if(keyHiddenTextView.getText().toString().toUpperCase().equals(keyword.toUpperCase())){


            keyHiddenTextView.setVisibility(View.INVISIBLE);
            resultView.setVisibility(View.VISIBLE);
            resultView.setText(congratsList.get(rand.nextInt(4)));
            resultView.setTextColor(Color.GREEN);

            resultView2.setText(keyword.toLowerCase() + " - " + mainDatabase.getTranslation(KEYWORDS, keyword));
            setNextButton(keyword);
           mainDatabase.deleteRecord(KEYWORDS, keyword);
           if(!isTriedAgain){
               if(level == 1){
                   points+=5;
               }else if(level == 2){
                   points+=10;
               }else if(level == 3){
                   points+=15;
               }

               pointsScored.setText(String.valueOf(points));

           }else{
               isTriedAgain = false;
           }







            lettersLayout.setVisibility(View.INVISIBLE);
            lettersLayout2.setVisibility(View.INVISIBLE);
            lettersLayout3.setVisibility(View.INVISIBLE);
            lettersLayout4.setVisibility(View.INVISIBLE);





        }
    }

}
if(isThere){
    MediaPlayer correctMP = MediaPlayer.create(this, R.raw.correct);
    correctMP.start();
    correctMP.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            mp.release();
        }
    });
}

if(!isThere){
    final MediaPlayer wrongMP = MediaPlayer.create(this, R.raw.wrong);

    wrongMP.start();
    wrongMP.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            mp.release();
        }
    });
    counter--;
    textView.setOnClickListener(null);
    textView.setTextColor(Color.RED);
    viewsList.add(textView);
    counterTextView.setText(String.valueOf(counter));

    if(counter == 0){
        resultView.setText(wrongList.get(rand.nextInt(4)));
        showAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resultView.setText("Chodziło o: ");
                resultView2.setText(keyword.toLowerCase() + "- " + mainDatabase.getTranslation(KEYWORDS, keyword));
                setNextButton(keyword);
                mainDatabase.moveWord(keyword);
                mainDatabase.deleteRecord(KEYWORDS, keyword);
                showAnswerButton.setVisibility(View.INVISIBLE);
                tryAgainButton.setVisibility(View.INVISIBLE);






            }
        });


        keyHiddenTextView.setVisibility(View.INVISIBLE);
        resultView.setVisibility(View.VISIBLE);
        resultView.setTextColor(Color.RED);
        lettersLayout.setVisibility(View.INVISIBLE);
        lettersLayout2.setVisibility(View.INVISIBLE);
        lettersLayout3.setVisibility(View.INVISIBLE);
        lettersLayout4.setVisibility(View.INVISIBLE);

        setTryAgainButton();


    }
}

    }


    public void setNextButton(final String keyword){
        if(mainDatabase.getThisPro(KEYWORDS, keyword) != 0){
            sharedPreferences = getSharedPreferences("my prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("Pronunciation_to_store", mainDatabase.getThisPro(KEYWORDS, keyword));
            editor.commit();


            final Context context = this;
            pronounceIcon.setVisibility(View.VISIBLE);

            lastPron = mainDatabase.getThisPro(KEYWORDS, keyword);


            pronounceIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MediaPlayer mp3 = MediaPlayer.create(context, lastPron);
                   mp3.start();
                    mp3.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mp.release();
                        }
                    });

                }

            });

        }

        resultView2.setVisibility(View.VISIBLE);
            nextButton.setVisibility(View.VISIBLE);
        counterTextView.setVisibility(View.INVISIBLE);
            nextButton.setText("Następne hasło");
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainImageView.setOnClickListener(null);
                    if(ranksDatabase.upgradeStats(points, rankTextView) == true){

                        saveData();
                        loadData();
                        showProgress();
                    }else{
                        saveData();
                        reset();
                        loadPage();

                    }

                }
            });


    }
    public void setTryAgainButton(){

            showAnswerButton.setVisibility(View.VISIBLE);
            tryAgainButton.setVisibility(View.VISIBLE);
            tryAgainButton.setText("Spróbuj ponownie");
            tryAgainButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isTriedAgain = true;
                    reset();
                    restorePage(level);


                }
            });


    }

    public void reset(){
        nextButton.setVisibility(View.INVISIBLE);
        lettersLayout.setVisibility(View.VISIBLE);
        lettersLayout2.setVisibility(View.VISIBLE);
        lettersLayout3.setVisibility(View.VISIBLE);
        lettersLayout4.setVisibility(View.VISIBLE);
        showAnswerButton.setVisibility(View.INVISIBLE);
        resultView.setVisibility(View.INVISIBLE);
        resultView2.setVisibility(View.INVISIBLE);
        tryAgainButton.setVisibility(View.INVISIBLE);
        counterTextView.setVisibility(View.VISIBLE);

        pronounceIcon.setVisibility(View.INVISIBLE);
        mainImageView.setOnClickListener(null);
        for(int i = 0;i<viewsList.size();i++){
            viewsList.get(i).setTextColor(Color.parseColor("#808080"));
            viewsList.get(i).setVisibility(View.VISIBLE);
        }
        counter = 3;

    }
    protected void createKeyHidden(String key){
        keyHidden="";
        for(int i = 0; i<key.length();i++){
            keyHidden+="_";

        }
        keyHiddenTextView = findViewById(R.id.keyHiddenTextView);
        keyHiddenTextView.setText(keyHidden);


    }
    private void showProgress() {
        Intent intent = new Intent(this, ProgressActivity.class);
        startActivity(intent);

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(pressedTwice){
                Intent intent = new Intent(this, ChooseLevelActivity.class);
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
