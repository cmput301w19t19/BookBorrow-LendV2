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
public class BorrowerRequestActivityTest extends ActivityTestRule<BorrowerRequest> {

    private Solo solo;

    public BorrowerRequestActivityTest(){
        super(BorrowerRequest.class);

    }

    @Rule
    public ActivityTestRule<BorrowerRequest> rule =
            new ActivityTestRule<>(BorrowerRequest.class,true,true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(),rule.getActivity());
    }

    @Test
    public void start() throws Exception{

        Activity activity = rule.getActivity();
    }

    /**
     * test click book listview
     * and jump to book detail activity
     */
    @Test
    public void clickBookList(){

        solo.assertCurrentActivity("wrong activity",BorrowerRequest.class);

        solo.clickInList(0);

        solo.assertCurrentActivity("should be book detail",PublicBookDetails.class);

    }

    @Test
    public void clickButton(){
        solo.assertCurrentActivity("wrong activity",BorrowerRequest.class);
        solo.clickOnButton("show accepted");
        solo.searchText("Element");  //there is a book title with element appear when accepted button click

        solo.clickOnButton("show requested");
        solo.searchText("111");

    }


    @After
    public void tearDown() throws Exception{

        solo.finishOpenedActivities();
    }




}
