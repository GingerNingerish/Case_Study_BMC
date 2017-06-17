package com.assignment.binlix26.case_study_bmc.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by binlix26 on 15/06/17.
 */

public class Utility {

    public static List<String> purposeList = Arrays.asList("General Business", "Drop In", "Scheduled Appointment", "Other");
    public static List<String> visitorListFilter = Arrays.asList("All", "Check In", "Check Out");

    public static String getCurrentTimeAsString(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }
}
