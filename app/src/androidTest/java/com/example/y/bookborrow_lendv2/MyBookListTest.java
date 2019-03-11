package com.example.y.bookborrow_lendv2;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.auth.FirebaseAuth;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class MyBookListTest extends ActivityTestRule<MyBookList> {
    private Solo solo;
    private FirebaseAuth auth;

    public MyBookListTest(){
        super(MyBookList.class);
    }

    @Rule
    public ActivityTestRule<MyBookList> rule = new ActivityTestRule<>(MyBookList.class,true,true);


    @Before
    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(),rule.getActivity());
    }

    @Test
    public void start() throws Exception{

        Activity activity = rule.getActivity();
    }

    @Test
    public void list(){
        solo.assertCurrentActivity("wrong activity",MyBookList.class);
        solo.clickInList(0);
        solo.assertCurrentActivity("..",PrivateBookDetails.class);
        solo.goBack();

    }

    @Test
    public void button(){
        solo.assertCurrentActivity("wrong activity",MyBookList.class);
        solo.clickOnText("available");
        solo.searchText("available");

        solo.clickOnText("requested");
        solo.clickOnText("accepted");

        solo.clickOnImageButton(0);
    }



}
