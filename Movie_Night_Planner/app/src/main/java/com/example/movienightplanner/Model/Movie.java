package com.example.movienightplanner.Model;

public class Movie {
    private int _id;
    private String movie_year;
    private String movie_name;
    private String movie_path;

    public Movie() {
    }

    public Movie(String movie_year, String movie_name, String movie_path) {
        this.movie_year = movie_year;
        this.movie_name = movie_name;
        this.movie_path = movie_path;
    }

    public Movie(String movie_year, String movie_name) {
        this.movie_year = movie_year;
        this.movie_name = movie_name;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getMovie_year() {
        return movie_year;
    }

    public void setMovie_year(String movie_year) {
        this.movie_year = movie_year;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public String getMovie_path() {
        return movie_path;
    }

    public void setMovie_path(String movie_path) {
        this.movie_path = movie_path;
    }
}
