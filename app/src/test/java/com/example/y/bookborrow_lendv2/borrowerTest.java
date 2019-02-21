package com.example.y.bookborrow_lendv2;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class borrowerTest {





    @Test
    public void getGet_SetRating(){
        borrower borrower1 = new borrower();
            borrower1.setBorrowerRating((float)3.3);
            assertEquals((float)3.3,borrower1.getBorrowerRating(),(float)0.0001);

        }








}
