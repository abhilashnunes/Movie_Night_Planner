package com.example.movienightplanner.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.example.movienightplanner.Model.Events;
import com.example.movienightplanner.Model.Movie;
import com.example.movienightplanner.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
                if (databaseHelper.getId() == -1) {
                    setEventFromText(databaseHelper);
                    setMovieFromText(databaseHelper);
                }
                Intent intent = new Intent(SplashScreen.this, Dashboard.class);
                startActivity(intent);
                finish();
            }
        };
        timer.schedule(timerTask, 3000);
    }

    void setEventFromText(DatabaseHelper databaseHelper) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("events.txt")));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                Log.d("LINE", mLine);
                if (mLine.startsWith("//")) {
                    Log.d("LINE", mLine);
                } else {
                    String newLine = mLine.replace("\"", "");
                    Log.d("New LINE", newLine);
                    String[] array = newLine.split(",");
                    Events events = new Events();
                    events.setTitle(array[1]);
                    events.setStart_date(array[2]);
                    events.setEnd_date(array[3]);
                    events.setVenue(array[4]);
                    events.setLat(array[5]);
                    events.set_long(array[6]);
                    events.setAttendee("");
                    events.setMovie_name("");
                    String[] sortArray = array[2].substring(0, 9).split("/");
                    String _sortdate = sortArray[2] + "-" + sortArray[1] + "-" + sortArray[0];
                    Log.d("Sort Date", _sortdate);
                    databaseHelper.insertEventData(events, _sortdate);
                }
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
    }

    void setMovieFromText(DatabaseHelper databaseHelper) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("movies.txt")));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                if (mLine.startsWith("//")) {
                    Log.d("LINE", mLine);
                } else {
                    String newLine = mLine.replace("\"", "");
                    Log.d("New LINE", newLine);
                    String[] array = newLine.split(",");
                    Movie movie = new Movie();
                    movie.setMovie_name(array[1]);
                    movie.setMovie_year(array[2]);
                    movie.setMovie_path("");
                    databaseHelper.insertMovieData(movie);
                }
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
    }
}
