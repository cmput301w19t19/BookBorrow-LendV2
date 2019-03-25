/*
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
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;

/**
 * @author BoweiLi
 * @version 1.0
 * @see BaseAdapter
 * @see BorrowBookList
 */
public class BorrowingBookAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<book> myBook;
    public BorrowingBookAdapter(Context context, ArrayList<book> data) {
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
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder = null;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.borrow_book_item, parent, false);
            holder = new BorrowingBookAdapter.ViewHolder();

            holder.image = (ImageView) convertView.findViewById(R.id.BorBookImage);
            holder.bookName = (TextView) convertView.findViewById(R.id.BorBookName);
            holder.OwnerName = (TextView) convertView.findViewById(R.id.OwnerName);
            holder.description = (TextView) convertView.findViewById(R.id.BorBookDescrip);
            holder.rating = (TextView) convertView.findViewById(R.id.BorRating_on);

            convertView.setTag(holder);
        }
        else{
            holder = (BorrowingBookAdapter.ViewHolder) convertView.getTag();
        }

        book book = myBook.get(position);
        holder.bookName.setText(book.getName());
        holder.OwnerName.setText(book.getOwnerID());
        //holder.image.setImageDrawable();
        holder.rating.setText(book.getBookRating());
        holder.description.setText(book.getDescription());
        //holder.info.set
        return convertView;

    }
    /**
     * the viewHolder object used for this adapter
     */
    private class ViewHolder{
        ImageView image;
        TextView bookName;
        TextView OwnerName;
        TextView description;
        TextView rating;
    }


}