package com.example.y.bookborrow_lendv2;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.auth.FirebaseAuth;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

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

    //@Before
    //public void setUp() throws

}