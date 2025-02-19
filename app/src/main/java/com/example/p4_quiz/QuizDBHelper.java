package com.example.p4_quiz;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;

import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The QuizDBHelper class is used to initialize the tables and import the data from the csv
 */
public class QuizDBHelper extends SQLiteOpenHelper {
    private static final String DEBUG_TAG = "QuizDBHelper";

    private static final String DB_NAME = "state.db";
    private static final int DB_VERSION = 1;

    public static final String TABLE_CAPITALS = "capitals";
    public static final String CAPITALS_COLUMN_ID = "_id";
    public static final String CAPITALS_COLUMN_STATE = "state";
    public static final String CAPITALS_COLUMN_CAPITAL = "capital";
    public static final String CAPITALS_COLUMN_CITY1 = "city1";
    public static final String CAPITALS_COLUMN_CITY2 = "city2";

    public static final String TABLE_QUIZZES = "quizzes";
    public static final String QUIZZES_COLUMN_ID = "_id";
    public static final String QUIZZES_COLUMN_SCORE = "score";
    public static final String QUIZZES_COLUMN_DATE = "date";

    private static QuizDBHelper helperInstance;
    private static Context myContext;

    // SQL command to create the capitals table
    private static final String CREATE_CAPITALS =
            "create table " + TABLE_CAPITALS + " ("
                    + CAPITALS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + CAPITALS_COLUMN_STATE + " TEXT, "
                    + CAPITALS_COLUMN_CAPITAL + " TEXT, "
                    + CAPITALS_COLUMN_CITY1 + " TEXT, "
                    + CAPITALS_COLUMN_CITY2 + " TEXT"
                    + ")";

    // SQL command to create the  quizzes table
    private static final String CREATE_QUIIZZES =
            "create table " + TABLE_QUIZZES + " ("
                    + QUIZZES_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + QUIZZES_COLUMN_SCORE + " INTEGER, "
                    + QUIZZES_COLUMN_DATE + " TEXT "
                    + ")";

    private QuizDBHelper(Context context) {
        super( context, DB_NAME, null, DB_VERSION );
        this.myContext = context;
    }

    public static synchronized QuizDBHelper getInstance( Context context ) {
        if( helperInstance == null ) {
            helperInstance = new QuizDBHelper( context.getApplicationContext() );
        }
        return helperInstance;
    }

    // We must override onCreate method, which will be used to create the database if
    // it does not exist yet.
    @Override
    public void onCreate( SQLiteDatabase db ) {
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_CAPITALS);
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_QUIZZES);
        db.execSQL( CREATE_CAPITALS );
        db.execSQL( CREATE_QUIIZZES );
        Log.d( DEBUG_TAG, "Table " + TABLE_CAPITALS + " created" );
        new QuizDBPopulateTask().execute( db );
    }

    /**
     * QuizDBPopulateTask is an AsyncTask that populates the initial database for the capitals table
     */
    private class QuizDBPopulateTask extends AsyncTask<SQLiteDatabase, Void, SQLiteDatabase> {

        @Override
        protected SQLiteDatabase doInBackground(SQLiteDatabase... sqLiteDatabases) {
            try {
                Resources res = myContext.getResources();
                InputStream in_s = res.openRawResource( R.raw.states );

                // read the CSV data
                CSVReader reader = new CSVReader( new InputStreamReader( in_s ) );
                String [] nextLine;
                while( ( nextLine = reader.readNext() ) != null ) {
                    ContentValues values = new ContentValues();
                    values.put( QuizDBHelper.CAPITALS_COLUMN_STATE, nextLine[0]);
                    values.put( QuizDBHelper.CAPITALS_COLUMN_CAPITAL, nextLine[1] );
                    values.put( QuizDBHelper.CAPITALS_COLUMN_CITY1, nextLine[2] );
                    values.put( QuizDBHelper.CAPITALS_COLUMN_CITY2, nextLine[3] );

                    long id = sqLiteDatabases[0].insert(QuizDBHelper.TABLE_CAPITALS, null, values );

                    Log.d( DEBUG_TAG, "Line: " + nextLine );
                }
            } catch (Exception e) {
                Log.e( DEBUG_TAG, e.toString() );
            }
            return null;
        }
    }

    // We should override onUpgrade method, which will be used to upgrade the database if
    // its version (DB_VERSION) has changed
    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_CAPITALS);
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_QUIZZES);
        onCreate( db );
        Log.d( DEBUG_TAG, "Table " + TABLE_CAPITALS + " upgraded" );
    }


}
