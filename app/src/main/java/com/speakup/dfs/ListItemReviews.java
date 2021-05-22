package com.speakup.dfs;

public class ListItemReviews {

    private String vehicle;
    private String body_plate;
    private int ratings;
    private String narrative;

    public ListItemReviews(String vehicle, String body_plate, int ratings, String narrative) {
        this.vehicle = vehicle;
        this.body_plate = body_plate;
        this.ratings = ratings;
        this.narrative = narrative;
    }

    public String getVehicle() {
        return vehicle;
    }

    public String getBody_plate() {
        return body_plate;
    }

    public int getRatings() {
        return ratings;
    }

    public String getNarrative() {
        return narrative;
    }
}
