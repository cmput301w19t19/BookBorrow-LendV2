package com.example.y.bookborrow_lendv2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class Request_Book_MyAdapter extends BaseAdapter {
    public static HashMap<Integer, Boolean> isSelected;
    private LayoutInflater mInflater;
    private ArrayList<B_request> bookRequest;
    //public static Map<Integer, Boolean> isSelected;
    //private boolean defulat = false;

    //need a context to find the layout.inflater, use inflater to set the item's layout
    public Request_Book_MyAdapter(Context context, ArrayList<B_request> datas) {
        mInflater = LayoutInflater.from(context);
        bookRequest = datas;
     }


     // get the length of the data
     @Override
    public int getCount(){
        return bookRequest.size();
     }

     @Override
     public Object getItem(int position){
        return bookRequest.get(position);
     }

     @Override
    public long getItemId(int position){
        return position;
     }


     // create a viewholder
     @Override
    public View getView(int position, View convertView, ViewGroup parent){
         ViewHolder holder = null;
         final B_request b_request = bookRequest.get(position);
         if(convertView == null){
             convertView = mInflater.inflate(R.layout.book_request_list_item, parent, false);   //load the layout
             holder = new ViewHolder();

             holder.image = (ImageView) convertView.findViewById(R.id.R_L_user_image);
             holder.userName = (TextView) convertView.findViewById(R.id.R_L_username);
             holder.rating = (TextView) convertView.findViewById(R.id.R_L_borrower_rating);
             holder.checkBox = (CheckBox) convertView.findViewById(R.id.cb_checkbox);
             //holder.rating.setText(b_request.getRating().toString());

             convertView.setTag(holder);
         }
         // else: context has been used, just use it
         else{
             holder = (ViewHolder) convertView.getTag();
             holder.checkBox.setChecked(b_request.selected);
         }
         //B_request b_request = bookRequest.get(position);

         //holder.checkBox.setChecked(b_request.selected);
         //final B_request request = bookRequest.get(position);
         holder.userName.setText(b_request.getUserName());

         holder.checkBox.setOnClickListener(new View.OnClickListener() {

             @Override
             public void onClick(View v) {
                 CheckBox cb = (CheckBox)v;
                b_request.selected = cb.isChecked();
             }
         });


         return convertView;

     };

    private class ViewHolder{
        public ImageView image;
        public TextView userName;
        public TextView rating;
        public CheckBox checkBox;
    }


}
