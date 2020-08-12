package com.example.movienightplanner.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.movienightplanner.R;
import com.example.movienightplanner.activity.CalenderActivity;
import com.example.movienightplanner.activity.EventListingActivity;
import com.example.movienightplanner.activity.MovieListingActivity;


public class Dashboard extends AppCompatActivity {

    TextView tvMovies, tvEvents, tvCalender;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        tvMovies = (TextView) findViewById(R.id.tvMovie);
        tvEvents = (TextView) findViewById(R.id.tvEvents);
        tvCalender = (TextView) findViewById(R.id.tvCalender);

        tvEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, EventListingActivity.class);
                startActivity(intent);
            }
        });

        tvMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, MovieListingActivity.class);
                startActivity(intent);
            }
        });

        tvCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, CalenderActivity.class);
                startActivity(intent);
            }
        });
    }
}
