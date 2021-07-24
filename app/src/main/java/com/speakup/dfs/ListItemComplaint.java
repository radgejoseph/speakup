package com.speakup.dfs;

public class ListItemComplaint {

    private final String vehicle;
    private final String body_plate;
    private final String narrative;
    private final String date;
    private final String time;
    private final String image_name;
    private final String status;

    public ListItemComplaint(String vehicle, String body_plate, String narrative, String date, String time, String image_name, String status) {
        this.vehicle = vehicle;
        this.body_plate = body_plate;
        this.narrative = narrative;
        this.date = date;
        this.time = time;
        this.image_name = image_name;
        this.status = status;
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

    public String getStatus() {
        return status;
    }
}
