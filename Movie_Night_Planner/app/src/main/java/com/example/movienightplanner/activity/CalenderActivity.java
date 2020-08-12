package com.example.movienightplanner.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.movienightplanner.R;
import com.example.movienightplanner.View.CalenderCustomView;


public class CalenderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        CalenderCustomView calenderCustomView = (CalenderCustomView) findViewById(R.id.tvCalender);
    }
}
