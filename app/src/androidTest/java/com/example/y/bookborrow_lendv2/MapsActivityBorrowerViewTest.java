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
public class MapsActivityBorrowerViewTest extends ActivityTestRule<MapsActivityBorrowerView> {

    private Solo solo;


    public MapsActivityBorrowerViewTest(){
        super(MapsActivityBorrowerView.class);
    }

    @Rule
    public ActivityTestRule<MapsActivityBorrowerView>rule =
            new ActivityTestRule<>(MapsActivityBorrowerView.class,true,true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(),rule.getActivity());
    }

    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }


    @Test
    public void click(){

        solo.assertCurrentActivity("Wrong Activity",MapsActivityBorrowerView.class);

        solo.searchText("red");
    }

}
