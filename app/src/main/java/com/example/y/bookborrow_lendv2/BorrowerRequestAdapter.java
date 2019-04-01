/*
 * Class BorrowerRequestAdapter.java
 *
 * Version 2.0
 *
 * Date 2019.4.1
 *
 * Copyright 2019 TEAM19
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
package com.example.y.bookborrow_lendv2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * adapter class used for ListView in borrower -> request book list
 *
 * @author Team19
 * @version 1.0
 */

public class BorrowerRequestAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private ArrayList<book> myBook;

    /**
     * initialize the adapter
     *
     * @param context the context
     * @param data    the data
     */
    public BorrowerRequestAdapter(Context context, ArrayList<book> data){

        mInflater = LayoutInflater.from(context);
        myBook = data;
    }

    /**
     * get the length of myBook
     * @return myBook.size()
     */
    @Override
    public int getCount(){

        return myBook.size();
    }

    /**
     * get the item at the given position of myBook
     * @param position
     * @return myBook.get(position);
     */
    @Override
    public Object getItem(int position){

        return myBook.get(position);
    }

    /**
     * get the position
     * @param position
     * @return position
     */
    @Override
    public long getItemId(int position){

        return position;
    }

    /**
     * get a view Holder
     * @param position
     * @param convertView
     * @param parent
     * @return convertView
     */
    //get a view holder
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        ViewHolder holder = null;

        if(convertView == null){
            //also  use my_book_list_item layout
            convertView = mInflater.inflate(R.layout.my_book_list_item, parent, false);
            holder = new ViewHolder();


            //convert to the my_book_list_item layout, ids are in there
            holder.image = (ImageView) convertView.findViewById(R.id.BookImage);
            holder.bookName = (TextView) convertView.findViewById(R.id.EmailHint);
            holder.rating = convertView.findViewById(R.id.rating_on);
            holder.status = convertView.findViewById(R.id.Stat);
            //holder.currentBorrower = (TextView) convertView.findViewById(R.id.CurrentBorrower);
            //holder.description = (TextView) convertView.findViewById(R.id.descrip);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        book book = myBook.get(position);
        holder.bookName.setText(book.getName());
        holder.rating.setText(book.getBookRating());
        holder.status.setText(book.getStatus());
        holder.image.setImageBitmap(book.getImage());

        return convertView;
    }

    /**
     * the viewHolder object used for this adapter
     */
    private class ViewHolder{


        TextView bookName;

        ImageView image;

        TextView rating;
        TextView status;

    }

}
