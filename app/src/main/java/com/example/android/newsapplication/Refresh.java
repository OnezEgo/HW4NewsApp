package com.example.android.newsapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import org.json.JSONException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import com.example.android.newsapplication.data.DBHelper;
import com.example.android.newsapplication.data.DatabaseUtilities;


/**
 * Created by Fast_Balls on 7/28/2017.
 */

public class Refresh {
    //retrieves the articles from the api with the given url built in network utilities
    public static void fetchArticles(Context context){
        ArrayList<Article>  articles = null;
        URL url = NetworkUtilities.buildUrl();
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        try{
            DatabaseUtilities.deleteAll(db);
            //parses the json into usable information
            String json = NetworkUtilities.getResponseFromHttpUrl(url);
            articles = JsonUtilities.parseJSON(json);
            //takes the parsed json from the api and inserts it into the database
            DatabaseUtilities.bulkInsert(db, articles);
        }catch(IOException e){
            e.printStackTrace();
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
}
