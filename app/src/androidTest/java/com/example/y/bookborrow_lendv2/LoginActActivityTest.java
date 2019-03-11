package com.example.y.bookborrow_lendv2;


import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

@RunWith(AndroidJUnit4.class)
public class LoginActActivityTest extends ActivityTestRule<loginAct> {

    private Solo solo;

    public LoginActActivityTest(){
        super(loginAct.class);
    }

    @Rule
    public ActivityTestRule<loginAct> rule =
            new ActivityTestRule<>(loginAct.class,true,true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(),rule.getActivity());
    }

    @Test
    public void start() throws Exception{

        Activity activity = rule.getActivity();
    }

    @Test
    public void loginTest(){

        solo.assertCurrentActivity("wrong activity",loginAct.class);

        solo.enterText((EditText) solo.getView(R.id.loginEmail),"555@ualerta.ca");

        solo.enterText((EditText) solo.getView(R.id.password_editText), "123456");

        solo.clickOnButton("log in");

        solo.assertCurrentActivity("wrong activity",home_page.class);
    }

    @After
    public void tearDown() throws Exception{

        solo.finishOpenedActivities();
    }
}
