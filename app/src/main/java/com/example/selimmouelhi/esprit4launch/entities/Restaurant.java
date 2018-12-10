package com.example.selimmouelhi.esprit4launch.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Restaurant {

    @SerializedName("id")
    @Expose
        private  int id ;

    @SerializedName("name")
    @Expose
        private String name ;
    @SerializedName("adresse")
    @Expose
        private String adresse ;
    @SerializedName("rating")
    @Expose
    private  float rating ;

    @SerializedName("ouvert")
    @Expose
    private int ouvert;

    @SerializedName("ferme")
    @Expose
    private int ferme;

    @SerializedName("radius")
    @Expose
    private float radius ;


    @SerializedName("ratingS")
    @Expose
    private float ratingS;

    @SerializedName("ratingTasty")
    @Expose
    private float ratingTasty;

    @SerializedName("image")
    @Expose
    private  String imageUrl;

    @SerializedName("description")
    @Expose
    private  String description;

    public int getOuvert() {
        return ouvert;
    }

    public int getFerme() {
        return ferme;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Restaurant(){

    }

    public Restaurant(int id, String name, String adresse, float rating, int ouvert, int ferme, float radius, float ratingS, float ratingTasty, String imageUrl, String description) {
        this.id = id;
        this.name = name;
        this.adresse = adresse;
        this.rating = rating;
        this.ouvert = ouvert;
        this.ferme = ferme;
        this.radius = radius;
        this.ratingS = ratingS;
        this.ratingTasty = ratingTasty;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    /*
        * "id": 4,
    "name": "chaneb",
    "adresse": "petite ariana",
    "rating": 5,
    "ouvert": 0,
    "ferme": 1,
    "radius": 1000,
    "ratingS": 2.2,
    "ratingTasty": 4.5,
    "image": "CmRaAAAAz0GYEJK2R73NfmrVooyq4ZMxM288wYXuuzDjHI7ohnXQgsuoRqYVL7e0EOHH-cq67w4sw7KloICoIBj5FmOdT6JCi4dgdUs5hONeq0kb-8t2kP-r6bXwZi1I3kmPrallEhCpB80RGCWXhH__-vl_e_ZjGhTQfzpEwRQfTHbi4GttbrFyZBBlPQ"*/
    public Restaurant(int id, String name, String adresse, float rating, int ouvert, int ferme, float radius, String imageUrl) {
        this.id = id;
        this.name = name;
        this.adresse = adresse;
        this.rating = rating;
        this.ouvert = ouvert;
        this.ferme = ferme;
        this.radius = radius;
        this.imageUrl = imageUrl;
    }

    public Restaurant(String name, String adresse, float rating, int ouvert, int ferme, float radius, String imageUrl, float ratingS, float ratingTasty) {
        this.name = name;
        this.adresse = adresse;
        this.rating = rating;
        this.ouvert = ouvert;
        this.ferme = ferme;
        this.radius = radius;
        this.imageUrl = imageUrl;
        this.ratingS = ratingS;
        this.ratingTasty = ratingTasty;
    }

    public float getRatingS() {

        return ratingS;
    }

    public void setRatingS(float ratingS) {
        this.ratingS = ratingS;
    }

    public float getRatingTasty() {
        return ratingTasty;
    }

    public void setRatingTasty(float ratingTasty) {
        this.ratingTasty = ratingTasty;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Restaurant(int id, String name, String adresse, float rating, int ouvert, int ferme, float radius) {
        this.id = id;

        this.name = name;
        this.adresse = adresse;
        this.rating = rating;
        this.ouvert = ouvert;
        this.ferme = ferme;
        this.radius = radius;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int isOuvert() {
        return ouvert;
    }

    public void setOuvert(int ouvert) {
        this.ouvert = ouvert;
    }

    public int isFerme() {
        return ferme;
    }

    public void setFerme(int ferme) {
        this.ferme = ferme;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public  Restaurant(String name, String adrese){
        this.name = name;
        adresse = adrese;
    }
}
