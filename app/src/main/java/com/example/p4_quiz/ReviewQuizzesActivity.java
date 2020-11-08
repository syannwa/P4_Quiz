package com.example.p4_quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.AsyncTask;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import java.util.List;

/**
 * The ReviewQuizzesActivity class uses the QuizRecyclerAdapter to show the previous
 * quizzes stored in the app
 */
public class ReviewQuizzesActivity extends AppCompatActivity {

    public static final String DEBUG_TAG = "ReviewQuizzesActivity";

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter recyclerAdapter;

    private QuizData quizData = null;
    private List<QuizObject> quizList;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {

        Log.d( DEBUG_TAG, "ReviewQuizActivity.onCreate()" );

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_review_quizzes );

        recyclerView = (RecyclerView) findViewById( R.id.recyclerView );

        // use a linear layout manager for the recycler view
        layoutManager = new LinearLayoutManager(this );
        recyclerView.setLayoutManager( layoutManager );

        quizData = new QuizData( this );

        new QuizzesDBReaderTask().execute();

    }

    // This is an AsyncTask class (it extends AsyncTask) to perform DB reading of job leads, asynchronously.
    private class QuizzesDBReaderTask extends AsyncTask<Void, Void, List<QuizObject>> {

        // This method will run as a background process to read from db.
        // It returns a list of retrieved JobLead objects.
        // It will be automatically invoked by Android, when we call the execute method
        // in the onCreate callback (the job leads review activity is started).
        @Override
        protected List<QuizObject> doInBackground( Void... params ) {
            quizData.open();
            quizList = quizData.retrieveAllQuizzes();

            return quizList;
        }

        // This method will be automatically called by Android once the db reading
        // background process is finished.  It will then create and set an adapter to provide
        // values for the RecyclerView.
        // onPostExecute is like the notify method in an asynchronous method call discussed in class.
        @Override
        protected void onPostExecute( List<QuizObject> jobLeadsList ) {
            super.onPostExecute(quizList);
            recyclerAdapter = new QuizRecyclerAdapter( quizList );
            recyclerView.setAdapter( recyclerAdapter );
        }
    }

    @Override
    protected void onResume() {
        // open the database in onResume
        if( quizData != null )
            quizData.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        // close the database in onPause
        if( quizData != null )
            quizData.close();
        super.onPause();
    }

    // These activity callback methods are not needed and are for edational purposes only
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}