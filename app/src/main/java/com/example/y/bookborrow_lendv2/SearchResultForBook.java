package com.example.y.bookborrow_lendv2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import android.media.Image;

public class SearchResultForBook extends AppCompatActivity {

    private EditText mSearchWord;
    private Button mSearchButton;

    private RecyclerView mResultList;
    private DatabaseReference mBookDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_for_book);

        mBookDatabase = FirebaseDatabase.getInstance().getReference("Book");
        mSearchWord = (EditText) findViewById(R.id.editText);
        mSearchButton = (Button) findViewById(R.id.See_Result_of_BookButton);

        mResultList = (RecyclerView) findViewById(R.id.result_list);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));


        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchText = mSearchWord.getText().toString();

                firebaseBookSearch(searchText);
            }
        });
    }







    private void firebaseBookSearch (String searchText) {
        Toast.makeText(SearchResultForBook.this, "Start Search", Toast.LENGTH_LONG).show();

        Query firebaseSearchQuery = mBookDatabase.orderByChild("name").startAt(searchText).endAt(searchText + "\uf8ff");

        FirebaseRecyclerOptions<Book> options = new FirebaseRecyclerOptions.Builder<Book>().setQuery(firebaseSearchQuery, Book.class).setLifecycleOwner(this).build();
        FirebaseRecyclerAdapter<Book, BookViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Book, BookViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull BookViewHolder holder, int position, @NonNull Book model) {
                holder.book_name.setText(model.getName());
                holder.book_status.setText(model.getStatus());
                //holder.image.setImageDrawable();
                holder.book_description.setText(model.getDescription());

                //Picasso.get().load(model.getPhoto()).into(holder.Photo)
            }

            @NonNull
            @Override
            public BookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                //return null;
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_search_result_for_book, viewGroup, false);
                BookViewHolder viewHolder = new BookViewHolder(view);
                return viewHolder;

            }
        };

        mResultList.setAdapter(firebaseRecyclerAdapter);



    }

    @Override
    protected void onStart(){
        super.onStart();
       // SearchBookAdapter.startListening();


    }

    /*@Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<Book> options= new FirebaseRecyclerOptions.Builder<Book>().setQuery(firebaseSearchQuery, Book.class).setLifecycleOwner(this).build();
        FirebaseRecyclerAdapter<Book, BookViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Book, BookViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull BookViewHolder holder, int position, @NonNull Book model) {
                holder.book_name.setText(model.getName());
                holder.book_status.setText(model.getStatus());
                //holder.image.setImageDrawable();
                holder.book_description.setText(model.getDescription());


                //Picasso.get().load(model.getPhoto()).into(holder.Photo)
            }

            @NonNull
            @Override
            public BookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                //return null;
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_search_result_for_book, viewGroup, false);
                BookViewHolder viewHolder = new BookViewHolder(view);
                return viewHolder;

            }
        };

        mResultList.setAdapter(firebaseRecyclerAdapter);

        firebaseRecyclerAdapter.startListening();


    }*/


    // view Book Class

    public static class BookViewHolder extends RecyclerView.ViewHolder{
        View mView;
        TextView book_name, book_status, book_description;
        public BookViewHolder(View itemView){
            super(itemView);
            mView= itemView;

            book_name= (TextView) itemView.findViewById(R.id.BookName) ;
            book_status = (TextView) itemView.findViewById(R.id.Stat);
            book_description = (TextView) itemView.findViewById(R.id.descrip);
        }

        public void setDetails(Context ctx, String bookName, String bookStatus, String bookDescription, Image bookImage){

            TextView book_name= (TextView) mView.findViewById(R.id.BookName) ;
            TextView book_status = (TextView) mView.findViewById(R.id.Stat);
            TextView book_description = (TextView) mView.findViewById(R.id.descrip);
            //ImageView book_image = (ImageView) mView.findViewById(R.id.BookImage);


            book_name.setText(bookName);
            book_status.setText(bookStatus);
            book_description.setText(bookDescription);

            //Glide.with(ctx).load(bookImage).into(book_image);

        }
    }



    public void newSearch(View view){
        //BookDatabase= FirebaseDatabase.getInstance().getReference("books");



    }

    public void backHome(View view){


    }

    public void displayBookName(View view){


    }


    public void displayBookDetail(View view){


    }

    public void displayBookImage(View view){

    }
}
