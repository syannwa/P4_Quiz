package com.example.p4_quiz;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class QuizData {

    public static final String DEBUG_TAG = "QuizData";

    // this is a reference to our database; it is used later to run SQL commands
    public Context myContext;
    private SQLiteDatabase db;
    private SQLiteOpenHelper quizDbHelper;
    private static final String[] allColumns = {
            QuizDBHelper.CAPITALS_COLUMN_ID,
            QuizDBHelper.CAPITALS_COLUMN_STATE,
            QuizDBHelper.CAPITALS_COLUMN_CAPITAL,
            QuizDBHelper.CAPITALS_COLUMN_CITY1,
            QuizDBHelper.CAPITALS_COLUMN_CITY2
    };

    public QuizData( Context context ) {
        this.quizDbHelper = QuizDBHelper.getInstance( context );
        this.myContext = context;
        populate();
    }

    // Open the database
    public void open() {
        db = quizDbHelper.getWritableDatabase();
        Log.d( DEBUG_TAG, "JobLeadsData: db open" );
        //populate();
    }

    // Close the database
    public void close() {
        if( quizDbHelper != null ) {
            quizDbHelper.close();
            QuizDBHelper.methodDrop(db);
            Log.d(DEBUG_TAG, "JobLeadsData: db closed");
        }
    }

    public void populate() {
        try {
            Resources res = myContext.getResources();
            InputStream in_s = res.openRawResource( R.raw.states );

            // read the CSV data
            CSVReader reader = new CSVReader( new InputStreamReader( in_s ) );
            String [] nextLine;
            while( ( nextLine = reader.readNext() ) != null ) {
                //for( int i = 0; i < nextLine.length; i++ ) {
                    ContentValues values = new ContentValues();
                    values.put( QuizDBHelper.CAPITALS_COLUMN_STATE, nextLine[0]);
                    values.put( QuizDBHelper.CAPITALS_COLUMN_CAPITAL, nextLine[1] );
                    values.put( QuizDBHelper.CAPITALS_COLUMN_CITY1, nextLine[2] );
                    values.put( QuizDBHelper.CAPITALS_COLUMN_CITY2, nextLine[3] );

                    long id = db.insert(QuizDBHelper.TABLE_CAPITALS, null, values );

                    Log.d( DEBUG_TAG, "Line: " + nextLine );
                //}

            }
        } catch (Exception e) {
            Log.e( DEBUG_TAG, e.toString() );
        }
    }

    // Retrieve all job leads and return them as a List.
    // This is how we restore persistent objects stored as rows in the job leads table in the database.
    // For each retrieved row, we create a new JobLead (Java POJO object) instance and add it to the list.
    public List<QuizQuestion> retrieveAllQuizQuestions() {
        ArrayList<QuizQuestion> questions = new ArrayList<>();
        Cursor cursor = null;

        try {
            // Execute the select query and get the Cursor to iterate over the retrieved rows
            cursor = db.query( QuizDBHelper.TABLE_CAPITALS, allColumns,
                    null, null, null, null, null );

            // collect all job leads into a List
            if( cursor.getCount() > 1 ) {
                while( cursor.moveToNext() ) {
                    // get all attribute values of this job lead
                    long id = cursor.getLong( cursor.getColumnIndex( QuizDBHelper.CAPITALS_COLUMN_ID ) );
                    String state = cursor.getString( cursor.getColumnIndex( QuizDBHelper.CAPITALS_COLUMN_STATE ) );
                    String capital = cursor.getString( cursor.getColumnIndex( QuizDBHelper.CAPITALS_COLUMN_CAPITAL ) );
                    String city1 = cursor.getString( cursor.getColumnIndex( QuizDBHelper.CAPITALS_COLUMN_CITY1 ) );
                    String city2 = cursor.getString( cursor.getColumnIndex( QuizDBHelper.CAPITALS_COLUMN_CITY2 ) );

                    // create a new JobLead object and set its state to the retrieved values
                    QuizQuestion question = new QuizQuestion( state, capital, city1, city2 );
                    question.setId( id ); // set the id (the primary key) of this object
                    // add it to the list
                    questions.add( question );
                    Log.d( DEBUG_TAG, "Retrieved JobLead: " + question );
                }
            }
            Log.d( DEBUG_TAG, "Number of records from DB: " + cursor.getCount() );
        }
        catch( Exception e ){
            Log.d( DEBUG_TAG, "Exception caught: " + e );
        }
        finally{
            // we should close the cursor
            if (cursor != null) {
                cursor.close();
            }
        }
        // return a list of retrieved job leads
        return questions;
    }

    // Store a new job lead in the database.
    public QuizQuestion storeJobLead( QuizQuestion question ) {

        // Prepare the values for all of the necessary columns in the table
        // and set their values to the variables of the JobLead argument.
        // This is how we are providing persistence to a JobLead (Java object) instance
        // by storing it as a new row in the database table representing job leads.
        ContentValues values = new ContentValues();
        values.put( QuizDBHelper.CAPITALS_COLUMN_STATE, question.getState());
        values.put( QuizDBHelper.CAPITALS_COLUMN_CAPITAL, question.getCapital() );
        values.put( QuizDBHelper.CAPITALS_COLUMN_CITY1, question.getCity1() );
        values.put( QuizDBHelper.CAPITALS_COLUMN_CITY2, question.getCity2() );

        // Insert the new row into the database table;
        // The id (primary key) is automatically generated by the database system
        // and returned as from the insert method call.
        long id = db.insert( QuizDBHelper.TABLE_CAPITALS, null, values );

        // store the id (the primary key) in the JobLead instance, as it is now persistent
        question.setId( id );

        Log.d( DEBUG_TAG, "Stored new job lead with id: " + String.valueOf( question.getId() ) );

        return question;
    }
}
