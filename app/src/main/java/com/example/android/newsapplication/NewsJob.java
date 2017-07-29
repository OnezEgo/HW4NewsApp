package com.example.android.newsapplication;

import android.os.AsyncTask;
import android.widget.Toast;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.JobParameters;

/**
 * Created by Fast_Balls on 7/28/2017.
 */

public class NewsJob extends JobService {
    private AsyncTask mBackgroundTask;

    //main method that the service calls
    @Override
    public boolean onStartJob(final JobParameters job){
        mBackgroundTask = new AsyncTask() {

            @Override
            protected void onPreExecute(){
                Toast.makeText(NewsJob.this, "News has been refreshed.", Toast.LENGTH_SHORT).show();
                // creates a toast the lets the user know the backgrond task has been completed
                super.onPreExecute();
            }
            @Override
            protected Object doInBackground(Object[] params) {
                Refresh.fetchArticles(NewsJob.this);
                return null;
            }

            @Override
            protected void onPostExecute(Object o){
                jobFinished(job, false);
                super.onPostExecute(o);
            }
        };
        mBackgroundTask.execute();
        return true;
    }
    // stops the job passed in the args
    @Override
    public boolean onStopJob(JobParameters job){
        if (mBackgroundTask != null){
            mBackgroundTask.cancel(false);
        }
        return true;
    }
}
