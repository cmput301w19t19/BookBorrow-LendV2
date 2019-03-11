package com.example.y.bookborrow_lendv2;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.widget.EditText;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

public class profileTest extends ActivityTestRule<profile> {

    private Solo solo;

    public profileTest(){
        super(profile.class);
    }

    @Rule
    public ActivityTestRule<profile>rule =
            new ActivityTestRule<>(profile.class,true,true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), rule.getActivity());
    }

    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }

    @Test
    public void name(){
        solo.assertCurrentActivity("Wrong Activity",EditBookDetail.class);
        solo.enterText((EditText) solo.getView(R.id.InputName), "Name");
        solo.clickOnButton("Save");
        assertTrue(solo.waitForText("Name",1,2000));
    }

    @Test
    public void email(){
        solo.assertCurrentActivity("Wrong Activity",EditBookDetail.class);
        solo.enterText((EditText) solo.getView(R.id.InputEmail), "email");
        solo.clickOnButton("Save");
        assertTrue(solo.waitForText("email",1,2000));
    }

    @Test
    public void Phone(){
        solo.assertCurrentActivity("Wrong Activity",EditBookDetail.class);
        solo.enterText((EditText) solo.getView(R.id.InputPhone), "Phone");
        solo.clickOnButton("Save");
        assertTrue(solo.waitForText("Phone",1,2000));
    }

    @Test
    public void message(){
        solo.assertCurrentActivity("Wrong Activity",EditBookDetail.class);
        solo.enterText((EditText) solo.getView(R.id.InputMessage), "message");
        solo.clickOnButton("Save");
        assertTrue(solo.waitForText("message",1,2000));
    }






}