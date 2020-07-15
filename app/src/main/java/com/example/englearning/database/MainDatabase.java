package com.example.englearning.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import androidx.annotation.Nullable;

import com.example.englearning.R;
import com.example.englearning.ui.DictGameActivity;
import com.example.englearning.ui.MainActivity;

import static com.example.englearning.ui.GameActivity.TAG;


public class MainDatabase extends SQLiteOpenHelper {

    protected static final String DATABASE_NAME = "keyword.db";
    protected static final String TABLE_NAME = "keywords_table";
    protected static final String COL_1 = "ID";
    protected static final String COL_2 = "KEYWORD";
    protected static final String COL_3 = "IMAGEID";
    protected static final String COL_4 = "TRANSLATION";
    protected static final String TABLE2_NAME = "failures_table";
    protected static final String COL11 = "ID";
    protected static final String TABLE3_NAME = "stats_table";
DictGameActivity dictGameActivity;
    private String KEYW;
    private int IMGID;



    private int SOUND;
   private int PRO;

    public int getLEVEL() {
        return LEVEL;
    }

    private int LEVEL;
  private  String TRANS;
    public int getPRO() {
        return PRO;
    }

    public MainDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 126);
        SQLiteDatabase db = this.getWritableDatabase();


    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        dictGameActivity = new DictGameActivity();
        db.execSQL("create table " + TABLE_NAME + " (KEYWORD TEXT NOT NULL, IMAGEID TEXT NOT NULL, TRANSLATION TEXT NOT NULL, SOUND TEXT, PRONUNCIATION TEXT NOT NULL, LEVEL INTEGER NOT NULL)");
        db.execSQL("create table " + TABLE2_NAME + " (KEYWORD TEXT NOT NULL, IMAGEID TEXT NOT NULL, TRANSLATION TEXT, SOUND TEXT NOT NULL, PRONUNCIATION TEXT NOT NULL)");
        db.execSQL("create table " + TABLE3_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT NOT NULL, POINTSREQ INTEGER NOT NULL, IMAGEID TEXT NOT NULL)");

insertWords(db);




        insertRank(db,"Początkujący" , 20, R.drawable.rank1);
        insertRank(db,"Wprawiony" , 60, R.drawable.rank2);
        insertRank(db,"Fachowiec " , 100, R.drawable.rank3);
        insertRank(db, "Zaawansowany" , 140, R.drawable.rank4);
        insertRank( db,"Specjalista" , 170, R.drawable.rank5);
        insertRank( db,"Zawodowiec" , 200, R.drawable.rank6);
        insertRank(db,"Mędrzec" , 220, R.drawable.rank7);
        insertRank(db, "Ekspert" , 250, R.drawable.rank8);
        insertRank(db, "Mistrz angielskiego" , 280, R.drawable.rank9);


    }

    protected void insertWords(SQLiteDatabase db){





        insertColumn(db,"frog", R.drawable.soundicon, "żaba", R.raw.frog, R.raw.frogprononunce, 1);
        insertColumn( db,"horse", R.drawable.horse, "koń", 0, R.raw.horsepronounce, 1);
        insertColumn(db, "backpack", R.drawable.image1, "plecak",0 , R.raw.backpack, 2);
        insertColumn(db,"notebook", R.drawable.image2, "notatnik", 0, R.raw.notebook, 2);
        insertColumn(db,"cloud", R.drawable.image3, "chmura", 0, R.raw.cloud, 2);
        insertColumn(db, "hat", R.drawable.image4, "czapka", 0, R.raw.hat, 1);
        insertColumn(db, "snowman", R.drawable.image5, "bałwan" , 0, R.raw.snowman, 3);

        insertColumn( db,"watermelon", R.drawable.watermelon, "arbuz" , 0, R.raw.watermelon, 1);
        insertColumn( db, "coach" , R.drawable.coach, "autokar" , 0, R.raw.coach, 2);
        insertColumn( db, "stork" , R.drawable.stork, "bocian" , 0, R.raw.stork, 2);
        insertColumn( db, "bouquet" , R.drawable.bouquet, "bukiet" , 0, R.raw.bouquet, 2);
        insertColumn( db, "brick" , R.drawable.brick, "cegła" , 0, R.raw.brick, 2);
        insertColumn( db, "onion" , R.drawable.onion, "cebula" , 0, R.raw.onion, 1);
        insertColumn( db, "cheetah" , R.drawable.cheetah, "gepard" , 0, R.raw.cheetah, 3);
        insertColumn( db, "cookie" , R.drawable.cookie, "ciastko" , 0, R.raw.cookie, 1);
        insertColumn( db, "house" , R.drawable.house, "dom" , 0, R.raw.house, 1);
        insertColumn( db, "tree" , R.drawable.tree, "drzewo" , 0, R.raw.tree, 1);
        insertColumn( db, "boar" , R.drawable.boar, "dzik" , 0, R.raw.boar, 2);
        insertColumn( db, "apron" , R.drawable.apron, "fartuch" , 0, R.raw.apron, 3);
        insertColumn( db, "cup" , R.drawable.cup, "filiżanka" , 0, R.raw.cup, 1);
        insertColumn( db, "pot" , R.drawable.pot, "garnek" , 0, R.raw.pot, 1);
        insertColumn( db, "star" , R.drawable.star, "gwiazda" , 0, R.raw.star, 1);
        insertColumn( db, "needle" , R.drawable.needle, "igła" , 0, R.raw.needle, 2);
        insertColumn( db, "cucurbit" , R.drawable.cucurbit, "kabaczek", 0, R.raw.cucurbit, 3);
        insertColumn( db, "lavender" , R.drawable.lavender, "lawenda" , 0, R.raw.lavender, 3);
        insertColumn( db, "brain" , R.drawable.brain, "mózg" , 0, R.raw.brain, 1);
        insertColumn( db, "watercolors" , R.drawable.watercolors, "akwarele" , 0, R.raw.watercolors, 3);
        insertColumn( db, "egg" , R.drawable.egg, "jajko" , 0, R.raw.egg, 1);
        insertColumn( db, "icon" , R.drawable.icon, "ikona" , 0, R.raw.icon, 1);
        insertColumn( db, "bell" , R.drawable.bell, "dzwonek" , 0, R.raw.bell, 2);
        insertColumn( db, "banana" , R.drawable.banana, "banan" , 0, R.raw.banana, 1);
        insertColumn( db, "binoculars" , R.drawable.binoculars, "lornetka" , 0, R.raw.binoculars, 3);
        insertColumn( db, "sandwich" , R.drawable.sandwich, "kanapka" , 0, R.raw.sandwich, 2);
        insertColumn( db, "beads" , R.drawable.beads, "koraliki" , 0, R.raw.beads, 3);
        insertColumn( db, "truck" , R.drawable.truck, "ciężarówka" , 0, R.raw.truck, 2);
        insertColumn( db, "mandarin" , R.drawable.mandarin, "mandarynka" , 0, R.raw.mandarin, 1);
        insertColumn( db, "microwave" , R.drawable.microwave, "mikrofalówka" , 0, R.raw.microwave, 2);
        insertColumn( db, "tea" , R.drawable.tea, "herbata" , 0, R.raw.tea, 1);
        insertColumn( db, "sugar" , R.drawable.sugar, "cukier" , 0, R.raw.sugar, 1);
        insertColumn( db, "door" , R.drawable.door, "drzwi" , 0, R.raw.door, 1);
        insertColumn( db, "pasta" , R.drawable.pasta, "makaron" , 0, R.raw.pasta, 1);
        insertColumn( db, "cough" , R.drawable.soundicon, "kaszel" , R.raw.caughsound, R.raw.caugh, 1);
        insertColumn( db, "chair" , R.drawable.soundicon, "krzesło" , R.raw.chairsound, R.raw.chair, 1);
        insertColumn( db, "cry" , R.drawable.soundicon, "płacz" , R.raw.crysound, R.raw.cry, 1);
        insertColumn( db, "horn" , R.drawable.soundicon, "klakson" , R.raw.hornsound, R.raw.horn, 2);
        insertColumn( db, "kettle" , R.drawable.soundicon, "czajnik" , R.raw.kettlesound, R.raw.kettle, 2);
        insertColumn( db, "keyboard" , R.drawable.soundicon, "klawiatura" , R.raw.keyboardsound, R.raw.keyboard, 1);
        insertColumn( db, "plane" , R.drawable.soundicon, "samolot" , R.raw.planesound, R.raw.plane, 3);
        insertColumn( db, "sheep" , R.drawable.soundicon, "owca" , R.raw.sheepsound, R.raw.sheep, 1);
        insertColumn( db, "train" , R.drawable.soundicon, "pociąg" , R.raw.trainsound, R.raw.train, 1);
        insertColumn( db, "printer" , R.drawable.soundicon, "drukarka" , R.raw.printersound, R.raw.printer, 2);
        insertColumn( db, "bee" , R.drawable.soundicon, "pszczoła" , R.raw.beesound, R.raw.bee, 2);

        insertColumn( db,"belt", R.drawable.belt, "pasek", 0, R.raw.belt, 1);
        insertColumn( db,"bike", R.drawable.bike, "rower", 0, R.raw.bike, 1);
        insertColumn( db,"ear", R.drawable.ear, "ucho", 0, R.raw.ear, 1);
        insertColumn( db,"grass", R.drawable.grass, "trawa", 0, R.raw.grass, 1);
        insertColumn( db,"hatrack", R.drawable.hatrack, "wieszak", 0, R.raw.hatrack, 3);
        insertColumn( db,"heart", R.drawable.heart, "serce", 0, R.raw.heart, 1);
        insertColumn( db,"knife", R.drawable.knife, "nóż", 0, R.raw.knife, 1);
        insertColumn( db,"painting", R.drawable.painting, "obraz", 0, R.raw.painting, 1);
        insertColumn( db,"bag", R.drawable.bag, "torebka", 0, R.raw.bag, 1);
        insertColumn( db,"moustache", R.drawable.moustache, "wąsy", 0, R.raw.moustache, 2);
        insertColumn( db,"rocket", R.drawable.rocket, "rakieta", 0, R.raw.rocket, 2);
        insertColumn( db,"rose", R.drawable.rose, "róża", 0, R.raw.rose, 1);
        insertColumn( db,"ship", R.drawable.ship, "statek", 0, R.raw.ship, 2);
        insertColumn( db,"skis", R.drawable.skis, "narty", 0, R.raw.skis, 3);
        insertColumn( db,"steak", R.drawable.steak, "stek", 0, R.raw.steak, 2);
        insertColumn( db,"tire", R.drawable.tire, "opona", 0, R.raw.tire, 2);
        insertColumn( db,"tomato", R.drawable.tomato, "pomidor", 0, R.raw.tomato, 1);
        insertColumn( db,"tooth", R.drawable.tooth, "ząb", 0, R.raw.tooth, 2);
        insertColumn( db,"trainers", R.drawable.trainers, "trampki", 0, R.raw.trainers, 3);
        insertColumn( db,"zebra", R.drawable.zebra, "zebra", 0, R.raw.zebra, 1);
        insertColumn( db,"glasses", R.drawable.glasses, "okulary", 0, R.raw.glasses, 2);
        insertColumn( db,"urn", R.drawable.urn, "urna", 0, R.raw.urn, 3);
        insertColumn( db,"sausages", R.drawable.sausages, "parówki", 0, R.raw.sausages, 2);
        insertColumn( db,"reindeer", R.drawable.reindeer, "renifer", 0, R.raw.reindeer, 3);
        insertColumn( db,"dress", R.drawable.dress, "sukienka", 0, R.raw.dress, 1);
        insertColumn( db,"mouth", R.drawable.mouth, "usta", 0, R.raw.mouth, 2);
        insertColumn( db,"toaster", R.drawable.toaster, "toster", 0, R.raw.toaster, 1);
        insertColumn( db,"dragonfly", R.drawable.dragonfly, "ważka", 0, R.raw.dragonfly, 3);
        insertColumn( db,"telephone", R.drawable.telephone, "telefon", 0, R.raw.telephone, 2);
        insertColumn( db,"stone", R.drawable.stone, "skała", 0, R.raw.stone, 1);
        insertColumn( db,"clock", R.drawable.clock, "zegar", 0, R.raw.clock, 1);
        insertColumn( db,"umbrella", R.drawable.umbrella, "parasolka", 0, R.raw.umbrella, 2);
        insertColumn( db,"robot", R.drawable.robot, "robot", 0, R.raw.robot, 1);
        insertColumn( db,"superstitions", R.drawable.superstitions, "przesądy", 0, R.raw.superstitions, 3);
        insertColumn( db, "rain", R.drawable.soundicon, "deszcz", R.raw.rainsound, R.raw.rain, 1);
        insertColumn( db, "dog", R.drawable.soundicon, "pies", R.raw.dogsound, R.raw.dog, 1);
        insertColumn( db, "violin", R.drawable.soundicon, "wiolonczela", R.raw.violinsound, R.raw.violin, 3);
        insertColumn( db, "applause", R.drawable.soundicon, "oklaski", R.raw.applausesound, R.raw.applause, 3);
        insertColumn( db, "engine", R.drawable.soundicon, "silnik", R.raw.enginesound, R.raw.engine, 2);
        insertColumn( db, "drill", R.drawable.soundicon, "wiertarka", R.raw.drillsound, R.raw.drill, 2);
        insertColumn( db, "wind", R.drawable.soundicon, "wiatr", R.raw.windsound, R.raw.wind, 2);
        insertColumn( db, "saxophone", R.drawable.soundicon, "saksofon", R.raw.saxophonesound, R.raw.saxophone, 3);
        insertColumn( db, "tambourine", R.drawable.soundicon, "tamburyno", R.raw.tambourinesound, R.raw.tambourine, 3);
        insertColumn( db, "drums", R.drawable.soundicon, "perkusja", R.raw.drumssound, R.raw.drums, 2);







    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE2_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE3_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + "keyword_table");

        onCreate(db);
    }
    public boolean insertRank(SQLiteDatabase db, String name, int points, int imageid){

        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", name);
        contentValues.put("POINTSREQ", points);
        contentValues.put("IMAGEID", imageid);
        long result = db.insert(TABLE3_NAME, null, contentValues);

        if(result == -1){

            return  false;
        }else{

            return  true;
        }



    }


    public boolean insertData(SQLiteDatabase db, String key, int img, String translation){

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, key);
        contentValues.put(COL_3, img);
        contentValues.put(COL_4, translation);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1){

            return  false;
        }else{

            return  true;
        }

    }
    public boolean insertColumn(SQLiteDatabase db, String key, int img, String translation, int sound, int pronunciation, int level){

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, key);
        contentValues.put(COL_3, img);
        contentValues.put(COL_4, translation);
        contentValues.put("SOUND", sound);
        contentValues.put("PRONUNCIATION", pronunciation);
        contentValues.put("LEVEL", level);
        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1){

            return  false;

        }else{

            return  true;
        }

    }
    public int getSOUND() {
        return SOUND;
    }


    public String getKEYW() {
        return KEYW;
    }

    public int getIMGID() {
        return IMGID;
    }






    public void moveWord(String key){

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "insert into " +  TABLE2_NAME + " ( KEYWORD, IMAGEID, TRANSLATION, SOUND, PRONUNCIATION) SELECT KEYWORD, IMAGEID, TRANSLATION, SOUND, PRONUNCIATION FROM " +
                TABLE_NAME + " WHERE KEYWORD = " + "'" + key.toLowerCase() + "'";

        db.execSQL(query);

        String query2 = "delete from " + TABLE_NAME + " WHERE KEYWORD = " + "'" + key.toLowerCase() + "'";
        db.execSQL(query2);
        db.close();



    }
    public String printTableData(){
        SQLiteDatabase db = getReadableDatabase();
        String row_values= "";
        Cursor cur = db.rawQuery("SELECT KEYWORD FROM " + TABLE2_NAME, null);
        Cursor cur2 = db.rawQuery("SELECT TRANSLATION FROM " + TABLE2_NAME, null);

        if(cur.getCount() != 0 && cur2.getCount() != 0){
            cur.moveToFirst();
            cur2.moveToFirst();

            do{


                for(int i = 0 ; i < cur.getColumnCount(); i++){
                    row_values = row_values+ cur.getString(i) + " - " + cur2.getString(i);
                }
                row_values+="\n";


            }while (cur.moveToNext() && cur2.moveToNext());
        }
        cur.close();
        cur2.close();
        db.close();
        return row_values;
    }
    public boolean printTableData2(String table){

        KEYW = "";
        IMGID = 0;
        TRANS = "";
        SOUND = 0;
        PRO = 0;
        LEVEL = 0;
        SQLiteDatabase db = getReadableDatabase();

        Cursor cur = db.rawQuery("SELECT * FROM " + table + " ORDER BY RANDOM() LIMIT 1 ", null);


        if(cur.getCount() != 0){
            cur.moveToFirst();





            do{

                KEYW = cur.getString(cur.getColumnIndex("KEYWORD"));

                IMGID = cur.getInt(cur.getColumnIndex("IMAGEID"));


                TRANS = cur.getString(cur.getColumnIndex("TRANSLATION"));
                SOUND = cur.getInt(cur.getColumnIndex("SOUND"));
                PRO = cur.getInt(cur.getColumnIndex("PRONUNCIATION"));
                if(table.equals("keywords_table")){
                    LEVEL = cur.getInt(cur.getColumnIndex("LEVEL"));
                }




            }while(cur.moveToNext());
            cur.close();
            db.close();
        }
        return true;

    }
    public boolean printTableDataL(String table, int level){

        KEYW = "";
        IMGID = 0;
        TRANS = "";
        SOUND = 0;
        PRO = 0;

        SQLiteDatabase db = getReadableDatabase();

        Cursor cur = db.rawQuery("SELECT * FROM " + table + " WHERE LEVEL = " + level + " ORDER BY RANDOM() LIMIT 1 ", null);


        if(cur.getCount() != 0){
            cur.moveToFirst();





            do{

                KEYW = cur.getString(cur.getColumnIndex("KEYWORD"));

                IMGID = cur.getInt(cur.getColumnIndex("IMAGEID"));


                TRANS = cur.getString(cur.getColumnIndex("TRANSLATION"));
                SOUND = cur.getInt(cur.getColumnIndex("SOUND"));
                PRO = cur.getInt(cur.getColumnIndex("PRONUNCIATION"));




            }while(cur.moveToNext());
            cur.close();
            db.close();
        }
        return true;

    }
    public int getThisSound(String table, String keyword){
        SQLiteDatabase db = this.getWritableDatabase();
        int num = 0;
        String query2 = "select SOUND from " + table + " WHERE  KEYWORD = " + "'" + keyword.toLowerCase() + "'";
        Cursor cursor2 = db.rawQuery(query2, null);
        cursor2.moveToFirst();
        if( cursor2 != null && cursor2.moveToFirst() ){
            num = cursor2.getInt(cursor2.getColumnIndex("SOUND"));


        }
        cursor2.close();
db.close();

        return num;


    }
    public String getWord(String table, int level){
        SQLiteDatabase db = this.getWritableDatabase();
        String num = "";
        String query = "select KEYWORD from " + table + " WHERE  LEVEL = " + level + " ORDER BY RANDOM() limit 1 ";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if( cursor != null && cursor.moveToFirst() ){
            num = cursor.getString(cursor.getColumnIndex("KEYWORD"));

        }
        cursor.close();
        db.close();


        return num;
    }
    public int getThisPro(String table, String keyword){
        SQLiteDatabase db = this.getWritableDatabase();
        int num = 0;
        String query2 = "select PRONUNCIATION from " + table + " WHERE  KEYWORD = " + "'" + keyword.toLowerCase() + "'";
        Cursor cursor2 = db.rawQuery(query2, null);
        cursor2.moveToFirst();
        if( cursor2 != null && cursor2.moveToFirst() ){
            num = cursor2.getInt(cursor2.getColumnIndex("PRONUNCIATION"));


        }
        cursor2.close();
        db.close();

        return num;


    }
    public int getImageID(String table, String keyword){
        SQLiteDatabase db = this.getWritableDatabase();
        int num = 0;
        String query2 = "select IMAGEID from " + table + " WHERE  KEYWORD = " + "'" + keyword.toLowerCase() + "'";
        Cursor cursor2 = db.rawQuery(query2, null);
        cursor2.moveToFirst();
        if( cursor2 != null && cursor2.moveToFirst() ){
            num = cursor2.getInt(cursor2.getColumnIndex("IMAGEID"));


        }
        cursor2.close();
        db.close();

        return num;


    }
    public long getCounted(String table) {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, table);
        db.close();
        return count;
    }
    public long getCountedByLevel(String table, int level) {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, table, " LEVEL= " + level);
        db.close();
        return count;
    }


    public String getTranslation(String table, String keyword){
        SQLiteDatabase db = this.getWritableDatabase();
        String num = "";
        String query = "select TRANSLATION from " + table + " WHERE  KEYWORD = " + "'" + keyword.toLowerCase() + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if( cursor != null && cursor.moveToFirst() ){
            num = cursor.getString(cursor.getColumnIndex(COL_4));

        }
        cursor.close();
        db.close();


        return num;
    }

    public void deleteRecord(String table, String keyword){
        SQLiteDatabase db = this.getWritableDatabase();

        String query2 = "delete from " + table + " WHERE KEYWORD = " + "'" + keyword.toLowerCase() + "'";
        db.execSQL(query2);
        db.close();
    }




    public void clearDictionary(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE2_NAME);
        db.close();
    }


}
