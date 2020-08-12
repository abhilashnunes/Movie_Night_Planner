package com.example.movienightplanner.View;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.movienightplanner.Model.Events;
import com.example.movienightplanner.Model.Movie;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "movieplanner.db";
    public static final int DATABASE_VERSION = 1;

    public static final String EVENT = "event_table";
    public static final String MOVIE = "movie_table";

    //column name of contact table
    public static final String _ID = "_id";
    public static final String TITLE = "title";
    public static final String START_DATE = "start_date";
    public static final String END_DATE = "end_date";
    public static final String MOVIE_NAME = "movie_name";
    public static final String VENUE = "venue";
    public static final String LAT = "lat";
    public static final String _LONG = "_long";
    public static final String _SORTDATE = "_sortdate";
    public static final String ATTENDEE = "_attendee";


    //column name of contact table
    public static final String MOVIE_ID = "_id";
    public static final String MOVIE_PATH = "movie_image";
    public static final String MOVIE_YEAR = "movie_year";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createTables(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + EVENT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MOVIE);
        onCreate(sqLiteDatabase);
    }

    public void createTables(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_MAINTABLE = "CREATE TABLE " + EVENT + "("
                + _ID + " INTEGER PRIMARY KEY,"
                + TITLE + " TEXT,"
                + START_DATE + " TEXT,"
                + END_DATE + " TEXT,"
                + MOVIE_NAME + " TEXT,"
                + VENUE + " TEXT,"
                + LAT + " TEXT,"
                + _LONG + " TEXT,"
                + _SORTDATE + " TEXT,"
                + ATTENDEE + " TEXT)";

        sqLiteDatabase.execSQL(CREATE_TABLE_MAINTABLE);

        String CREATE_TABLE_GRAPH = "CREATE TABLE " + MOVIE + "("
                + MOVIE_ID + " INTEGER PRIMARY KEY,"
                + MOVIE_NAME + " TEXT,"
                + MOVIE_PATH + " TEXT,"
                + MOVIE_YEAR + " INTEGER)";
        sqLiteDatabase.execSQL(CREATE_TABLE_GRAPH);
    }

    public ArrayList<Movie> getMovieList() {

        ArrayList<Movie> movieList = new ArrayList<Movie>();
        SQLiteDatabase db = getReadableDatabase();
        String rawQuery = "SELECT * FROM " + MOVIE;
        Cursor cursor = db.rawQuery(rawQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Movie movie = new Movie();

                movie.setMovie_name(cursor.getString(cursor.getColumnIndex(MOVIE_NAME)));
                movie.setMovie_year(cursor.getString(cursor.getColumnIndex(MOVIE_YEAR)));
                movie.setMovie_path(cursor.getString(cursor.getColumnIndex(MOVIE_PATH)));
                movie.set_id(cursor.getInt(cursor.getColumnIndex(MOVIE_ID)));

                movieList.add(movie);
            } while (cursor.moveToNext());
        }
        return movieList;
    }

    public ArrayList<Events> getEventList() {

        ArrayList<Events> eventsList = new ArrayList<Events>();
        SQLiteDatabase db = getReadableDatabase();
        String rawQuery = "SELECT * FROM " + EVENT + " ORDER BY " + _SORTDATE + " ASC";
        Cursor cursor = db.rawQuery(rawQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Events events = new Events();
                events.set_id(cursor.getInt(cursor.getColumnIndex(_ID)));
                events.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
                events.setMovie_name(cursor.getString(cursor.getColumnIndex(MOVIE_NAME)));
                events.setStart_date(cursor.getString(cursor.getColumnIndex(START_DATE)));
                events.setEnd_date(cursor.getString(cursor.getColumnIndex(END_DATE)));
                events.setVenue(cursor.getString(cursor.getColumnIndex(VENUE)));
                events.setLat(cursor.getString(cursor.getColumnIndex(LAT)));
                events.set_long(cursor.getString(cursor.getColumnIndex(_LONG)));
                events.setAttendee(cursor.getString(cursor.getColumnIndex(ATTENDEE)));

                eventsList.add(events);
            } while (cursor.moveToNext());
        }
        return eventsList;
    }


    public boolean insertEventData(Events event, String _date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(START_DATE, event.getStart_date());
        contentValues.put(END_DATE, event.getEnd_date());
        contentValues.put(MOVIE_NAME, event.getMovie_name());
        contentValues.put(VENUE, event.getVenue());
        contentValues.put(TITLE, event.getTitle());
        contentValues.put(LAT, event.getLat());
        contentValues.put(_LONG, event.get_long());
        contentValues.put(ATTENDEE, event.getAttendee());
        contentValues.put(_SORTDATE, _date);

        db.insert(EVENT, null, contentValues);
        db.close();
        return true;
    }

    public boolean insertMovieData(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MOVIE_NAME, movie.getMovie_name());
        contentValues.put(MOVIE_PATH, movie.getMovie_path());
        contentValues.put(MOVIE_YEAR, movie.getMovie_year());

        db.insert(MOVIE, null, contentValues);
        db.close();
        return true;
    }


    public int getId() {
        SQLiteDatabase db = getReadableDatabase();
        String rawQuery = "SELECT " + MOVIE_ID + " FROM " + MOVIE;
        Cursor cursor = db.rawQuery(rawQuery, null);
        if (cursor.getCount() > 0) {
            cursor.moveToLast();
            int id = cursor.getInt(0);
            cursor.close();
            return id;
        }
        return -1;
    }


    //update the db for charging out
    public int updateEvent(int id, Events event, String _date) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(START_DATE, event.getStart_date());
        contentValues.put(END_DATE, event.getEnd_date());
        contentValues.put(MOVIE_NAME, event.getMovie_name());
        contentValues.put(VENUE, event.getVenue());
        contentValues.put(TITLE, event.getTitle());
        contentValues.put(LAT, event.getLat());
        contentValues.put(_LONG, event.get_long());
        contentValues.put(ATTENDEE, event.getAttendee());
        contentValues.put(_SORTDATE, _date);

        return db.update(EVENT, contentValues, _ID + " = ? ", new String[]{String.valueOf(id)});
    }


    public Events getEvents(String id) {
        SQLiteDatabase database = getReadableDatabase();

        String rawQuery = "SELECT * FROM " + EVENT + " WHERE " + _ID + " = ?";
        Cursor cursor = database.rawQuery(rawQuery, new String[]{id});
        List<Events> eventsList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Events events = new Events();
                events.set_id(cursor.getInt(cursor.getColumnIndex(_ID)));
                events.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
                events.setMovie_name(cursor.getString(cursor.getColumnIndex(MOVIE_NAME)));
                events.setStart_date(cursor.getString(cursor.getColumnIndex(START_DATE)));
                events.setEnd_date(cursor.getString(cursor.getColumnIndex(END_DATE)));
                events.setVenue(cursor.getString(cursor.getColumnIndex(VENUE)));
                events.setLat(cursor.getString(cursor.getColumnIndex(LAT)));
                events.set_long(cursor.getString(cursor.getColumnIndex(_LONG)));
                events.setAttendee(cursor.getString(cursor.getColumnIndex(ATTENDEE)));

                eventsList.add(events);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return eventsList.get(0);
    }

    public List<Movie> getMovie() {
        SQLiteDatabase database = getReadableDatabase();

        String rawQuery = "SELECT * FROM " + MOVIE;
        Cursor cursor = database.rawQuery(rawQuery, null);
        List<Movie> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Movie movie = new Movie();
                movie.set_id(cursor.getInt(0));
                movie.setMovie_name(cursor.getString(1));
                movie.setMovie_path(cursor.getString(2));
                movie.setMovie_year(cursor.getString(3));
                list.add(movie);

            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }

    public List<String> getMovieName() {
        SQLiteDatabase database = getReadableDatabase();
        String rawQuery = "SELECT * FROM " + MOVIE;
        Cursor cursor = database.rawQuery(rawQuery, null);
        List<String> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }


}

