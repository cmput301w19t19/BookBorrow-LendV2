package com.example.y.bookborrow_lendv2;

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
public class HomePageActivityTest extends ActivityTestRule<home_page> {

    private Solo solo;

    public HomePageActivityTest(){
        super(home_page.class);
    }

    @Rule
    public ActivityTestRule<home_page> rule =
            new ActivityTestRule<>(home_page.class,true,true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(),rule.getActivity());
    }

    @Test
    public void start() throws Exception{

        Activity activity = rule.getActivity();
    }

    @Test
    public void clickowner(){





        solo.assertCurrentActivity("wrong activity",home_page.class);
        solo.clickOnText("Owner");
        solo.assertCurrentActivity("owner home page",OwnerHomeActivity.class);
        solo.goBack();

        solo.assertCurrentActivity("wrong activity",home_page.class);
        solo.clickOnText("Borrower");
        solo.assertCurrentActivity("Borrower home page",BorrowerMenu.class);
        solo.goBack();

        solo.assertCurrentActivity("wrong activity",home_page.class);
        solo.clickOnText("Profile");
        solo.assertCurrentActivity("profile page",profile.class);
        solo.goBack();

        solo.assertCurrentActivity("wrong activity",home_page.class);
        solo.clickOnText("Sign Out");
        solo.assertCurrentActivity("login page",loginAct.class);
        solo.goBack();

    }

}
