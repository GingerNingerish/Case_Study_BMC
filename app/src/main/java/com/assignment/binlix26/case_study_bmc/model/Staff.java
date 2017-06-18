package com.assignment.binlix26.case_study_bmc.model;

import java.io.Serializable;

/**
 * Created by binlix26 on 13/06/17.
 */

public class Staff implements Serializable {

    private int id;
    private String name;
    private String title;
    private String department;
    private String phone;
    private byte[] photo;

    public Staff() {
    }

    public Staff(int id, String name, String title) {
        this.id = id;
        this.name = name;
        this.title = title;
    }

    public Staff(int id, String name, String title, String department, String phone, byte[] photo) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.department = department;
        this.phone = phone;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getDepartment() {
        return department;
    }

    public String getPhone() {
        return phone;
    }

    public byte[] getPhoto() {
        return photo;
    }
}
