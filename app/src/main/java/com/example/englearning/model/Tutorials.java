package com.example.englearning.model;

import com.example.englearning.R;

public class Tutorials {

    public Tutorial[] tutorials;

    public Tutorials(){

        tutorials = new Tutorial[10];

        tutorials[0] = new Tutorial(R.drawable.flag, R.string.tutTitle1, R.string.tutDesc1);
        tutorials[1] = new Tutorial(R.drawable.step0, R.string.tutTitle2, R.string.tutDesc2);
        tutorials[2] = new Tutorial(R.drawable.step1, R.string.tutTitle3, R.string.tutDesc3);
        tutorials[3] = new Tutorial(R.drawable.step2, R.string.tutTitle4, R.string.tutDesc4);
        tutorials[4] = new Tutorial(R.drawable.step3, R.string.tutTitle5, R.string.tutDesc5);
        tutorials[5] = new Tutorial(R.drawable.step4, R.string.tutTitle6, R.string.tutDesc6);
        tutorials[6] = new Tutorial(R.drawable.step5, R.string.tutTitle7, R.string.tutDesc7);
        tutorials[7] = new Tutorial(R.drawable.step6, R.string.tutTitle8, R.string.tutDesc8);
        tutorials[8] = new Tutorial(R.drawable.dictionary, R.string.tutTitle9, R.string.tutDesc9);
        tutorials[9] = new Tutorial(R.drawable.step7, R.string.tutTitle10, R.string.tutDesc10);
    }

    public Tutorial getTutorial(int pageNumber){
        return tutorials[pageNumber];
    }
}
