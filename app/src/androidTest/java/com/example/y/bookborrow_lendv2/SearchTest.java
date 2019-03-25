package com.example.y.bookborrow_lendv2;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
public class SearchTest extends ActivityTestRule<Search> {
    private Solo solo;

    public SearchTest(){super (Search.class);}

    @Rule
    public ActivityTestRule<Search> rule=
            new ActivityTestRule<>(Search.class, true, true);

    @Before
    public void setup() throws Exception{
        solo= new Solo(getInstrumentation(), rule.getActivity());
    }

    @Test
    public void start() throws Exception{
        Activity activity= rule.getActivity();
    }

    @Test
    public void checkinput(){

        solo.assertCurrentActivity("Wrong Activity", Search.class);
        solo.enterText((EditText) solo.getView(R.id.keywordText), "Test Keyword!");

        solo.clickOnButton("See_Result_of_BookButton");
        assertTrue(solo.searchText("See_Result_of_BookButton"));

        assertTrue(solo.waitForText("Test Keyword!", 1, 2000));

        solo.assertCurrentActivity("Wrong Activity", SearchResultForBook.class);

        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", Search.class);



    }

    @Test
    public void checkinput_person(){
        solo.assertCurrentActivity("Wrong Activities", Search.class);
        solo.enterText((EditText) solo.getView(R.id.keywordText), "Test Keyword_person!");

        assertTrue(solo.searchText("See_Result_of_PersonButton"));
        solo.clickOnButton("See_Result_of_PersonButton");

        assertTrue(solo.waitForText("Test Keyword_person!", 1, 2000));

        solo.assertCurrentActivity("Wrong Activity", SearchResultForPeople.class);

        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", Search.class);

    }

    @After
    public void tearDown() throws Exception
    {
        solo.finishOpenedActivities();
    }
}
