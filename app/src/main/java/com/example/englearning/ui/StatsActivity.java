package com.example.englearning.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.englearning.R;
import com.example.englearning.database.RanksDatabase;

import java.util.ArrayList;


public class StatsActivity extends AppCompatActivity {
    RanksDatabase ranksDatabase;
    public static final String TABLE3_NAME = "stats_table";
    ConstraintLayout constraintLayout;
    ImageView imageView;
    ArrayList<ImageView> iViews = new ArrayList<ImageView>();
    SharedPreferences sharedPreferences;
    ImageView rank1;
    TextView rank1T;
    ImageView rank2;
    TextView rank2T;
    ImageView rank3;
    TextView rank3T;
    ImageView rank4;
    TextView rank4T;
    ImageView rank5;
    TextView rank5T;
    ImageView rank6;
    TextView rank6T;
    ImageView rank7;
    TextView rank7T;
    ImageView rank8;
    TextView rank8T;
    ImageView rank9;
    TextView rank9T;

    long points;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
ranksDatabase = new RanksDatabase(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        sharedPreferences = getSharedPreferences("my prefs", MODE_PRIVATE);
        points = sharedPreferences.getLong("Points_to_store", 0);

        setViews();

        constraintLayout = findViewById(R.id.constraintLayout);



    }
    protected void setViews(){
        rank1 = findViewById(R.id.rank1);
        rank1T = findViewById(R.id.rank1T);
        rank2 = findViewById(R.id.rank2);
        rank2T = findViewById(R.id.rank2T);
        rank3 = findViewById(R.id.rank3);
        rank3T = findViewById(R.id.rank3T);
        rank4 = findViewById(R.id.rank4);
        rank4T = findViewById(R.id.rank4T);
        rank5 = findViewById(R.id.rank5);
        rank5T = findViewById(R.id.rank5T);
        rank6 = findViewById(R.id.rank6);
        rank6T = findViewById(R.id.rank6T);
        rank7 = findViewById(R.id.rank7);
        rank7T = findViewById(R.id.rank7T);
        rank8 = findViewById(R.id.rank8);
        rank8T = findViewById(R.id.rank8T);
        rank9 = findViewById(R.id.rank9);
        rank9T = findViewById(R.id.rank9T);
        rank1.setImageDrawable(getResources().getDrawable(R.drawable.lock));
        rank2.setImageDrawable(getResources().getDrawable(R.drawable.lock));
        rank3.setImageDrawable(getResources().getDrawable(R.drawable.lock));
        rank4.setImageDrawable(getResources().getDrawable(R.drawable.lock));
        rank5.setImageDrawable(getResources().getDrawable(R.drawable.lock));
        rank6.setImageDrawable(getResources().getDrawable(R.drawable.lock));
        rank7.setImageDrawable(getResources().getDrawable(R.drawable.lock));
        rank8.setImageDrawable(getResources().getDrawable(R.drawable.lock));
        rank9.setImageDrawable(getResources().getDrawable(R.drawable.lock));


        ranksDatabase.printTableData3(1);


        if(ranksDatabase.getPOINTSREQ() <= points){
            rank1T.setText(ranksDatabase.getRANKNAME());
            rank1.setImageDrawable(getResources().getDrawable(ranksDatabase.displayRankImg(rank1T.getText().toString())));

        }else{
            return;
        }


        ranksDatabase.printTableData3(2);
        if(ranksDatabase.getPOINTSREQ() <= points) {
            rank2T.setText(ranksDatabase.getRANKNAME());
            rank2.setImageDrawable(getResources().getDrawable(ranksDatabase.displayRankImg(rank2T.getText().toString())));

        }else{
            return;
        }
        ranksDatabase.printTableData3(3);
        if(ranksDatabase.getPOINTSREQ() <= points) {
            rank3T.setText(ranksDatabase.getRANKNAME());
            rank3.setImageDrawable(getResources().getDrawable(ranksDatabase.displayRankImg(rank3T.getText().toString())));

        }else{
            return;
        }
        ranksDatabase.printTableData3(4);
        if(ranksDatabase.getPOINTSREQ() <= points) {
            rank4T.setText(ranksDatabase.getRANKNAME());
            rank4.setImageDrawable(getResources().getDrawable(ranksDatabase.displayRankImg(rank4T.getText().toString())));

        }else{
            return;
        }
        ranksDatabase.printTableData3(5);
        if(ranksDatabase.getPOINTSREQ() <= points) {
            rank5T.setText(ranksDatabase.getRANKNAME());
            rank5.setImageDrawable(getResources().getDrawable(ranksDatabase.displayRankImg(rank5T.getText().toString())));

        }else{
            return;
        }
        ranksDatabase.printTableData3(6);
        if(ranksDatabase.getPOINTSREQ() <= points) {
            rank6T.setText(ranksDatabase.getRANKNAME());
            rank6.setImageDrawable(getResources().getDrawable(ranksDatabase.displayRankImg(rank6T.getText().toString())));
        }else{
            return;
        }
        ranksDatabase.printTableData3(7);
        if(ranksDatabase.getPOINTSREQ() <= points) {
            rank7T.setText(ranksDatabase.getRANKNAME());
            rank7.setImageDrawable(getResources().getDrawable(ranksDatabase.displayRankImg(rank7T.getText().toString())));

        }else{
            return;
        }
        ranksDatabase.printTableData3(8);
        if(ranksDatabase.getPOINTSREQ() <= points) {
            rank8T.setText(ranksDatabase.getRANKNAME());
            rank8.setImageDrawable(getResources().getDrawable(ranksDatabase.displayRankImg(rank8T.getText().toString())));

        }else{
            return;
        }
        ranksDatabase.printTableData3(9);
        if(ranksDatabase.getPOINTSREQ() <= points) {
            rank9T.setText(ranksDatabase.getRANKNAME());
            rank9.setImageDrawable(getResources().getDrawable(ranksDatabase.displayRankImg(rank9T.getText().toString())));

        }else{
            return;
        }
    }

}
