package com.example.y.bookborrow_lendv2_refactored;

import java.util.UUID;

class super_book {
    protected UUID ID;

    /**
     * Sets id.
     *
     * @param s this method set a unique id to book
     */

    public void setID(String s) {
        this.ID = UUID.fromString(s);
    }

    /**
     * this method return book ID
     *
     * @return book 's ID
     */
    public String getID(){return this.ID.toString();}
}
