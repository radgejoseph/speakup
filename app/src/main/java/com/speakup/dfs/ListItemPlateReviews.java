package com.speakup.dfs;

public class ListItemPlateReviews {

    //private String vehicle;
    //private String body_plate;
    private int ratings;
    private String narrative;
    private String username;

//    public ListItemPlateReviews(String vehicle, String body_plate, int ratings, String narrative, String username) {
public ListItemPlateReviews(String username, int ratings, String narrative) {
        //this.vehicle = vehicle;
        //this.body_plate = body_plate;
        this.ratings = ratings;
        this.narrative = narrative;
        this.username = username;
    }


//    public String getVehicle() {
//        return vehicle;
//    }
//
//    public String getBody_plate() {
//        return body_plate;
//    }

    public int getRatings() {
        return ratings;
    }

    public String getNarrative() {
        return narrative;
    }

    public String getUsername() {
        return username;
    }
}
