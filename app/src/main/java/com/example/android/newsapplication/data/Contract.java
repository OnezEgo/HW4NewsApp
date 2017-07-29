package com.example.android.newsapplication.data;

import android.provider.BaseColumns;

/**
 * Created by Fast_Balls on 7/28/2017.
 */

// created table to use with the database and identified the attributes that will be used
public class Contract {
    public static class TABLE_ARTICLES implements BaseColumns{
        public static final String TABLE_NAME = "articles";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_AUTHOR = "author";
        public static final String COLUMN_NAME_PUBLISHED_AT = "publishedAt";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_URL = "url";
        public static final String COLUMN_NAME_URL_TO_IMAGE = "urlToImage";
    }
}
