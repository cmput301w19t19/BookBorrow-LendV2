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
public class RequestToOwnerActivityTest extends ActivityTestRule<RequestToOwner> {


    private Solo solo;

    public RequestToOwnerActivityTest() {
        super(RequestToOwner.class);
    }

    @Rule
    public ActivityTestRule<RequestToOwner> rule =
            new ActivityTestRule<>(RequestToOwner.class,true,true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(),rule.getActivity());
    }

    @Test
    public void start() throws Exception{

        Activity activity = rule.getActivity();
    }

    @Test
    public void button(){

        solo.assertCurrentActivity("wrong",RequestToOwner.class);
        solo.clickOnButton("accept");
        solo.clickOnButton("decline");

    }
}
