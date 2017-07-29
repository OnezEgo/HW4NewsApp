package com.example.android.newsapplication;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.newsapplication.data.Contract;
import com.example.android.newsapplication.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Fast_Balls on 7/28/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.NewsHolder> {
    private ArrayList<Article> articlesList;
    NewsClickListener listener;
    private Context context;
    Cursor cursor;

    final static String TAG = "newsadapter";

    //Constructor of NewsAdapter
    public MyAdapter(Cursor cursor, NewsClickListener listener){
        this.cursor = cursor;
        this.listener = listener;
    }

    public interface NewsClickListener{
        void onNewsClick(Cursor cursor, int clickedNewsIndex);
    }

    //Creates each of the ViewHolders to display on the screen
    @Override
    public NewsHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        //gets the context of the current activity displayed on the screen
        context = viewGroup.getContext();

        //obtains the views from the xml file
        LayoutInflater inflater = LayoutInflater.from(context);

        //used to make any layout changes
        //if true, it will return to root object which won't show any changes made
        //for false: child views are inflated in onCreateViewHolder()
        boolean attachToParentImmediately = false;

        //used to instantiate the layout xml file into actual View objects
        View view = inflater.inflate(R.layout.news_item, viewGroup, attachToParentImmediately);

        NewsHolder holder = new NewsHolder(view);

        return holder;
    }

    //Displays the data information of an article at a specified position given as a parameter
    @Override
    public void onBindViewHolder(NewsHolder newsHolder, int position){
        newsHolder.bind(position);
    }

    @Override
    public int getItemCount(){
        //Returns the number of rows in the cursor/table
        return cursor.getCount();
    }

    public class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView mThumbnail;
        TextView mTitle;
        TextView mDescription;
        TextView mArticleInfo;



        public NewsHolder(View view){
            super(view);

            //Getting references of the id's from the news_article.xml file
            mThumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            mTitle = (TextView) view.findViewById(R.id.title);

            mArticleInfo = (TextView) view.findViewById(R.id.articleInfo);
            mDescription = (TextView) view.findViewById(R.id.description);

            view.setOnClickListener(this);
        }

        //Responsible for getting the position of a specific article and setting the views
        //corresponding to that article
        public void bind(int position){
            //gets the position of the specific article
            cursor.moveToPosition(position);

            //sets the text of each view according to the specific position gotten above
            mTitle.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_TITLE)));
            mDescription.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_DESCRIPTION)));
            mArticleInfo.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_AUTHOR)) + " - " + cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_PUBLISHED_AT)));

            //grabs the url of the image of the article
            String imageUrl = cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_URL_TO_IMAGE));


            //applies the image into the ImageView
            if (imageUrl != null){
                Picasso.with(context)
                        .load(imageUrl)
                        .into(mThumbnail);
            }


        }

        @Override
        public void onClick(View view){
            int pos = getAdapterPosition();
            listener.onNewsClick(cursor, pos);
        }
    }
}
