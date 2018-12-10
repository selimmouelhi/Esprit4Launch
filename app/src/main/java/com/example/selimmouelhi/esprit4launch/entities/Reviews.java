package com.example.selimmouelhi.esprit4launch.entities;

public class Reviews {

    int id;
    int rest_id;
    String comment;
    float rating;


    public Reviews() { }
    public Reviews(int id, int rest_id, String comment, float rating) {
        this.id = id;
        this.rest_id = rest_id;
        this.comment = comment;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRest_id() {
        return rest_id;
    }

    public void setRest_id(int rest_id) {
        this.rest_id = rest_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

}
