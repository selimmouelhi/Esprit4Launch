package com.example.selimmouelhi.esprit4launch.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    @Expose
    private String id ;
    @SerializedName("nom")
    @Expose
    private String nom ;
    @SerializedName("prenom")
    @Expose
    private String prenom ;
    @SerializedName("mail")
    @Expose
    private  String mail ;
    @SerializedName("phone")
    @Expose
    private  int phone;
    @SerializedName("image_url")
    @Expose
    private String image ;

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", mail='" + mail + '\'' +
                ", phone=" + phone +
                ", image='" + image + '\'' +
                ", status='" + status + '\'' +
                ", followers=" + followers +
                ", following=" + following +
                ", friends=" + friends +
                '}';
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public int getFriends() {
        return friends;
    }

    public void setFriends(int friends) {
        this.friends = friends;
    }

    @SerializedName("status")
    @Expose

    private String status ;
    @SerializedName("followers")
    @Expose
    private int followers ;
    @SerializedName("following")
    @Expose
    private int  following ;
    @SerializedName("friends")
    @Expose
    private int  friends ;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    public User(String id, String nom, String prenom,  String mail, int phone) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }



    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public User(String id, String nom, String prenom, String mail, int phone, String image) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.phone = phone;
        this.image = image;
    }
    public User(){

    }

}

