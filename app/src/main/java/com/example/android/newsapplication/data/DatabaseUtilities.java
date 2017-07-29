package com.example.android.newsapplication.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.android.newsapplication.Article;
import java.util.ArrayList;
import static com.example.android.newsapplication.data.Contract.TABLE_ARTICLES.*;

/**
 * Created by Fast_Balls on 7/28/2017.
 */

public class DatabaseUtilities {
    //Query that gets all the items in the table and sort them by the most recent updates
    public static Cursor getAll(SQLiteDatabase db) {
        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                COLUMN_NAME_PUBLISHED_AT + " DESC"
        );
        return cursor;
    }

    //easier way to insert multiple articles into the db
    public static void bulkInsert(SQLiteDatabase db, ArrayList<Article> articles){
        db.beginTransaction();
        try{
            for(Article news : articles){
                ContentValues cv = new ContentValues();
                cv.put(COLUMN_NAME_TITLE, news.getTitle());
                cv.put(COLUMN_NAME_AUTHOR, news.getAuthor());
                cv.put(COLUMN_NAME_DESCRIPTION, news.getDescription());
                cv.put(COLUMN_NAME_PUBLISHED_AT, news.getPublishedAt());
                cv.put(COLUMN_NAME_URL, news.getUrl());
                cv.put(COLUMN_NAME_URL_TO_IMAGE, news.getUrlToImage());
                db.insert(TABLE_NAME, null, cv);
            }
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
            db.close();
        }
    }

    //completely deletes the table and all of its values
    public static void deleteAll(SQLiteDatabase db){
        db.delete(TABLE_NAME, null, null);
    }
}
