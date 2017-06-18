package com.assignment.binlix26.case_study_bmc.utility;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.assignment.binlix26.case_study_bmc.R;

import java.io.ByteArrayOutputStream;
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
    public static Bitmap bitmap;

    public static String getCurrentTimeAsString(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

    // convert from bitmap to byte array
    public static byte[] getImageBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap toBitmapImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
