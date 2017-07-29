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
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Created by Fast_Balls on 7/28/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.NewsHolder> {
    NewsClickListener listener;
    private Context context;
    Cursor cursor;

    //MyAdapter constructor
    public MyAdapter(Cursor cursor, NewsClickListener listener){
        this.cursor = cursor;
        this.listener = listener;
    }

    public interface NewsClickListener{
        void onNewsClick(Cursor cursor, int clickedNewsIndex);
    }

    //Creates ViewHolders for the individual items
    @Override
    public NewsHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        //context of the screen
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //when false the child views are inflated in the method onCreateViewHolder()
        boolean attachToParentImmediately = false;
        //instantiates the layout xml file into the view object they belong to
        View view = inflater.inflate(R.layout.news_item, viewGroup, attachToParentImmediately);
        NewsHolder holder = new NewsHolder(view);
        return holder;
    }

    //the article information is displayed
    @Override
    public void onBindViewHolder(NewsHolder newsHolder, int position){
        newsHolder.bind(position);
    }

    @Override
    public int getItemCount(){
        return cursor.getCount();
    }

    public class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView mThumbnail;
        TextView mTitle;
        TextView mDescription;
        TextView mArticleInfo;
        //views used to display the information of the article from the news items xml
        public NewsHolder(View view){
            super(view);
            mThumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            mTitle = (TextView) view.findViewById(R.id.title);
            mArticleInfo = (TextView) view.findViewById(R.id.articleInfo);
            mDescription = (TextView) view.findViewById(R.id.description);
            view.setOnClickListener(this);
        }

        // gets the position on an article and the sets the infromation to the appropriate view
        public void bind(int position){
            cursor.moveToPosition(position);
            mTitle.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_TITLE)));
            mDescription.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_DESCRIPTION)));
            mArticleInfo.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_AUTHOR)) + " - " + cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_PUBLISHED_AT)));
            String urlToImage = cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_URL_TO_IMAGE));

            //checks to see if the image was null and if not continues to set the image using picasso
            if (urlToImage != null){
                Picasso.with(context)
                        .load(urlToImage)
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
