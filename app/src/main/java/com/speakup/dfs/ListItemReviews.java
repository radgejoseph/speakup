package com.speakup.dfs;

public class ListItemReviews {

    private String vehicle;
    private String body_plate;
    private int ratings;
    private String narrative;
    private String created_at;

    public ListItemReviews(String vehicle, String body_plate, int ratings, String narrative, String created_at) {
        this.vehicle = vehicle;
        this.body_plate = body_plate;
        this.ratings = ratings;
        this.narrative = narrative;
        this.created_at = created_at;
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

    public String getTimestampL() {
        return created_at;
    }
}
