package com.example.y.bookborrow_lendv2_refactored;

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

@RunWith(AndroidJUnit4.class)
public class BorrowerMenuTest extends ActivityTestRule<BorrowerMenu> {

    private Solo solo;

    public BorrowerMenuTest(){

        super(BorrowerMenu.class);
    }

    @Rule
    public ActivityTestRule<BorrowerMenu> rule =
            new ActivityTestRule<>(BorrowerMenu.class,true,true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(),rule.getActivity());
    }

    @Test
    public void start() throws Exception{

        Activity activity = rule.getActivity();
    }

    @Test
    public void test(){

        solo.assertCurrentActivity("wrong activity",BorrowerMenu.class);
        solo.clickOnText("Book Requested");
        solo.assertCurrentActivity("br page",BorrowerRequest.class);
        solo.goBack();

        solo.assertCurrentActivity("wrong activity",BorrowerMenu.class);
        solo.clickOnText("My Book List");
        solo.assertCurrentActivity("my book list page",BorrowBookList.class);
        solo.goBack();

        solo.assertCurrentActivity("wrong activity",BorrowerMenu.class);
        solo.clickOnText("Searching");
        solo.assertCurrentActivity("profile page",Search.class);
        solo.goBack();

        //solo.assertCurrentActivity("wrong activity",BorrowerMenu.class);
        //solo.clickOnText("Scan");
        //solo.assertCurrentActivity("login page",loginAct.class);
        //solo.goBack();

    }

    @After
    public void tearDown() throws Exception{

        solo.finishOpenedActivities();
    }



}


