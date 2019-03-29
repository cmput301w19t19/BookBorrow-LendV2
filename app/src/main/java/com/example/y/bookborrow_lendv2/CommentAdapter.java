package com.example.y.bookborrow_lendv2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CommentAdapter extends BaseAdapter{
    private LayoutInflater mInflater;
    private ArrayList<comment> myComment;
    /**
     * initialize the adapter
     *
     * @param context the context
     * @param data    the data
     */
    public CommentAdapter(Context context, ArrayList<comment> data){

        mInflater = LayoutInflater.from(context);
        myComment = data;
    }

    /**
     * get the length of myBook
     * @return myBook.size()
     */
    @Override
    public int getCount(){

        return myComment.size();
    }

    /**
     * get the item at the given position of myBook
     * @param position
     * @return myBook.get(position);
     */
    @Override
    public Object getItem(int position){

        return myComment.get(position);
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

        CommentAdapter.ViewHolder holder = null;

        if(convertView == null){
            //also  use my_book_list_item layout
            convertView = mInflater.inflate(R.layout.comment_list_item, parent, false);
            holder = new CommentAdapter.ViewHolder();


            //convert to the my_book_list_item layout, ids are in there
            holder.image = (ImageView) convertView.findViewById(R.id.comment_image);
            holder.userName = (TextView) convertView.findViewById(R.id.comment_username);
            holder.rating = (TextView) convertView.findViewById(R.id.comment_rating);
            holder.comment = (TextView )convertView.findViewById(R.id.comment_detail);
            holder.status = (TextView )convertView.findViewById(R.id.comment_status);
            //holder.currentBorrower = (TextView) convertView.findViewById(R.id.CurrentBorrower);
            //holder.description = (TextView) convertView.findViewById(R.id.descrip);

            convertView.setTag(holder);
        }
        else{
            holder = (CommentAdapter.ViewHolder) convertView.getTag();
        }

        comment comment = myComment.get(position);
        holder.userName.setText(comment.getUserName());
        holder.rating.setText(comment.getRating());
        holder.comment.setText(comment.getComment());
        holder.status.setText(comment.getStatus());
        holder.image.setImageBitmap(comment.getPhoto());

        return convertView;
    }

    /**
     * the viewHolder object used for this adapter
     */
    private class ViewHolder{

        TextView userName;

        ImageView image;

        TextView rating;
        TextView comment;
        TextView status;

    }

}
