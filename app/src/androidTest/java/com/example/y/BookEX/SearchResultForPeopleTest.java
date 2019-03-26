package com.example.y.BookEX;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class SearchResultForPeopleTest extends ActivityTestRule<SearchResultForPeople>{

    private Solo solo;

    public SearchResultForPeopleTest(){
        super (SearchResultForPeople.class);
    }

    @Rule
    public ActivityTestRule<SearchResultForPeople> rule=
            new ActivityTestRule<>(SearchResultForPeople.class, true,true);



    @Before
    public void setUp() throws Exception {
        solo= new Solo(getInstrumentation(),rule.getActivity());
    }

    @Test
    public void start() throws Exception{
        Activity activity= rule.getActivity();
    }

    @Test
    public void checkNewSearch(){
        solo.assertCurrentActivity("Wrong Activity",SearchResultForPeople.class);
        solo.clickOnButton("NewSearch1");
        assertTrue(solo.waitForText("Test Keyword", 1,2000));
        solo.assertCurrentActivity("Wrong Activity", SearchResultForPeople.class);
    }

    @Test
    public void checkReturnHomePage(){
        solo.assertCurrentActivity("Wrong Activity", SearchResultForPeople.class);
        solo.clickOnButton("HomeButton1");
        solo.assertCurrentActivity("Wrong Activity", SearchResultForPeople.class);
    }
    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}