package com.example.y.bookborrow_lendv2;
import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class LogInActivityTest  extends ActivityTestRule<loginAct> {

    private Solo solo;
    private FirebaseAuth auth;


    public LogInActivityTest(){
        super(loginAct.class);

    }

    @Rule
    public ActivityTestRule<loginAct> rule =
            new ActivityTestRule<>(loginAct.class,true,true);

    @Before
    public void setUp(){

        solo = new Solo(getInstrumentation(),rule.getActivity());

    }

    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }

    @Test
    public void EnterEmail(){
        signOutActivity activity = (signOutActivity) solo.getCurrentActivity();

        /**there is a user signed in, we will sign out the user
         * first, then got to the log in activity
        */
        //auth = FirebaseAuth.getInstance();
        //FirebaseUser user = auth.getCurrentUser();
        //String uid = user.getUid();

        //if (uid!=null){
        activity.signOut();

        //}

        solo.assertCurrentActivity("Wrong Activity",loginAct.class);
        solo.enterText((EditText)solo.getView(R.id.loginEmail),"555@ualberta.ca!");

        solo.clickOnButton("login_button");

        assertTrue(solo.waitForText("555@ualberta.ca!"));


    }
    @Test
    public void EnterPassword(){
        solo.assertCurrentActivity("Wrong Activity",loginAct.class);

        solo.enterText((EditText)solo.getView(R.id.password_editText),"123456");

        solo.clickOnButton("password_editText");

        assertTrue(solo.waitForText("123456"));


    }
    @Test
    public void LogIn(){
        //get the current logged in usesr ID
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        solo.assertCurrentActivity("Wrong Activity",loginAct.class);
        solo.enterText((EditText)solo.getView(R.id.loginEmail),"555@ualberta.ca!");

        solo.enterText((EditText)solo.getView(R.id.password_editText),"123456");

        solo.clickOnButton("login");
        String uid = user.getUid();

        assertEquals(uid,"v1rSbJgp2uPgAxf4ZJXcclTgDyv2");


    }




    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }




}
