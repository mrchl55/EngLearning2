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

public class DictGameActivity extends AppCompatActivity {
    public static final String TAG = GameActivity.class.getSimpleName();
    MainActivity mainActivity;
    String keyHidden;
    TextView keyHiddenTextView;
    private String key;
    private int counter;
    MainDatabase database;
    RanksDatabase ranksDatabase;
    ArrayList<TextView> viewsList = new ArrayList<>();
    int wonRounds = 0;

    SharedPreferences sharedPreferences;

    ConstraintLayout mainDictLayout;
    LinearLayout lettersLayout;
    LinearLayout lettersLayout2;
    LinearLayout lettersLayout3;
    LinearLayout lettersLayout4;
    TextView counterTextView;
    Button nextButton;
    TextView resultView;
    TextView resultView2;
    Button showAnswerButton;
    Button tryAgainButton;
    List<String> congratsList = new ArrayList<String>();
    List<String> wrongList = new ArrayList<String>();
    ArrayList<String> usedKeys = new ArrayList<String>();
    Random rand;
    ImageView pronounceIcon;
    int lastPron;
    int lastSound;
    boolean pressedTwice = false;
        ImageView drawNextWordIcon;



    String lastKeyword;
    int lastImageId;
   String lastDrafted;




    ImageView imageView;
    boolean isLost=false;

    TextView letterA, letterB,letterC,letterD,letterE,letterF,letterG,letterH,
            letterI,letterJ,letterK,letterL,letterM,letterN,letterO,
            letterP,letterQ,letterR,letterS,letterT,letterU,letterV,letterW,letterX,letterY,letterZ;

    public static final String FAILURES = "failures_table";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictgame);
        database = new MainDatabase(this);
        if (database.getCounted(FAILURES) > 0){


            ranksDatabase = new RanksDatabase(this);
            mainActivity = new MainActivity();
            counter = 3;


            setViews();
            database.printTableData2(FAILURES);


            restorePage();
lastDrafted = lastKeyword;


            mainActivity.setHasStarted(true);
            saveData();
        }
        else{
            isThereMore();
        }
    }
    protected void restorePage(){
        final Context context = this;
        if (database.getCounted(FAILURES) > 0){

            loadData();

            if(database.getTranslation(FAILURES, lastKeyword) != "") {
                counterTextView.setText(String.valueOf(counter));
                key = lastKeyword;
                Drawable image = getResources().getDrawable(database.getImageID(FAILURES, key));
                imageView.setImageDrawable(image);



                setListeners();
                createKeyHidden(key);
                keyHiddenTextView.setVisibility(View.VISIBLE);
                if(database.getThisSound(FAILURES, key) != 0){
                    lastSound = database.getThisSound(FAILURES, key);


                    imageView.setOnClickListener(new View.OnClickListener(){

                        public void onClick(View v) {
                            final MediaPlayer mp = MediaPlayer.create(context, database.getThisSound(FAILURES, key));
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

                editor.remove("Keyword2_to_store");

                editor.commit();
                restorePage();
            }


        }else{
            isThereMore();
        }
    }
    protected boolean isThereMore(){
        mainDictLayout.setVisibility(View.INVISIBLE);
        final Intent intent = new Intent(this, DictionaryActivity.class);
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.prize2)
                .setTitle("Gratulacje!")
                .setMessage("To już wszystkie hasła które mieliśmy do zaoferowania!" + " Udało Ci się rozwiązać ich: " + wonRounds)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setNeutralButton("Wróć do menu głównego", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        startActivity(intent);

                    }
                })




                .show();


        return true;
    }
    public void saveData(){
        sharedPreferences = getSharedPreferences("my prefs2", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Sound2_to_store", lastSound);
        editor.putString("Keyword2_to_store", lastKeyword);
        editor.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
     final   Context context  = this;
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(pressedTwice){
    if(wonRounds>0){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setIcon(R.drawable.prize2);
        alertDialog.setTitle("Świetnie!");
        alertDialog.setMessage("Liczba odgadniętych właśnie słów z których rozwiązaniem miałeś wcześniej problem: " + wonRounds);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        saveData();
                        Intent intent = new Intent(context, DictionaryActivity.class);
                        finish();
                        startActivity(intent);

                    }
                });
        alertDialog.show();
    }else{
        saveData();
        Intent intent = new Intent(this, DictionaryActivity.class);
        finish();
        startActivity(intent);
    }




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
    public void loadData(){

        sharedPreferences = getSharedPreferences("my prefs2", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        lastKeyword = sharedPreferences.getString("Keyword2_to_store" , database.getKEYW());
        lastPron = sharedPreferences.getInt("Pronunciation2_to_store", database.getThisPro(FAILURES, lastKeyword));
        lastSound = sharedPreferences.getInt("Sound2_to_store", database.getThisSound(FAILURES, lastKeyword));


        

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){

        saveData();
        super.onSaveInstanceState(savedInstanceState);




    }


    protected void setViews(){
        mainDictLayout = findViewById(R.id.mainDictLayout);
        mainDictLayout.setVisibility(View.VISIBLE);
        imageView = findViewById(R.id.mainImageView);
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
ranksDatabase = new RanksDatabase(this);
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
        pronounceIcon = findViewById(R.id.pronounceIcon);

        drawNextWordIcon = findViewById(R.id.drawNextWordIcon);
        drawNextWordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (database.getCounted(FAILURES) > 1){
                    reset();

                    loadPage();
                }else{
                    isThereMore();
                }

            }
        });
        counterTextView = findViewById(R.id.counterTextView);

        nextButton = findViewById(R.id.nextButton);
        isLost = false;
        resultView = findViewById(R.id.resultView);
        resultView2 = findViewById(R.id.resultView2);
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
        sharedPreferences = getSharedPreferences("my prefs2", MODE_PRIVATE);

        if (database.getCounted(FAILURES) > 0) {
            database.printTableData2(FAILURES);
            key = database.getKEYW();


                if(!key.toLowerCase().equals(lastDrafted)){
                    lastDrafted = key.toLowerCase();

                    counterTextView.setText(String.valueOf(counter));

                    Drawable image = getResources().getDrawable(database.getImageID(FAILURES, key));
                    imageView.setImageDrawable(image);


                    if (database.getThisSound(FAILURES, key) != 0) {
                        lastSound = database.getThisSound(FAILURES, key);

                        imageView.setOnClickListener(new View.OnClickListener() {

                            public void onClick(View v) {
                                final MediaPlayer mp = MediaPlayer.create(context, database.getThisSound(FAILURES, key));
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

                    loadData();


                    lastKeyword = key;
                    saveData();
                }else if(key.toLowerCase().equals(lastDrafted) && database.getCounted(FAILURES) != 1){
                    loadPage();
                }else{
                    isThereMore();
                }




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

                    wonRounds++;
                    keyHiddenTextView.setVisibility(View.INVISIBLE);
                    resultView.setVisibility(View.VISIBLE);
                    resultView.setText(congratsList.get(rand.nextInt(4)));
                    resultView.setTextColor(Color.GREEN);

                    resultView2.setText(keyword + " - " + database.getTranslation(FAILURES, keyword));
                    setNextButton(keyword);
                    database.deleteRecord(FAILURES, keyword);
                    
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

                        resultView2.setText(keyword + "- " + database.getTranslation(FAILURES, keyword));
                        setNextButton(keyword);
                        showAnswerButton.setVisibility(View.INVISIBLE);
                        tryAgainButton.setVisibility(View.INVISIBLE);

                    }
                });

                keyHiddenTextView.setVisibility(View.INVISIBLE);
                resultView.setVisibility(View.VISIBLE);
                resultView.setText("Porażka!");
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
        if(database.getThisPro(FAILURES, keyword) != 0){
            sharedPreferences = getSharedPreferences("my prefs2", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("Pronunciation2_to_store", database.getThisPro(FAILURES, keyword));
            editor.commit();


            final Context context = this;
            pronounceIcon.setVisibility(View.VISIBLE);

            lastPron = sharedPreferences.getInt("Pronunciation2_to_store", 0);


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
        drawNextWordIcon.setVisibility(View.INVISIBLE);
        resultView2.setVisibility(View.VISIBLE);
        nextButton.setVisibility(View.VISIBLE);
        counterTextView.setVisibility(View.INVISIBLE);
        nextButton.setText("Następne hasło");
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setOnClickListener(null);

                    saveData();
                    reset();
                    loadPage();


            }
        });


    }
    public void setTryAgainButton(){

        showAnswerButton.setVisibility(View.VISIBLE);
        tryAgainButton.setVisibility(View.VISIBLE);
        tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                restorePage();


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
        drawNextWordIcon.setVisibility(View.VISIBLE);
        imageView.setOnClickListener(null);
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

}
