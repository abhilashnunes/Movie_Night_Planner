package com.example.movienightplanner.Model;

public class Events {
    private int _id;
    private String title;
    private String movie_name;
    private String start_date;
    private String end_date;
    private String venue;
    private String attendee;
    private String lat;
    private String _long;

    public Events() {
    }

    public Events(String title, String movie_name, String start_date, String end_date, String venue, String attendee, String lat, String _long) {
        this.title = title;
        this.movie_name = movie_name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.venue = venue;
        this.attendee = attendee;
        this.lat = lat;
        this._long = _long;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String get_long() {
        return _long;
    }

    public void set_long(String _long) {
        this._long = _long;
    }

    public String getAttendee() {
        return attendee;
    }

    public void setAttendee(String attendee) {
        if (attendee == null || attendee.isEmpty()) {
            this.attendee = "0";
        } else {
            this.attendee = attendee;
        }

    }
}
