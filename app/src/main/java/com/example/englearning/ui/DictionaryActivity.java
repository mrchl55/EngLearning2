package com.example.englearning.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.englearning.R;
import com.example.englearning.database.MainDatabase;


public class DictionaryActivity extends AppCompatActivity {

    TextView dictionaryTextView;
    Button retakeButton;
    MainDatabase database;
    public static final String FAILURES = "failures_table";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        database = new MainDatabase(this);

        dictionaryTextView = findViewById(R.id.dictionaryTextView);

        retakeButton = findViewById((R.id.retakeButton));
        dictionaryTextView.setMovementMethod(new ScrollingMovementMethod());
        final Intent intent = new Intent(this, DictGameActivity.class);
        retakeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(database.getCounted(FAILURES) > 0){
                    startActivity(intent);
                }else{
                    Toast.makeText(DictionaryActivity.this, "Twój słowniczek jest pusty!", Toast.LENGTH_LONG).show();
                }

            }
        });
        if(database.getCounted(FAILURES) > 0){
            dictionaryTextView.setText(database.printTableData());
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


}
