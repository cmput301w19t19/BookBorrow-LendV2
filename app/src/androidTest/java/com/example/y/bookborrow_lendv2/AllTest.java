package com.example.y.bookborrow_lendv2;


import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

@RunWith(AndroidJUnit4.class)
public class AllTest extends ActivityTestRule<loginAct> {

    private Solo solo;

    public AllTest(){
        super(loginAct.class);
    }

    @Rule
    public ActivityTestRule<loginAct> rule =
            new ActivityTestRule<>(loginAct.class,true,true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(),rule.getActivity());
    }

    @Test
    public void start() throws Exception{

        Activity activity = rule.getActivity();
    }

    @Test
    public void loginTest() {

        // login page
        // sign up
        /*solo.assertCurrentActivity("wrong activity", loginAct.class);
        solo.clickOnButton("Sign up");
        solo.assertCurrentActivity("wrong activity", Register.class);


        // Register
        // register
        solo.assertCurrentActivity("wrong activity", Register.class);
        solo.enterText((EditText) solo.getView(R.id.InputName_), "test UserName");
        solo.enterText((EditText) solo.getView(R.id.InputRegisterEmail), "test@ualberta.ca");
        solo.enterText((EditText) solo.getView(R.id.InputRegisterPassword), "123456");
        solo.enterText((EditText) solo.getView(R.id.InputPhone_), "1234567890");
        solo.clickOnButton("Done");
        solo.assertCurrentActivity("wrong activity", home_page.class);


        // HomePageActivityTest
        // sign out
        solo.assertCurrentActivity("wrong activity",home_page.class);
        solo.clickOnText("Sign Out");
        solo.assertCurrentActivity("login page",loginAct.class);*/


        // login page
        // login
        solo.assertCurrentActivity("wrong activity", loginAct.class);
        solo.enterText((EditText) solo.getView(R.id.loginEmail), "yf1@ualberta.ca");
        solo.enterText((EditText) solo.getView(R.id.password_editText), "123456");
        solo.clickOnButton("log in");
        solo.assertCurrentActivity("wrong activity", home_page.class);


        // HomePageActivityTest
        // Search and Recommend
        solo.assertCurrentActivity("wrong activity",home_page.class);
        solo.searchButton("Search");
        solo.enterText((EditText) solo.getView(R.id.SearchInput), "Element");
        solo.clickOnText("Search");
        solo.assertCurrentActivity("Search page",SearchResultForBook.class);


        // SearchResultForBook
        // Search Result and click the first listview
        solo.assertCurrentActivity("wrong activity",SearchResultForBook.class);
        solo.searchText("Book Search Result");
        solo.searchText("People Search Result");
        solo.clickInList(0,0);
        solo.assertCurrentActivity("Public book detail activity",PublicBookDetails.class);


        // Public Book detail
        // try to find the new added book deatil and back to click new search
        //////////////////////////////       not finish                           //////////////////////
        solo.assertCurrentActivity("wrong activity",PublicBookDetails.class);
        solo.searchText("ISBN:");
        solo.searchText("Author");
        solo.searchText("State");
        solo.searchText("Rate");
        solo.searchText("Owner");
        solo.searchText("Description from Owner:");
        solo.searchText("Comment from Borrowers:");
        solo.clickOnImage(0);
        solo.assertCurrentActivity("owner home page",SeeImageActivity.class);
        solo.goBack();
        solo.clickOnText("see more");
        solo.assertCurrentActivity("CommentDetail",CommentDetail.class);


        // Comment Detail
        // see the comment detail, click the element to detail and back
        solo.assertCurrentActivity("wrong activity",CommentDetail.class);
        solo.searchText("Comments");
        solo.clickInList(0,0);
        solo.assertCurrentActivity("wrong activity",SearchingUserDetail.class);
        solo.goBack();
        solo.assertCurrentActivity("wrong activity",CommentDetail.class);
        solo.goBack();



        //add the book detail
        //solo.clickOnButton("Request It!");
        solo.goBack();
        solo.assertCurrentActivity("wrong activity",SearchResultForBook.class);
        // SearchResultForBook
        solo.clickOnButton("New Search");


        // SearchTest
        // Search
        solo.assertCurrentActivity("wrong activity",Search.class);
        solo.enterText((EditText) solo.getView(R.id.keyword), "test");
        solo.clickOnButton("Search");
        solo.assertCurrentActivity("wrong activity",SearchResultForBook.class);


        // SearchResultForBook
        // Search Result and click back to home
        solo.assertCurrentActivity("wrong activity",SearchResultForBook.class);
        solo.searchText("Book Search Result");
        solo.searchText("People Search Result");
        solo.clickInList(0,1);


        // SearchingUserDetail
        // check the user detail
        solo.assertCurrentActivity("wrong activity",SearchingUserDetail.class);
        solo.searchText("Email:");
        solo.searchText("Phone:");
        solo.searchText("Lend Books:");
        solo.searchText("Borrow Books:");
        solo.searchText("As a Owner:");
        solo.searchText("As a Borrower:");
        solo.clickOnImage(0);
        solo.assertCurrentActivity("owner home page",SeeImageActivity.class);
        solo.goBack();
        solo.assertCurrentActivity("wrong activity",SearchingUserDetail.class);
        solo.goBack();
        solo.assertCurrentActivity("wrong activity",Search.class);
        solo.goBack();
        solo.assertCurrentActivity("wrong activity",home_page.class);
        solo.clickInList(0);
        solo.assertCurrentActivity("wrong activity",PublicBookDetails.class);
        solo.goBack();

        /*solo.clickOnImageButton(0);


        // OwnerHomeActivityTest
        // My Book List
        solo.assertCurrentActivity("wrong activity",OwnerHomeActivity.class);
        solo.searchText("Owner");
        solo.searchText("My Book List");
        solo.searchText("Click here to check your books");
        solo.searchText("Search");
        solo.searchText("Click to search");
        solo.searchText("Scan");
        solo.searchText("Click to scan a book");
        solo.clickOnText("My Book List");
        solo.assertCurrentActivity("br page",MyBookList.class);
        solo.goBack();


        // MyBookListTest


        // OwnerHomeActivityTest
        // Search
        solo.assertCurrentActivity("wrong activity",OwnerHomeActivity.class);
        solo.searchText("Please Enter A Keyword");
        solo.clickOnText("Search");
        solo.assertCurrentActivity("br page",Search.class);
        //solo.goBack();



        // OwnerHomeActivityTest
        // Scan
        solo.assertCurrentActivity("wrong activity",OwnerHomeActivity.class);
        solo.clickOnText("Scan");
        solo.assertCurrentActivity("br page",check_to_scan.class);
        solo.goBack();
        solo.goBack();


        // HomePageActivityTest
        // borrower menu
        solo.assertCurrentActivity("wrong activity",home_page.class);
        solo.clickOnText("Borrower");
        solo.assertCurrentActivity("Borrower home page",BorrowerMenu.class);
        solo.goBack();

        // HomePageActivityTest
        //self profile
        solo.assertCurrentActivity("wrong activity",home_page.class);
        solo.clickOnText("Profile");
        solo.assertCurrentActivity("profile page",profile.class);
        solo.goBack();

        // HomePageActivityTest
        // bigger image
        solo.assertCurrentActivity("wrong activity",home_page.class);
        //ImageButton photo = (ImageButton) solo.getView("UserHead") ;
        //solo.clickOnView(photo);
        solo.clickOnImage(0);
        solo.assertCurrentActivity("owner home page",SeeImageActivity.class);
        solo.goBack();

        // HomePageActivityTest
        // sign out
        solo.assertCurrentActivity("wrong activity",home_page.class);
        solo.clickOnText("Sign Out");
        solo.assertCurrentActivity("login page",loginAct.class);
        solo.goBack();*/

    }

    @After
    public void tearDown() throws Exception{

        solo.finishOpenedActivities();
    }
}
