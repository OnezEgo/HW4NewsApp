package com.example.android.newsapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.android.newsapplication.data.Contract;
import com.example.android.newsapplication.data.DBHelper;
import com.example.android.newsapplication.data.DatabaseUtilities;


public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Void>, MyAdapter.NewsClickListener{
    static final String TAG = "mainactivity";

    private RecyclerView rv;
    private ProgressBar progress;
    private Cursor cursor;
    private SQLiteDatabase db;
    private MyAdapter adapter;

    private static final int NEWS_LOADER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progress = (ProgressBar) findViewById(R.id.progressBar);
        rv = (RecyclerView) findViewById(R.id.recyclerView);

        rv.setLayoutManager(new LinearLayoutManager(this));

      // checks to see if this app has been installed previously, and if not calls to restart loader
        // job dispacher refreshes to get new articles every minute
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirst = sp.getBoolean("isfirst", true);
        if(isFirst){
            load();
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("isfirst", false);
            editor.commit();
        }
        ScheduleUtilities.scheduleRefresh(this);
    }

    @Override
    protected void onStart(){
        super.onStart();
        db = new DBHelper(MainActivity.this).getReadableDatabase();
        cursor = DatabaseUtilities.getAll(db);
        adapter = new MyAdapter(cursor, this);
        rv.setAdapter(adapter);
    }

    @Override
    protected void onStop(){
        super.onStop();
        db.close();
        cursor.close();
    }

    @Override
    public void onLoaderReset(Loader<Void> data){}

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.action_search){
            load();
        }
        return true;
    }

    public Loader<Void> onCreateLoader(int id, final Bundle args){
        return new AsyncTaskLoader<Void>(this) {

            //implementing all the required callbacks
            @Override
            public void onStartLoading(){
                super.onStartLoading();
                progress.setVisibility(View.VISIBLE);
            }

            //implementing all the required callbacks
            @Override
            public Void loadInBackground() {
                Refresh.fetchArticles(MainActivity.this);
                return null;
            }
        };
    }

    //implementing all the required callbacks
    @Override
    public void onLoadFinished(Loader<Void> loader, Void data){
        progress.setVisibility(View.GONE);
        db = new DBHelper(MainActivity.this).getReadableDatabase();
        cursor = DatabaseUtilities.getAll(db);

        adapter = new MyAdapter(cursor, this);
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }



    @Override
    public void onNewsClick(Cursor cursor, int clickedItem){
        cursor.moveToPosition(clickedItem);
        //stores the url of the article chosen and the proceeds to open the article in the browser
        String url = cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_URL));
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    public void load(){
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.restartLoader(NEWS_LOADER, null, this).forceLoad();
    }
}