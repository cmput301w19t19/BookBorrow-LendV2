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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
public class SearchResultForBookTest extends ActivityTestRule<SearchResultForBook> {

    private Solo solo;
    public SearchResultForBookTest(){
        super(SearchResultForBook.class);
    }

    @Rule
    public ActivityTestRule<SearchResultForBook> rule=
            new ActivityTestRule<>(SearchResultForBook.class, true,true);



    @Before
    public void setUp() throws Exception {
        solo= new Solo(getInstrumentation(),rule.getActivity());
    }

    @Test
    public void start() throws Exception{
        Activity activity= rule.getActivity();
    }
    @Test
    public void checkBookInfo(){
        solo.assertCurrentActivity("Wrong Activity",SearchResultForBook.class);
        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity",PublicBookDetails.class);

        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", SearchResultForBook.class);
    }
    @Test
    public void checkNewSearch(){
        solo.assertCurrentActivity("Wrong Activity",SearchResultForBook.class);
        solo.clickOnButton("NewSearch1");
        assertTrue(solo.waitForText("Test Keyword", 1,2000));
        solo.assertCurrentActivity("Wrong Activity", SearchResultForBook.class);
    }

    @Test
    public void checkReturnHomePage(){
        solo.assertCurrentActivity("Wrong Activity", SearchResultForBook.class);
        solo.clickOnButton("HomeButton1");
        solo.assertCurrentActivity("Wrong Activity", home_page.class);
    }
    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}