package com.example.movienightplanner.Model;

public class Attendee {
    private int _id;
    private String name;
    private String number;
    boolean isSelected;

    public Attendee(String name, String number) {
        this.name = name;
        this.number = number;
        this.isSelected = false;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
