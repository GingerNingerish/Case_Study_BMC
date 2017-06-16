package com.assignment.binlix26.case_study_bmc.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by binlix26 on 15/06/17.
 */

public class BMCCalender {
    public static String getCurrentTimeAsString(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }
}
