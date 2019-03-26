/*
 * Copyright 2019 TEAM01
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.example.y.bookborrow_lendv2_refactored;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.RadioButton;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ScanTest extends ActivityTestRule<check_to_scan> {

    private Solo solo;

    public ScanTest(){
        super(check_to_scan.class);
    }

    @Rule
    public ActivityTestRule<check_to_scan> rule =
            new ActivityTestRule<>(check_to_scan.class,true,true);

    @Before
    public void setUp(){

        solo = new Solo(getInstrumentation(),rule.getActivity());

    }

    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }

    @Test
    public void clickBookDetail() {

        RadioButton rb1 = (RadioButton) solo.getView(R.id.checkBookDetail);

        //check if it beginning at the page of check_to_scan
        solo.assertCurrentActivity("wrong activity", check_to_scan.class);
        //check the radio button on radio group
        solo.clickOnView(rb1);
        //Then check the scan button
        solo.clickOnButton("scan_button");
        //Check if the page turns to Private Book Details Page
        assertTrue(solo.getCurrentActivity().getLocalClassName()=="PrivateBookDetails");
        solo.goBack();
    }

     @Test
     public void clickBorrowBook() throws Exception{
         RadioButton rb2 = (RadioButton) solo.getView(R.id.borrowABook);

         //check if it beginning at the page of check_to_scan
         solo.assertCurrentActivity("wrong activity", check_to_scan.class);
         //click the borrow Book button
         solo.clickOnView(rb2);
         solo.clickOnButton("scan_button");
         //There is a Toast to show that the book is successfully borrowed
         //Then, check if the page go back to check_to_scan pages.
         solo.assertCurrentActivity("Wrong Activity", check_to_scan.class);
         solo.goBack();
        }

    @Test
    public void clickReturnBook() throws Exception{
        RadioButton rb3 = (RadioButton) solo.getView(R.id.ReturnABook);

        solo.assertCurrentActivity("wrong activity", check_to_scan.class);
        solo.clickOnView(rb3);
        solo.clickOnButton("scan_button");
        solo.assertCurrentActivity("Wrong Activity", check_to_scan.class);
        solo.goBack();
    }


    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}
