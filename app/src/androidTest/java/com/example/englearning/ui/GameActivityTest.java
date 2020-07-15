package com.example.englearning.ui;

import android.content.res.Configuration;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.test.rule.ActivityTestRule;

import com.example.englearning.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


import static org.junit.Assert.*;

public class GameActivityTest {

    @Rule
    public ActivityTestRule<GameActivity> gameActivityActivityTestRule = new ActivityTestRule<GameActivity>(GameActivity.class);

    private GameActivity gameActivity = null;

    @Before
    public void setUp() throws Exception {

        gameActivity = gameActivityActivityTestRule.getActivity();
    }

    @Test
    public void checkViews(){

        ImageView imageView = gameActivity.findViewById(R.id.mainImageView);
        TextView hiddenText = gameActivity.findViewById(R.id.keyHiddenTextView);
        LinearLayout lettersLayout = gameActivity.findViewById(R.id.lettersLayout);
        LinearLayout lettersLayout2 = gameActivity.findViewById(R.id.lettersLayout2);
        LinearLayout lettersLayout3 = gameActivity.findViewById(R.id.lettersLayout3);
        LinearLayout lettersLayout4 = gameActivity.findViewById(R.id.lettersLayout4);
        int orientation = gameActivity.getResources().getConfiguration().orientation;

        assertNotNull(imageView);
        assertNotNull(hiddenText);
        assertNotNull(lettersLayout);
        assertNotNull(lettersLayout2);
        assertNotNull(lettersLayout3);
        assertNotNull(lettersLayout4);
        assertNotNull(hiddenText);


        assertTrue(orientation == Configuration.ORIENTATION_PORTRAIT);

    }

    @After
    public void tearDown() throws Exception {

        gameActivity = null;
    }
}