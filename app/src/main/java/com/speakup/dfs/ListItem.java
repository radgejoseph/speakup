package com.speakup.dfs;

public class ListItem {

    private int id;
    private String plate;
    private int image;

    public ListItem(int id, String plate, int image) {
        this.id = id;
        this.plate = plate;
        this.image = image;
    }

    public int getIdL() {
        return id;
    }

    public String getPlateL() {
        return plate;
    }

    public int getImageL() {
        return image;
    }
}
