package com.example.y.BookEX;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

@RunWith(AndroidJUnit4.class)
public class PublicBookDetailsTest extends ActivityTestRule<PublicBookDetails> {

    private Solo solo;

    public PublicBookDetailsTest(){
        super(PublicBookDetails.class);
    }

    @Rule
    public ActivityTestRule<PublicBookDetails>rule =
            new ActivityTestRule<>(PublicBookDetails.class,true,true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), rule.getActivity());
    }

    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();

    }

    //


    @Test
    public void ClickOnRequestButton(){
        solo.assertCurrentActivity("Wrong Activity",PublicBookDetails.class);
        solo.clickOnButton("REQUEST IT!");
    }

    @Test
    public void ClickOnReteurnButton(){
        solo.clickOnButton("RETURN");
    }




}