package com.assignment.binlix26.case_study_bmc.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by binlix26 on 11/06/17.
 */

public class BMCContract {
    public static final String CONTENT_AUTHORITY = "com.casestudy.bmc.dataprovider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_STAFF = "staff";
    public static final String PATH_VISITOR = "visitor";
    public static final String PATH_APPOINTMENT = "appointment";
//    public static final String PATH_CHECK_IN_FORM = "check_in";

    public static final Integer CHECKOUT = 0;
    public static final Integer CHECKIN = 1;

    public static final class StaffEntry implements BaseColumns {
        // Content URI represents the base location for the table
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_STAFF).build();
        // Define the table schema
        // Table name
        public static final String TABLE_NAME = "STAFF";
        // Column names
        public static final String COLUMN_NAME = "staff_name";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_PHONE = "s_phone";
        public static final String COLUMN_DEPARTMENT = "department";
        public static final String COLUMN_PHOTO = "photo";
        public static final String COLUMN_DATE_CREATED = "s_date_created";

        public static Uri buildStaffUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class VisitorEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_VISITOR).build();

        // Table name
        public static final String TABLE_NAME = "VISITOR";

        // Column names
        public static final String COLUMN_NAME = "visitor_name";
        public static final String COLUMN_BUSINESS_NAME = "business_name";
        public static final String COLUMN_PURPOSE = "purpose";
        public static final String COLUMN_PHONE = "v_phone";
        public static final String COLUMN_SIGN_IN = "in_datetime";
        public static final String COLUMN_SIGN_OUT = "out_datetime";
        public static final String COLUMN_STATUS = "status";
//        public static final String COLUMN_DATE_CREATED = "v_date_created";

        public static Uri buildVisitorUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class AppointmentEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_APPOINTMENT).build();

        // TAble name
        public static final String TABLE_NAME = "APPOINTMENT";

        // Column names
        public static final String COLUMN_DESCRIPTION = "app_description";
        public static final String COLUMN_DATE_CREATED = "app_date_created";
        public static final String COLUMN_DATETIME = "app_datetime";
        public static final String COLUMN_VISITOR = "app_visitor";
        public static final String COLUMN_STAFF = "app_staff";

        public static Uri buildAppointmentUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    /*public static final class CheckInEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CHECK_IN_FORM).build();

        // Table name
        public static final String TABLE_NAME = "CHECK_IN";

        // Column names
        public static final String COLUMN_VISITOR = "sign_in_visitor";
        public static final String COLUMN_PURPOSE = "purpose";
        public static final String COLUMN_SIGN_IN = "in_datetime";
        public static final String COLUMN_SIGN_OUT = "out_datetime";
        public static final String COLUMN_STATUS = "status";

        public static Uri buildCheckInUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }*/
}
