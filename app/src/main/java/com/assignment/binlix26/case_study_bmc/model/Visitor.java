package com.assignment.binlix26.case_study_bmc.model;

import java.io.Serializable;

/**
 * Created by binlix26 on 15/06/17.
 */

public class Visitor implements Serializable {

    private int id;
    private String name;
    private String business;
    private String phone;
    private String purpose;
    private int status;
    private String signIn;
    private String signOut;

    public Visitor() {

    }

    public Visitor(int id, String name, String business) {
        this.id = id;
        this.name = name;
        this.business = business;
    }

    public Visitor(int id, String name, String business, String phone, String purpose, int status) {
        this.id = id;
        this.name = name;
        this.business = business;
        this.phone = phone;
        this.purpose = purpose;
        this.status = status;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getBusiness() {
        return business;
    }

    public int getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public String getPurpose() {
        return purpose;
    }
}

