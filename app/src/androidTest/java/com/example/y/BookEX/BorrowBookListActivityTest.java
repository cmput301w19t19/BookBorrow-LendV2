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
public class BorrowBookListActivityTest extends ActivityTestRule<BorrowBookList> {


    private Solo solo;

    public BorrowBookListActivityTest(){

        super(BorrowBookList.class);
    }

    @Rule
    public ActivityTestRule<BorrowBookList> rule =
            new ActivityTestRule<>(BorrowBookList.class,true,true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(),rule.getActivity());
    }

    @Test
    public void start() throws Exception{

        Activity activity = rule.getActivity();
    }

    @Test
    public void listviewtest(){

        solo.assertCurrentActivity("w",BorrowBookList.class);
        //solo.clickOnView();
        //solo.assertCurrentActivity("wrong",PublicBookDetails.class);
        solo.goBack();
    }


}
