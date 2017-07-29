package com.example.android.newsapplication;

import com.example.android.newsapplication.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Fast_Balls on 7/28/2017.
 */

public class JsonUtilities {
    public static ArrayList<Article> parseJSON(String json) throws JSONException{
        ArrayList<Article> results = new ArrayList<>();
        JSONObject main = new JSONObject(json);
        JSONArray articles = main.getJSONArray("articles");

        for(int i = 0; i < articles.length(); i++){
            JSONObject article = articles.getJSONObject(i);

            String author = article.getString("author");
            String title = article.getString("title");
            String description = article.getString("description");
            String url = article.getString("url");
            String imgUrl = article.getString("urlToImage");
            String published = article.getString("publishedAt");

            Article newsItem = new Article(author, title, description, url, imgUrl, published);
            results.add(newsItem);
        }


        return results;
    }
}
