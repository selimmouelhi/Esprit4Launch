package com.example.selimmouelhi.esprit4launch.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    @Expose
    private int id ;
    @SerializedName("nom")
    @Expose
    private String nom ;
    @SerializedName("prenom")
    @Expose
    private String prenom ;
    @SerializedName("datenaissance")
    @Expose
    private int dateN;
    @SerializedName("mail")
    @Expose
    private  String mail ;
    @SerializedName("phone")
    @Expose
    private  int phone;

    public User(int id, String nom, String prenom, int dateN, String mail, int phone) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateN = dateN;
        this.mail = mail;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getDateN() {
        return dateN;
    }

    public void setDateN(int dateN) {
        this.dateN = dateN;
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
}
