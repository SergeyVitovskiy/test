package com.example.apicategory;

public class Category {
    private int id;
    private String title;
    private String image;
    private int displayed;

    public Category(int id, String title, String image, int displayed) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.displayed = displayed;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public int getDisplayed() {
        return displayed;
    }
}
