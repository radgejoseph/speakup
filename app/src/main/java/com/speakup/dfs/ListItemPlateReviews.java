package com.speakup.dfs;

public class ListItemPlateReviews {

    private final int ratings;
    private final String narrative;
    private final String username;
    private final String created_at;

public ListItemPlateReviews(String username, int ratings, String narrative, String created_at) {

        this.ratings = ratings;
        this.narrative = narrative;
        this.username = username;
        this.created_at = created_at;
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

    public String getTimestampL() {
        return created_at;
    }
}
