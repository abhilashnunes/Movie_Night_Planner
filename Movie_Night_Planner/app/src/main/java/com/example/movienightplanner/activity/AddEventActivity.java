package com.example.movienightplanner.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;


import com.example.movienightplanner.Model.Attendee;
import com.example.movienightplanner.Model.Events;
import com.example.movienightplanner.R;
import com.example.movienightplanner.View.DatabaseHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_CONTACTS;

public class AddEventActivity extends AppCompatActivity {

    EditText etTitle;
    EditText etVenue;
    EditText etStartDate;
    EditText etEndDate;
    EditText etLat;
    EditText etLong;
    Spinner movieSpinner;
    Button addAttendee;
    Button button;
    List<Attendee> attendeeList = new ArrayList<>();
    private int mYear, mMonth, mDay, mHour, mMinute;
    String message = "";
    private ArrayList itemsSelected = new ArrayList();
    private static final int PERMISSION_REQUEST_CODE = 200;
    private String sortDate = "";
    private Events getEventByID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listing_view);
        etTitle = (EditText) findViewById(R.id.etEventTitle);
        etStartDate = (EditText) findViewById(R.id.etStartDate);
        etEndDate = (EditText) findViewById(R.id.etEndDate);
        etVenue = (EditText) findViewById(R.id.etVenue);
        etLat = (EditText) findViewById(R.id.etLat);
        etLong = (EditText) findViewById(R.id.etLong);
        movieSpinner = (Spinner) findViewById(R.id.movieSpinner);
        button = (Button) findViewById(R.id.buttonSave);
        addAttendee = (Button) findViewById(R.id.addAttendee);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (checkPermission()) {
                attendeeList = getContact();
            } else {
                requestPermission();
            }
        }


        final DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
        final List<String> list = databaseHelper.getMovieName();


        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            Intent intent = getIntent();
            int id = intent.getIntExtra("_id", 0);
            getEventByID = databaseHelper.getEvents(String.valueOf(id));

            etTitle.setText(getEventByID.getTitle());
            etStartDate.setText(getEventByID.getStart_date());
            etEndDate.setText(getEventByID.getEnd_date());
            etVenue.setText(getEventByID.getVenue());
            etLat.setText(getEventByID.getLat());
            etLong.setText(getEventByID.get_long());
            button.setVisibility(View.GONE);

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            movieSpinner.setAdapter(spinnerArrayAdapter);
            if (getEventByID.getMovie_name() != null) {
                int spinnerPosition = spinnerArrayAdapter.getPosition(getEventByID.getMovie_name());
                movieSpinner.setSelection(spinnerPosition);
            }
        } else {
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            movieSpinner.setAdapter(spinnerArrayAdapter);

            addAttendee.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialogBox();
                }
            });

            movieSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            etStartDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("IN DATE", "YES");
                    getStartDate();
                }
            });
            etEndDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (etStartDate.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Enter Start Date", Toast.LENGTH_SHORT).show();
                    } else {
                        getEndDate();
                    }

                }
            });

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isValid()) {
                        Log.d("IS VALID", "YES");
                        Events events = new Events();
                        String title = etTitle.getText().toString();
                        String movie = movieSpinner.getSelectedItem().toString();
                        String venue = etVenue.getText().toString();
                        String lat = etLat.getText().toString();
                        String _long = etLong.getText().toString();
                        String startDate = etStartDate.getText().toString();
                        String endDate = etEndDate.getText().toString();
                        String selectedMember = "";
                        Log.d("Attendee Size", String.valueOf(attendeeList.size()));
                        for (int i = 0; i < attendeeList.size(); i++) {
                            Log.i("Attendee Name", attendeeList.get(i).getName() + " " + attendeeList.get(i).isSelected());
                            if (attendeeList.get(i).isSelected()) {
                                selectedMember = selectedMember + attendeeList.get(i).getName() + ",";
                            }
                        }

                        events.setTitle(title);
                        events.setMovie_name(movie);
                        events.setVenue(venue);
                        events.setLat(lat);
                        events.set_long(_long);
                        events.setStart_date(startDate);
                        events.setEnd_date(endDate);
                        events.setAttendee(selectedMember);

                        databaseHelper.insertEventData(events, sortDate);
                        AddEventActivity.this.finish();
                    } else {
                        Log.d("IS VALID", "NO");
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    private void getStartDate() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        etStartDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        sortDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + " 00:00:00";
                        getStartTime(date);

                    }
                }, mYear, mMonth, mDay);
        Calendar calendar = Calendar.getInstance();
        calendar.set(mYear, mMonth + 2, mDay);
        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
        datePickerDialog.show();
    }

    private void getStartTime(final String date) {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        String AM_PM;
                        if (hourOfDay < 12) {
                            AM_PM = "AM";
                        } else {
                            AM_PM = "PM";
                            hourOfDay = hourOfDay - 12;
                        }

                        etStartDate.setText(date + " " + hourOfDay + ":" + minute + " " + AM_PM);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    private void getEndDate() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        getEndTime(date);

                    }
                }, mYear, mMonth, mDay);
        Calendar calendar = Calendar.getInstance();
        String[] array = etStartDate.getText().toString().split("-");
        calendar.set(mYear, Integer.parseInt(array[1]) - 1, Integer.parseInt(array[0]));

        Calendar calendarMaxDate = Calendar.getInstance();
        calendarMaxDate.set(mYear, mMonth + 2, mDay);

        datePickerDialog.getDatePicker().setMaxDate(calendarMaxDate.getTimeInMillis());
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }

    private void getEndTime(final String date) {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        String AM_PM;
                        if (hourOfDay < 12) {
                            AM_PM = "AM";
                        } else {
                            AM_PM = "PM";
                            hourOfDay = hourOfDay - 12;
                        }

                        etEndDate.setText(date + " " + hourOfDay + ":" + minute + " " + AM_PM);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    List<Attendee> getContact() {

        Cursor contacts = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        List<Attendee> listContact = new ArrayList<>();

        int nameFieldColumnIndex = contacts.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        int numberFieldColumnIndex = contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

        if (contacts.moveToFirst()) {
            do {
                Log.d("Contact", contacts.getString(nameFieldColumnIndex) +
                        contacts.getString(numberFieldColumnIndex));
                listContact.add(new Attendee(contacts.getString(nameFieldColumnIndex),
                        contacts.getString(numberFieldColumnIndex)));
            } while (contacts.moveToNext());
        }
        contacts.close();
        attendeeList = listContact;
        return listContact;
    }

    void showDialogBox() {
        Dialog dialog;
        int attendeeSize = 0;
        if (attendeeList.size() > 20) {
            attendeeSize = 20;
            attendeeList = attendeeList.subList(0, 20);
        } else {
            attendeeSize = attendeeList.size();
        }
        String[] items = new String[attendeeSize];
        boolean[] itemBoolenSelected = new boolean[attendeeSize];
        for (int i = 0; i < attendeeSize; i++) {
            items[i] = attendeeList.get(i).getName();
            itemBoolenSelected[i] = attendeeList.get(i).isSelected();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Attendee : ");
        builder.setMultiChoiceItems(items, itemBoolenSelected,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedItemId,
                                        boolean isSelected) {
                        attendeeList.get(selectedItemId).setSelected(isSelected);
                        if (isSelected) {
                            itemsSelected.add(selectedItemId);
                        } else if (itemsSelected.contains(selectedItemId)) {
                            itemsSelected.remove(Integer.valueOf(selectedItemId));
                        }
                    }
                })
                .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //Your logic when OK button is clicked
                        Log.d("ITEM LENGHT", String.valueOf(itemsSelected.size()));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        dialog = builder.create();
        dialog.show();
    }

    boolean isValid() {
        Log.d("IS VALID", "ENTER");
        if (etTitle.getText().toString().isEmpty()) {
            message = "Enter Title";
            return false;
        } else if (etStartDate.getText().toString().isEmpty()) {
            message = "Enter Start Date";
            return false;
        } else if (etEndDate.getText().toString().isEmpty()) {
            message = "Enter End Date";
            return false;
        } else if (movieSpinner.getSelectedItem().toString().isEmpty()) {
            message = "Enter Movie";
            return false;
        } else if (etVenue.getText().toString().isEmpty()) {
            message = "Enter Venue";
            return false;
        } else if (etLat.getText().toString().isEmpty()) {
            message = "Enter Lat";
            return false;
        } else if (etLong.getText().toString().isEmpty()) {
            message = "Enter Long";
            return false;
        } else if (getItemSelected() == 0) {
            message = "Enter Enter Attendee";
            return false;
        } else {
            message = "Event Created";
            return true;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), READ_CONTACTS);

        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{READ_CONTACTS}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean readContactAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (readContactAccepted) {
                        attendeeList = getContact();
                    } else {

//                        Snackbar.make(view, "Permission Denied, You cannot access location data and camera.", Snackbar.LENGTH_LONG).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                showMessageOKCancel("You need to allow access to the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                requestPermissions(new String[]{READ_CONTACTS}, PERMISSION_REQUEST_CODE);
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }


                break;
        }
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new android.support.v7.app.AlertDialog.Builder(AddEventActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    public int getItemSelected() {
        int length = 0;
        for (int i = 0; i < attendeeList.size(); i++) {
            if (attendeeList.get(i).isSelected()) {
                length = length + 1;
            }
        }
        return length;
    }
}
