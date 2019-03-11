package com.example.y.bookborrow_lendv2;
import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
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
public class LogInActivityTest  extends ActivityTestRule<LoginAct> {

    private Solo solo;
    private FirebaseAuth auth;


    public LogInActivityTest(){
        super(LoginAct.class);

    }

    @Rule
    public ActivityTestRule<LoginAct> rule =
            new ActivityTestRule<>(LoginAct.class,true,true);

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

        /**there is a User signed in, we will sign out the User
         * first, then got to the log in activity
        */
        //auth = FirebaseAuth.getInstance();
        //FirebaseUser User = auth.getCurrentUser();
        //String uid = User.getUid();

        //if (uid!=null){

        //}

        solo.assertCurrentActivity("Wrong Activity", LoginAct.class);
        solo.enterText((EditText)solo.getView(R.id.loginEmail),"555@ualberta.ca");

       // solo.clickOnButton("login_button");

        assertTrue(solo.waitForText("555@ualberta.ca"));


    }
    @Test
    public void EnterPassword(){
        solo.assertCurrentActivity("Wrong Activity", LoginAct.class);

        solo.enterText((EditText)solo.getView(R.id.password_editText),"123456");

      //  solo.clickOnButton("password_editText");

        assertTrue(solo.waitForText("123456"));


    }
    @Test
    public void LogIn(){
        LoginAct activity = (LoginAct) solo.getCurrentActivity();

        solo.assertCurrentActivity("Wrong Activity", LoginAct.class);
        solo.enterText((EditText)solo.getView(R.id.loginEmail),"555@ualberta.ca");

        solo.enterText((EditText)solo.getView(R.id.password_editText),"123456");

        solo.clickOnButton("log in");


        String uid = activity.returnCurrentUser();

        assertEquals(uid,"v1rSbJgp2uPgAxf4ZJXcclTgDyv2");


    }




    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }




}
