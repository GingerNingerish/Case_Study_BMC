package com.assignment.binlix26.case_study_bmc.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import static com.assignment.binlix26.case_study_bmc.data.BMCContract.AppointmentEntry;
import static com.assignment.binlix26.case_study_bmc.data.BMCContract.CONTENT_AUTHORITY;
import static com.assignment.binlix26.case_study_bmc.data.BMCContract.PATH_APPOINTMENT;
import static com.assignment.binlix26.case_study_bmc.data.BMCContract.PATH_STAFF;
import static com.assignment.binlix26.case_study_bmc.data.BMCContract.PATH_VISITOR;
import static com.assignment.binlix26.case_study_bmc.data.BMCContract.StaffEntry;
import static com.assignment.binlix26.case_study_bmc.data.BMCContract.VisitorEntry;

/**
 * Created by binlix26 on 11/06/17.
 */

public class BMCContentProvider extends ContentProvider {
    // Use an int for each URI we will run, this represents the different queries
    private static final int STAFF = 1;
    private static final int STAFF_ID = 2;
    private static final int VISITOR = 11;
    private static final int VISITOR_ID = 12;
    private static final int APPOINTMENT = 21;
    private static final int APPOINTMENT_ID = 22;
//    private static final int CHECK_IN = 31;
//    private static final int CHECK_IN_ID = 32;

    private DatabaseHelper helper;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    /**
     * Builds a UriMatcher that is used to determine witch database request is being made.
     */

    // All paths to the UriMatcher have a corresponding code to return
    // when a match is found (the ints above).
    static {
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_STAFF, STAFF);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_STAFF + "/#", STAFF_ID);

        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_VISITOR, VISITOR);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_VISITOR + "/#", VISITOR_ID);

        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_APPOINTMENT, APPOINTMENT);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_APPOINTMENT + "/#", APPOINTMENT_ID);

//        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_CHECK_IN_FORM, CHECK_IN);
//        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_CHECK_IN_FORM + "/#", CHECK_IN_ID);

    }

    @Override
    public boolean onCreate() {
        helper = new DatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = helper.getWritableDatabase();

        Cursor cursor;

        long _id;

        // join tables for appointment
        String app_join = AppointmentEntry.TABLE_NAME
                + " inner join "
                + StaffEntry.TABLE_NAME
                + " on " + AppointmentEntry.COLUMN_STAFF + " = "
                + StaffEntry.TABLE_NAME + "." + StaffEntry._ID
                + " inner join "
                + VisitorEntry.TABLE_NAME
                + " on " + AppointmentEntry.COLUMN_VISITOR + " = "
                + VisitorEntry.TABLE_NAME + "." + VisitorEntry._ID;

        // join tables for check_in
        /*String check_in_join = CheckInEntry.TABLE_NAME
                + " inner join "
                + VisitorEntry.TABLE_NAME
                + " on " + CheckInEntry.COLUMN_VISITOR + " = "
                + VisitorEntry.TABLE_NAME + "." + VisitorEntry._ID;*/

        // sql builder
        SQLiteQueryBuilder builder;

        // representative of queries
        switch (uriMatcher.match(uri)) {
            case VISITOR:
                cursor = database.query(
                        VisitorEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case VISITOR_ID:
                _id = ContentUris.parseId(uri);
                cursor = database.query(VisitorEntry.TABLE_NAME,
                        projection,
                        VisitorEntry._ID + " = ?",
                        new String[]{String.valueOf(_id)},
                        null,
                        null,
                        sortOrder
                );
                break;
            case STAFF:
                cursor = database.query(
                        StaffEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case STAFF_ID:
                _id = ContentUris.parseId(uri);
                cursor = database.query(StaffEntry.TABLE_NAME,
                        projection,
                        StaffEntry._ID + " = ?",
                        new String[]{String.valueOf(_id)},
                        null,
                        null,
                        sortOrder
                );
                break;
            case APPOINTMENT:
                builder = new SQLiteQueryBuilder();
                builder.setTables(app_join);
                cursor = builder.query(database, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            case APPOINTMENT_ID:
                builder = new SQLiteQueryBuilder();
                builder.setTables(app_join);
                _id = ContentUris.parseId(uri);
                cursor = builder.query(
                        database,
                        projection,
                        AppointmentEntry._ID + " = ?",
                        new String[]{String.valueOf(_id)},
                        null,
                        null,
                        sortOrder
                );
                break;
            /*case CHECK_IN:
                builder = new SQLiteQueryBuilder();
                builder.setTables(check_in_join);
                cursor = builder.query(database, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            case CHECK_IN_ID:
                builder = new SQLiteQueryBuilder();
                builder.setTables(check_in_join);
                _id = ContentUris.parseId(uri);
                cursor = builder.query(
                        database,
                        projection,
                        CheckInEntry._ID + " = ?",
                        new String[]{String.valueOf(_id)},
                        null,
                        null,
                        sortOrder
                );
                break;*/
            default:
                throw new IllegalArgumentException("Querying Unknown URI: " + uri);
        }
        // Set the notification URI for the cursor to the one passed into the function. This
        // causes the cursor to register a content observer to watch for changes that happen to
        // this URI and any of it's descendants. By descendants, we mean any URI that begins
        // with this path.
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        switch (uriMatcher.match(uri)) {
            case VISITOR:
                return insertRow(uri, values, VisitorEntry.TABLE_NAME);
            case STAFF:
                return insertRow(uri, values, StaffEntry.TABLE_NAME);
            case APPOINTMENT:
                return insertRow(uri, values, AppointmentEntry.TABLE_NAME);

            /*case CHECK_IN:
                return insertRow(uri, values, CheckInEntry.TABLE_NAME);*/

            default:
                throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
        }
    }

    private Uri insertRow(Uri uri, ContentValues values, String tableName) {
        SQLiteDatabase database = helper.getWritableDatabase();
        long _id = database.insert(tableName, null, values);

        if (_id <= 0) {
            Log.e("Error", "Inserting error for URI " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, _id);
    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = helper.getWritableDatabase();
        // Number of rows affected
        int rows;

        switch (uriMatcher.match(uri)) {
            case VISITOR:
                rows = database.delete(VisitorEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case STAFF:
                rows = database.delete(StaffEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case APPOINTMENT:
                rows = database.delete(AppointmentEntry.TABLE_NAME, selection, selectionArgs);
                break;

            /*case CHECK_IN:
                rows = database.delete(CheckInEntry.TABLE_NAME, selection, selectionArgs);
                break;*/

            default:
                throw new UnsupportedOperationException("Deleting Unknown URI: " + uri);
        }

        //Because null could delete all rows
        if (selection == null || rows != 0)
            getContext().getContentResolver().notifyChange(uri, null);

        return rows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = helper.getWritableDatabase();
        // Number of rows affected
        int rows;

        switch (uriMatcher.match(uri)) {
            case VISITOR:
                rows = database.update(VisitorEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case STAFF:
                rows = database.update(StaffEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case APPOINTMENT:
                rows = database.update(AppointmentEntry.TABLE_NAME, values, selection, selectionArgs);
                break;

            /*case CHECK_IN:
                rows = database.update(CheckInEntry.TABLE_NAME, values, selection, selectionArgs);
                break;*/

            default:
                throw new UnsupportedOperationException("Updating Unknown URI: " + uri);
        }

        if (rows != 0)
            getContext().getContentResolver().notifyChange(uri, null);

        return rows;
    }
}
