package com.assignment.binlix26.case_study_bmc.model;

import java.io.Serializable;

/**
 * Created by binlix26 on 13/06/17.
 */

public class Appointment implements Serializable {

    private int id;
    private String description;
    private String time;
    private int visitorId;
    private int staffId;

    public Appointment(int id, String description, String time) {
        this.id = id;
        this.description = description;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        return time;
    }

    public int getVisitorId() {
        return visitorId;
    }

    public int getStaffId() {
        return staffId;
    }
}
