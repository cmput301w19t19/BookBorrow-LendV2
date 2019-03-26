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

package com.example.y.BookEX;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * The type Search person adapter.
 */
public class SearchPersonAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<NormalUser> myUser;

    /**
     * Instantiates a new Search person adapter.
     *
     * @param context the context
     * @param data    the data
     */
    public SearchPersonAdapter(Context context, ArrayList<NormalUser> data) {
        mInflater = LayoutInflater.from(context);
        myUser = data;
    }

    // get the length of the data
    @Override
    public int getCount(){
        return myUser.size();
    }

    @Override
    public Object getItem(int position){
        return myUser.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        SearchPersonAdapter.ViewHolder holder = null;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.list_layout_for_person, parent, false);
            holder = new SearchPersonAdapter.ViewHolder();

            //holder.image = (ImageButton) convertView.findViewById(R.id.userImage);
            holder.userName = (TextView) convertView.findViewById(R.id.UserName);
            holder.userEmail = (TextView) convertView.findViewById(R.id.Email);
            holder.userImage = convertView.findViewById(R.id.profileImage);
            convertView.setTag(holder);
        }
        else{
            holder = (SearchPersonAdapter.ViewHolder) convertView.getTag();
        }

        NormalUser NormalUsers = myUser.get(position);
        holder.userName.setText(NormalUsers.getName());
        //holder.image.setImageDrawable();
        holder.userEmail.setText(NormalUsers.getEmail());
        holder.userImage.setImageBitmap(NormalUsers.getPhoto());
        return convertView;

    }

    private class ViewHolder{

        ImageView userImage;

        TextView userName;
        /**
         * The User email.
         */
        TextView userEmail;
    }



}
