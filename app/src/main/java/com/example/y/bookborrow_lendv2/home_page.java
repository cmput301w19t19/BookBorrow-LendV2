package com.example.y.bookborrow_lendv2;

/**
 * This activity handles home_page
 */
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;

public class home_page extends AppCompatActivity {
    private ArrayList<lender> Owner;
    //public class home_page extends user
    //private static home_page instance;
    //public ArrayList<borrower> Borrower;
    private ArrayList<profile> profile;
    private LinkedList<Object> objectQueue = new LinkedList<>();
    private LinkedList<String> objectAction = new LinkedList<>();


    public home_page(){super ();}

   /** public static home_page getInstance(){
        if (instance==null){
            instance= new home_page();
        }
        return instance;
    }

   /* public void synchronizeWithSearch(){
        Log.d("synchRecord", "synchronizeWithSearch: begin"+ String.valueOf(objectQueue.size())+""+String.valueOf(objectAction.size()));
    }*/
}
