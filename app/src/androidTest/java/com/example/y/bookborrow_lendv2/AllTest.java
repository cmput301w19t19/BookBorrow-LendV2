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
        solo.enterText((EditText) solo.getView(R.id.loginEmail), "jlin7@ualberta.ca");
        solo.enterText((EditText) solo.getView(R.id.password_editText), "123456");
        solo.clickOnButton("log in");
        solo.assertCurrentActivity("wrong activity", home_page.class);

/*
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
        solo.clickOnButton("Request It!");
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
        solo.assertCurrentActivity("wrong activity",home_page.class);


        // HomePageTest
        // click the image button to the other layout
        // OwnerHomePage
        solo.clickOnImageButton(1);
        solo.assertCurrentActivity("Owner activity",OwnerHomeActivity.class);


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
        solo.assertCurrentActivity("MyBookList page",MyBookList.class);

/*
        // MyBookListTest
        // Test the MyBookList
        solo.assertCurrentActivity("wrong activity",MyBookList.class);

        // Filter button (available)
        solo.clickOnButton("Filter");
        solo.clickOnText("available");
        solo.clickOnText("Next");
        solo.clickInList(0);
        solo.assertCurrentActivity("wrong activity",PrivateBookDetails.class);
        solo.searchText("available");
        solo.clickOnButton("Edit");
        solo.assertCurrentActivity("wrong activity",EditBookDetail.class);

        // Edit Book Detail
        // delete photo
        solo.clickOnImage(0);
        solo.clickOnText("Delete");

        // Edit Book Detail
        // click back button
        solo.clearEditText((EditText) solo.getView(R.id.pt2));
        solo.enterText((EditText) solo.getView(R.id.pt2), "King");
        solo.goBack();
        solo.clickOnButton("Edit");
        solo.assertCurrentActivity("wrong activity",EditBookDetail.class);

        // Edit Book Detail
        // click save button
        solo.clearEditText((EditText) solo.getView(R.id.pt2));
        solo.enterText((EditText) solo.getView(R.id.pt2), "King");
        solo.clickOnButton("Save");
        solo.assertCurrentActivity("wrong activity",MyBookList.class);
        solo.searchText("King");

        // Edit Book Detail
        // Delete a book
        solo.clickInList(0);
        solo.clickOnButton("Delete");
        solo.assertCurrentActivity("wrong activity",MyBookList.class);

        // Filter button (requested)
        solo.clickOnButton("Filter");
        solo.clickOnText("requested");
        solo.clickOnText("Next");
        solo.clickInList(0);
        solo.assertCurrentActivity("wrong activity",PrivateBookDetails.class);
        solo.searchText("requested");
        solo.clickOnButton("Request");
        // go to the request list
        // RequestToOwnerTest
        solo.assertCurrentActivity("wrong activity",RequestToOwner.class);
        solo.clickOnCheckBox(0);
        solo.clickOnButton("decline");
        solo.clickOnCheckBox(0);
        solo.clickOnButton("accept");
        solo.assertCurrentActivity("wrong activity",MyBookList.class);

        // Filter button (accepted)
        solo.clickOnButton("Filter");
        solo.clickOnText("accepted");
        solo.clickOnText("Next");
        solo.clickInList(0);
        solo.assertCurrentActivity("wrong activity",PrivateBookDetails.class);
        solo.searchText("accepted");
        solo.clickOnButton("Location");
        solo.assertCurrentActivity("wrong activity",MapsActivityOwnerSetLocation.class);
        solo.goBack();
        solo.goBack();
        solo.assertCurrentActivity("wrong activity",MyBookList.class);

        // Filter button (borrowed)
        solo.clickOnButton("Filter");
        solo.clickOnText("borrowed");
        solo.clickOnText("Next");
        solo.clickInList(0);
        solo.assertCurrentActivity("wrong activity",PrivateBookDetails.class);
        solo.searchText("borrowed");
        solo.goBack();
        solo.assertCurrentActivity("wrong activity",MyBookList.class);

        // Filter button (show all)
        solo.clickOnButton("Filter");
        solo.clickOnText("show all");
        solo.clickOnText("Next");
        solo.clickInList(0);
        solo.assertCurrentActivity("wrong activity",PrivateBookDetails.class);
        solo.searchText("available");
        solo.searchText("requested");
        solo.searchText("accepted");
        solo.searchText("borrowed");
        solo.goBack();
        solo.assertCurrentActivity("wrong activity",MyBookList.class);



        // OwnerHomeActivityTest
        // Request Notified
        solo.assertCurrentActivity("wrong activity",OwnerHomeActivity.class);
        solo.clickOnImageButton(0);
        solo.assertCurrentActivity("br page",ViewRequests.class);
        solo.goBack();


        // OwnerHomeActivityTest
        // Scan
        solo.assertCurrentActivity("wrong activity",OwnerHomeActivity.class);
        solo.clickOnText("Scan");
        solo.assertCurrentActivity("br page",check_to_scan.class);
        solo.goBack();


        // OwnerHomeActivityTest
        // Search
        solo.assertCurrentActivity("wrong activity",OwnerHomeActivity.class);
        solo.clickOnText("Search");
        solo.assertCurrentActivity("br page",Search.class);
        solo.goBack();


        // HomePageTest
        // click the image button to the other layout
        // BorrowerHomePage
        solo.clickOnImageButton(2);
        solo.assertCurrentActivity("Borrower activity",BorrowerMenu.class);


        // BorrowerMenuTest
        // BorrowerMenu
        solo.searchText("Borrower");
        solo.searchText("My Book List");
        solo.searchText("Book Requested");
        solo.searchText("Search");
        solo.searchText("Click to search");
        solo.searchText("Scan");
        solo.searchText("Click to scan a book");


        // BorrowerMenuTest
        // BorrowerBookListTest
        solo.clickOnText("My Book List");
        solo.assertCurrentActivity("BorrowBookList page",BorrowBookList.class);
        solo.searchText("My Borrowed books");
        solo.clickLongInList(0);
        solo.assertCurrentActivity("publicBookDetail page",PublicBookDetails.class);
        solo.goBack();
        solo.goBack();
        solo.assertCurrentActivity("Borrower activity",BorrowerMenu.class);


        // BorrowerMenuTest
        // BookRequest
        solo.assertCurrentActivity("wrong activity",BorrowerMenu.class);
        solo.clickOnText("Book Requested");
        solo.assertCurrentActivity("BorrowerRequestTest activity",BorrowerRequest.class);
        solo.clickOnButton("show accepted");
        solo.searchText("accepted");  //there is a book title with element appear when accepted button click
        solo.clickInList(0);
        solo.assertCurrentActivity("should be book detail",PublicBookDetails.class);
        solo.goBack();
        solo.clickOnButton("show requested");
        solo.searchText("requested");
        solo.clickInList(0);
        solo.assertCurrentActivity("should be book detail",PublicBookDetails.class);
        solo.goBack();
        solo.goBack();

        // BorrowerMenuTest
        // Scan
        solo.assertCurrentActivity("wrong activity",BorrowerMenu.class);
        solo.clickOnText("Scan");
        solo.assertCurrentActivity("br page",check_to_scan.class);
        solo.goBack();


        // BorrowerMenuTest
        // Request Notified
        solo.assertCurrentActivity("wrong activity",BorrowerMenu.class);
        solo.clickOnImageButton(0);
        solo.assertCurrentActivity("br page",ViewAcceptedRequests.class);
        solo.goBack();


        // BorrowerMenuTest
        // Search
        solo.assertCurrentActivity("wrong activity",BorrowerMenu.class);
        solo.clickOnText("Search");
        solo.assertCurrentActivity("br page",Search.class);
        solo.goBack();

*/

        // HomePageTest
        // click the image button to the other layout
        // Profile
        solo.clickOnImageButton(3);
        solo.assertCurrentActivity("Borrower activity",SearchingUserDetail.class);


        // ProfileTest
        // edit profile
        solo.assertCurrentActivity("profile page",SearchingUserDetail.class);
        solo.clickOnImageButton(0);
        solo.assertCurrentActivity("profile page",profile.class);
        solo.clearEditText(R.id.InputPhone);
        solo.enterText((EditText) solo.getView(R.id.InputPhone), "1234567890");
        solo.clickOnButton("Done");
        solo.searchText("1234567890");

        // ProfileTest
        // bigger image
        solo.clickOnImage(0);
        solo.assertCurrentActivity("owner home page",SeeImageActivity.class);
        solo.goBack();

        // HomePageActivityTest
        // sign out
        solo.assertCurrentActivity("wrong activity",SearchingUserDetail.class);
        solo.clickOnText("Sign Out");
        solo.assertCurrentActivity("login page",loginAct.class);

    }

    @After
    public void tearDown() throws Exception{

        solo.finishOpenedActivities();
    }
}
