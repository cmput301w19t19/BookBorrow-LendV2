package com.example.y.bookborrow_lendv2;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class lenderTest {
    @Test

   public void getGet_SetRating(){
       lender lender1 = new lender();
       lender1.setLenderRating((float)3.3);
       assertEquals((float)3.3,lender1.getLenderRating(),(float)0.0001);

   }




}
