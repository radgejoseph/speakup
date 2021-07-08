package com.speakup.dfs;

public class ListItemCommendComplaint {

    private String vehicle;
    private String body_plate;
    private String narrative;
    private String date;
    private String time;
    private String image_name;


    public ListItemCommendComplaint(String vehicle, String body_plate, String narrative, String date, String time, String image_name) {
        this.vehicle = vehicle;
        this.body_plate = body_plate;
        this.narrative = narrative;
        this.date = date;
        this.time = time;
        this.image_name = image_name;
    }

    public String getVehicle() {
        return vehicle;
    }

    public String getBody_plate() {
        return body_plate;
    }

    public String getNarrative() {
        return narrative;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getImage_name() {
        return image_name;
    }
}
