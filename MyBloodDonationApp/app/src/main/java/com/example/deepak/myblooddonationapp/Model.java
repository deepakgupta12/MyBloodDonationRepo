package com.example.deepak.myblooddonationapp;

/**
 * Created by standarduser on 28/02/17.
 */
public class Model {

    public Model(String name, String email) {
        this.name = name;
        this.email = email;
    }

    private String name;
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
