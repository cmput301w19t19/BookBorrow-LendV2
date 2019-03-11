package com.example.y.bookborrow_lendv2;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

public class PrivateBookDetailsTest extends ActivityTestRule<PrivateBookDetails> {

    private Solo solo;

    public PrivateBookDetailsTest(){
        super(PrivateBookDetails.class);
    }

    @Rule
    public ActivityTestRule<PrivateBookDetails>rule =
            new ActivityTestRule<>(PrivateBookDetails.class,true,true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), rule.getActivity());
    }

    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }

    @Test
    public void ClickOnEditButton(){
        solo.assertCurrentActivity("Wrong Activity",PrivateBookDetails.class);
        solo.clickOnButton("Edit");
        solo.assertCurrentActivity("Wrong Activity",EditBookDetail.class);
        solo.goBack();
    }

    @Test
    public void ClickOnRequestedButton(){
        solo.assertCurrentActivity("Wrong Activity",PrivateBookDetails.class);
        solo.clickOnButton("Requested");
        solo.assertCurrentActivity("Wrong Activity",RequestToOwner.class);
        solo.goBack();
    }

    @Test
    public void ClickOnReturnButton(){
        solo.assertCurrentActivity("Wrong Activity",PrivateBookDetails.class);
        solo.clickOnButton("return");
        solo.assertCurrentActivity("Wrong Activity",MyBookList.class);
    }


    @Test
    public void ClickOnDeleteButton(){
        solo.assertCurrentActivity("Wrong Activity",PrivateBookDetails.class);
        solo.clickOnButton("delete");
        solo.assertCurrentActivity("Wrong Activity",MyBookList.class);
    }




}