package com.example.p4_quiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * QuizData is used to interact with DBHelper and the database
 */
public class QuizData {

    public static final String DEBUG_TAG = "QuizData";

    // this is a reference to our database; it is used later to run SQL commands
    public Context myContext;
    private SQLiteDatabase db;
    private SQLiteOpenHelper quizDbHelper;
    //defines the columns used in the table to store the information about each state
    private static final String[] allQuestionColumns = {
            QuizDBHelper.CAPITALS_COLUMN_ID,
            QuizDBHelper.CAPITALS_COLUMN_STATE,
            QuizDBHelper.CAPITALS_COLUMN_CAPITAL,
            QuizDBHelper.CAPITALS_COLUMN_CITY1,
            QuizDBHelper.CAPITALS_COLUMN_CITY2
    };
    //defines the columns used in the table to store the quizzes
    private static final String[] allQuizColumns = {
            QuizDBHelper.QUIZZES_COLUMN_ID,
            QuizDBHelper.QUIZZES_COLUMN_SCORE,
            QuizDBHelper.QUIZZES_COLUMN_DATE
    };

    public QuizData( Context context ) {
        this.quizDbHelper = QuizDBHelper.getInstance( context );
        this.myContext = context;
    }

    // Open the database
    public void open() {
        db = quizDbHelper.getWritableDatabase();
        Log.d( DEBUG_TAG, "QuizData: db open" );
    }

    // Close the database
    public void close() {
        if( quizDbHelper != null ) {
            quizDbHelper.close();
            Log.d(DEBUG_TAG, "QuizData: db closed");
        }
    }

    //retrieve all the Quiz Questions and return them as a list
    public List<QuizQuestion> retrieveAllQuizQuestions() {
        ArrayList<QuizQuestion> questions = new ArrayList<>();
        Cursor cursor = null;
        QuizQuestion question;

        try {
            cursor = db.query( QuizDBHelper.TABLE_CAPITALS, allQuestionColumns,
                    null, null, null, null, null );

            if( cursor.getCount() > 1 ) {
                while( cursor.moveToNext() ) {
                    long id = cursor.getLong( cursor.getColumnIndex( QuizDBHelper.CAPITALS_COLUMN_ID ) );
                    String state = cursor.getString( cursor.getColumnIndex( QuizDBHelper.CAPITALS_COLUMN_STATE ) );
                    String capital = cursor.getString( cursor.getColumnIndex( QuizDBHelper.CAPITALS_COLUMN_CAPITAL ) );
                    String city1 = cursor.getString( cursor.getColumnIndex( QuizDBHelper.CAPITALS_COLUMN_CITY1 ) );
                    String city2 = cursor.getString( cursor.getColumnIndex( QuizDBHelper.CAPITALS_COLUMN_CITY2 ) );

                    question = new QuizQuestion(state, capital, city1, city2);
                    question.setId( id );
                    questions.add( question );
                    Log.d( DEBUG_TAG, "Retrieved Question: " + question );
                }
            }
            Log.d( DEBUG_TAG, "Number of records from DB: " + cursor.getCount() );
        }
        catch( Exception e ){
            Log.d( DEBUG_TAG, "Exception caught: " + e );
        }
        finally{
            // close the cursor
            if (cursor != null) {
                cursor.close();
            }
        }
        // return a list of retrieved questions
        return questions;
    }

    //retrieve all the Quizzes and return them as a list
    public List<QuizObject> retrieveAllQuizzes() {
        ArrayList<QuizObject> quizzes = new ArrayList<>();
        Cursor cursor = null;
        QuizObject quiz;

        try {
            cursor = db.query( QuizDBHelper.TABLE_QUIZZES, allQuizColumns,
                    null, null, null, null, null );

            if( cursor.getCount() > 1 ) {
                while( cursor.moveToNext() ) {
                    long id = cursor.getLong( cursor.getColumnIndex( QuizDBHelper.QUIZZES_COLUMN_ID ) );
                    String date = cursor.getString( cursor.getColumnIndex( QuizDBHelper.QUIZZES_COLUMN_DATE ) );
                    int score = Integer.parseInt(cursor.getString( cursor.getColumnIndex( QuizDBHelper.QUIZZES_COLUMN_SCORE ) ));

                    quiz = new QuizObject(score,  date);
                    quiz.setId( id );
                    quizzes.add( quiz );
                    Log.d( DEBUG_TAG, "Retrieved Quiz: " + quiz );
                }
            }
            Log.d( DEBUG_TAG, "Number of Quizzes from DB: " + cursor.getCount() );
        }
        catch( Exception e ){
            Log.d( DEBUG_TAG, "Exception caught: " + e );
        }
        finally{
            // close the cursor
            if (cursor != null) {
                cursor.close();
            }
        }
        // return a list of retrieved quizzes
        return quizzes;
    }

    // Store a new quiz in the database.
    public QuizObject storeQuiz( QuizObject quiz ) {

        ContentValues values = new ContentValues();
        values.put( QuizDBHelper.QUIZZES_COLUMN_SCORE, quiz.getScore());
        values.put( QuizDBHelper.QUIZZES_COLUMN_DATE, quiz.getDate());

        // insert a new quiz into the database
        long id = db.insert( QuizDBHelper.TABLE_QUIZZES, null, values );

        // store the id (the primary key) in the quiz inistance
        quiz.setId( id );

        Log.d( DEBUG_TAG, "Stored new job lead with id: " + String.valueOf( quiz.getId() ) );

        return quiz;
    }

    //stores a new quiz question
    public QuizQuestion storeQuizQuestion( QuizQuestion question ) {

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
