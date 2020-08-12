package com.example.movienightplanner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


import com.example.movienightplanner.Adapter.EventAdapter;
import com.example.movienightplanner.Model.Events;
import com.example.movienightplanner.R;
import com.example.movienightplanner.View.DatabaseHelper;
import com.facebook.stetho.Stetho;

import java.util.ArrayList;
import java.util.Collections;

public class EventListingActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;
    private DatabaseHelper databaseHelper;
    private ArrayList<Events> eventList = new ArrayList<>();
    private static final int PERMISSION_REQUEST_CODE = 200;
    private RecyclerView recyclerView;
    private EventAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Stetho.initializeWithDefaults(this);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fbAddMovie);
        recyclerView = (RecyclerView) findViewById(R.id.rvMovieList);

        setUpUI();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventListingActivity.this, AddEventActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setUpUI() {
        databaseHelper = new DatabaseHelper(this);
        eventList = databaseHelper.getEventList();

        mAdapter = new EventAdapter(eventList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_sort, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort:
                Collections.reverse(eventList);
                mAdapter.notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpUI();
    }
}
