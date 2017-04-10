package com.example.deepak.myblooddonationapp;

/**
 * Created by standarduser on 18/03/17.
 */
public class HospitalModel {
    String name;
    String addresses;


    public String getAddresses() {
        return addresses;
    }
    public void setAddresses(String addresses) {
        this.addresses = addresses;
    }


    public HospitalModel(String name,String addresses) {
        this.name=name;
        this.addresses=addresses;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
