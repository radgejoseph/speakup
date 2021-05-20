package com.speakup.dfs;

public class ListItemPlateReviews {

    private int ratings;
    private String narrative;
    private String username;

public ListItemPlateReviews(String username, int ratings, String narrative) {

        this.ratings = ratings;
        this.narrative = narrative;
        this.username = username;
    }

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
