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

public class EditBookDetailTest extends ActivityTestRule<EditBookDetail> {

    private Solo solo;

    public EditBookDetailTest(){
        super(EditBookDetail.class);
    }

    @Rule
    public ActivityTestRule<EditBookDetail>rule =
            new ActivityTestRule<>(EditBookDetail.class,true,true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), rule.getActivity());
    }

    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }

    @Test
    public void description(){
        solo.assertCurrentActivity("Wrong Activity",EditBookDetail.class);
        solo.enterText((EditText) solo.getView(R.id.editTextDes), "description");
        solo.clickOnButton("Save");
        assertTrue(solo.waitForText("description",1,2000));
    }

    @Test
    public void name(){
        solo.assertCurrentActivity("Wrong Activity",EditBookDetail.class);
        solo.enterText((EditText) solo.getView(R.id.pt4), "Name");
        solo.clickOnButton("Save");
        assertTrue(solo.waitForText("Name",1,2000));
    }

    @Test
    public void ISBN(){
        solo.assertCurrentActivity("Wrong Activity",EditBookDetail.class);
        solo.enterText((EditText) solo.getView(R.id.pt3), "ISBN");
        solo.clickOnButton("Save");
        assertTrue(solo.waitForText("ISBN",1,2000));
    }

    @Test
    public void Author(){
        solo.assertCurrentActivity("Wrong Activity",EditBookDetail.class);
        solo.enterText((EditText) solo.getView(R.id.pt2), "Author");
        solo.clickOnButton("Save");
        assertTrue(solo.waitForText("Author",1,2000));
    }



}