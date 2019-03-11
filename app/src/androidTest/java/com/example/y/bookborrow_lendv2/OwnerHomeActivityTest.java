package com.example.y.bookborrow_lendv2;

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
public class OwnerHomeActivityTest extends ActivityTestRule<OwnerHomeActivity> {

    private Solo solo;

    public OwnerHomeActivityTest(){

        super(OwnerHomeActivity.class);
    }

    @Rule
    public ActivityTestRule<OwnerHomeActivity> rule =
            new ActivityTestRule<>(OwnerHomeActivity.class,true,true);

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

        solo.assertCurrentActivity("wrong activity",OwnerHomeActivity.class);
        solo.clickOnText("My Book List");
        solo.assertCurrentActivity("br page",MyBookList.class);
        solo.goBack();

        solo.assertCurrentActivity("wrong activity",OwnerHomeActivity.class);
        solo.clickOnText("Search");
        solo.assertCurrentActivity("br page",Search.class);
        solo.goBack();


    }

    @After
    public void tearDown() throws Exception{

        solo.finishOpenedActivities();
    }


}
