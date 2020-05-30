package models;

import javafx.beans.property.SimpleStringProperty;

public class TimeTable {

    SimpleStringProperty day, time, venue;
    int id;

    public TimeTable(int id, String day, String time, String venue) {

        this.day = new SimpleStringProperty(day);
        this.time = new SimpleStringProperty(time);
        this.venue = new SimpleStringProperty(venue);
        this.id = id;
    }

    public String getVenue() {
        return venue.get();
    }

    public SimpleStringProperty venueProperty() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue.set(venue);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay() {
        return day.get();
    }

    public void setDay(String day) {
        this.day.set(day);
    }

    public String getTime() {
        return time.get();
    }

    public void setTime(String time) {
        this.time.set(time);
    }
}
