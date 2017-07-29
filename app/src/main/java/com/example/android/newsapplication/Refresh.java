package com.example.android.newsapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.newsapplication.data.DBHelper;
import com.example.android.newsapplication.data.DatabaseUtilities;
import com.example.android.newsapplication.Article;
import com.example.android.newsapplication.NetworkUtilities;
import com.example.android.newsapplication.JsonUtilities;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Fast_Balls on 7/28/2017.
 */

public class Refresh {
    //method to fetch the articles from the API and store them in the database
    public static void fetchArticles(Context context){
        ArrayList<Article>  articles = null;
        URL url = NetworkUtilities.buildUrl();

        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        try{
            //clear the database of its current data
            DatabaseUtilities.deleteAll(db);

            //Grabs results from the API and parses the JSON
            String json = NetworkUtilities.getResponseFromHttpUrl(url);
            articles = JsonUtilities.parseJSON(json);

            //insert the json results of the articles into the database
            DatabaseUtilities.bulkInsert(db, articles);

        }catch(IOException e){
            e.printStackTrace();
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
}
