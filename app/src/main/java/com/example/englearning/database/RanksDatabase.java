package com.example.englearning.database;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;

import static com.example.englearning.ui.GameActivity.TAG;

public class RanksDatabase extends MainDatabase {

    public String getRANKNAME() {
        return RANKNAME;
    }

    public int getRANKIMGID() {
        return RANKIMGID;
    }

    public int getPOINTSREQ() {
        return POINTSREQ;
    }

    String RANKNAME;
    int RANKIMGID;
    int POINTSREQ;

    public RanksDatabase(@Nullable Context context){
        super(context);
        SQLiteDatabase db = this.getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {


    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        onCreate(db);
    }
    public boolean upgradeStats(long points, TextView textView){
        SQLiteDatabase db = this.getWritableDatabase();
        String num;
        String query2 = "select NAME from " + TABLE3_NAME + " WHERE  POINTSREQ <= " + points + " ORDER BY ID desc LIMIT 1 ";
        Cursor cursor2 = db.rawQuery(query2, null);
        cursor2.moveToFirst();
        if( cursor2 != null && cursor2.moveToFirst()){

            num = cursor2.getString(cursor2.getColumnIndex("NAME"));

            if(!(cursor2.getString(cursor2.getColumnIndex("NAME"))).equals(String.valueOf(textView.getText()))){
                textView.setText(num);

                cursor2.close();
                return true;
            }else{
                cursor2.close();
                return false;
            }


        }else{
            return false;
        }

    }
    public int displayRankImg(String rankName){
        SQLiteDatabase db = this.getWritableDatabase();
        int num = 0;
        String query2 = "select IMAGEID from " + TABLE3_NAME + " WHERE  NAME = " + "'" + rankName + "'";
        Cursor cursor2 = db.rawQuery(query2, null);
        cursor2.moveToFirst();
        if( cursor2 != null && cursor2.moveToFirst() ){
            num = cursor2.getInt(cursor2.getColumnIndex("IMAGEID"));


        }
        cursor2.close();


        return num;


    }

   public String getRankName(int ID){
       SQLiteDatabase db = this.getWritableDatabase();
       String num = "";
       String query = "select RANK from " + TABLE3_NAME + " WHERE  ID = " + ID;
       Cursor cursor = db.rawQuery(query, null);
       cursor.moveToFirst();
       if( cursor != null && cursor.moveToFirst() ){
           num = cursor.getString(cursor.getColumnIndex(COL_4));

       }
       cursor.close();


       return num;
    }
    public boolean printTableData3(int ID){
        RANKNAME = "";
        RANKIMGID = 0;
        POINTSREQ = 0;

        SQLiteDatabase db = getReadableDatabase();

        Cursor cur = db.rawQuery("SELECT * FROM " + TABLE3_NAME + " WHERE ID =  " + ID, null);


        if(cur.getCount() != 0){
            cur.moveToFirst();





            do{

                RANKNAME = cur.getString(cur.getColumnIndex("NAME"));
                RANKIMGID = cur.getInt(cur.getColumnIndex("IMAGEID"));
                POINTSREQ = cur.getInt(cur.getColumnIndex("POINTSREQ"));

            }while(cur.moveToNext());
            cur.close();
        }
        return true;

    }

    @Override
    public long getCounted(String table) {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, table);
        db.close();
        return count;
    }
}
