package com.assignment.binlix26.case_study_bmc.admin;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.assignment.binlix26.case_study_bmc.AdminActivity;
import com.assignment.binlix26.case_study_bmc.R;
import com.assignment.binlix26.case_study_bmc.data.BMCContract.AppointmentEntry;
import com.assignment.binlix26.case_study_bmc.data.BMCContract.StaffEntry;
import com.assignment.binlix26.case_study_bmc.data.BMCContract.VisitorEntry;
import com.assignment.binlix26.case_study_bmc.model.Appointment;
import com.assignment.binlix26.case_study_bmc.model.Staff;
import com.assignment.binlix26.case_study_bmc.model.Visitor;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.assignment.binlix26.case_study_bmc.utility.Utility.purposeList;

public class EditAppointmentActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,
        TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private static final int LOADER_VISITOR = 1;
    private static final int LOADER_STAFF = 2;

    private Staff staff;
    private Visitor visitor;

    private TextView tvDate;
    private TextView tvTime;

    private Spinner spStaff;
    private Spinner spVisitor;
    private Spinner spDesc;

    private List<String> staffList = new ArrayList<>();
    private List<String> visitorList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_appointment);

        getLoaderManager().initLoader(LOADER_VISITOR, null, this);
        getLoaderManager().initLoader(LOADER_STAFF, null, this);

        // texViews
        tvDate = (TextView) findViewById(R.id.edit_app_date);
        tvTime = (TextView) findViewById(R.id.edit_app_time);

        //spinners
        spStaff = (Spinner) findViewById(R.id.edit_app_sp_staff);
        spVisitor = (Spinner) findViewById(R.id.edit_app_sp_visitor);
        spDesc = (Spinner) findViewById(R.id.edit_app_sp_desc);

        // buttons
        Button btUpdate = (Button) findViewById(R.id.edit_app_bt_update);
        Button btPickDate = (Button) findViewById(R.id.edit_app_bt_date);
        Button btPickTime = (Button) findViewById(R.id.edit_app_bt_time);
        Button backButton = (Button) findViewById(R.id.backButton);

        //set up date and time picker
        btPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        EditAppointmentActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setTitle("Date Picker Dialog");
                dpd.show(getFragmentManager(), "DatePicker");
            }
        });

        btPickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        EditAppointmentActivity.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        true // true =  24 hour enable, false = 12 hour enable
                );
                tpd.setTitle("Time Picker Dialog");
                tpd.show(getFragmentManager(), "TimePicker");
            }
        });

        //Button to return to Administration
        backButton.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                       backToAdminActivity();
                    }
                }
        );




        // set up spinner description
        ArrayAdapter<String> adapterDesc = new ArrayAdapter<>(EditAppointmentActivity.this,
                android.R.layout.simple_spinner_item, purposeList);
        adapterDesc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDesc.setAdapter(adapterDesc);

        boolean exist = getIntent().getExtras().getBoolean("exist");

        if (exist) {
            final Appointment app = (Appointment) getIntent().getExtras().get("appointment");
            visitor = (Visitor) getIntent().getExtras().get("visitor");
            staff = (Staff) getIntent().getExtras().get("staff");

            // set purpose spinner position
            for (int indexOfPurpose = 0; indexOfPurpose < purposeList.size(); indexOfPurpose++) {
                if (purposeList.get(indexOfPurpose).equals(app.getDescription())) {
                    spDesc.setSelection(indexOfPurpose);
                    break;
                }
            }

            // get date and time from appointment object
            String[] dateTime = app.getTime().split(" ");
            tvDate.setText(dateTime[0]);
            tvTime.setText(dateTime[1]);

            btUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateApp(app.getId());
                }
            });

        } else {
            btUpdate.setText("Add");

            btUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    insertApp();
                }
            });
        }
    }

    private void insertApp() {

        String staff = spStaff.getSelectedItem().toString().split("-")[0];
        String visitor = spVisitor.getSelectedItem().toString().split("-")[0];
        String desc = spDesc.getSelectedItem().toString();
        String dateTime = tvDate.getText().toString() + " " + tvTime.getText().toString();

        ContentValues values = new ContentValues();
        values.put(AppointmentEntry.COLUMN_DESCRIPTION, desc);
        values.put(AppointmentEntry.COLUMN_DATETIME, dateTime);
        values.put(AppointmentEntry.COLUMN_VISITOR, visitor);
        values.put(AppointmentEntry.COLUMN_STAFF, staff);

        getContentResolver().insert(AppointmentEntry.CONTENT_URI, values);

        Intent admin = new Intent(this, AdminActivity.class);
        startActivity(admin);

    }

    private void updateApp(int id) {
        String selection = AppointmentEntry._ID + "=?";
        String[] selectionArgs = new String[]{
                String.valueOf(id)
        };

        String visitorId = spVisitor.getSelectedItem().toString().split("-")[0];
        String staffId = spStaff.getSelectedItem().toString().split("-")[0];
        String description = spDesc.getSelectedItem().toString();
        String dateTime = tvDate.getText().toString() + " " + tvTime.getText().toString();

        ContentValues values = new ContentValues();
        values.put(AppointmentEntry.COLUMN_DESCRIPTION, description);
        values.put(AppointmentEntry.COLUMN_DATETIME, dateTime);
        values.put(AppointmentEntry.COLUMN_VISITOR, visitorId);
        values.put(AppointmentEntry.COLUMN_STAFF, staffId);

        getContentResolver().update(AppointmentEntry.CONTENT_URI, values, selection, selectionArgs);

        Intent admin = new Intent(this, AdminActivity.class);
        startActivity(admin);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String month;
        if (monthOfYear < 9)
            month = "0" + (monthOfYear + 1);
        else
            month = (monthOfYear + 1) + "";

        String day;
        if (dayOfMonth < 10)
            day = "0" + dayOfMonth;
        else
            day = dayOfMonth + "";

        String date = year + "-" + month + "-" + day;
        tvDate.setText(date);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String hour;
        String sMinute;

        if (hourOfDay < 10)
            hour = "0" + hourOfDay;
        else
            hour = "" + hourOfDay;

        if (minute < 10)
            sMinute = "0" + minute;
        else
            sMinute = "" + minute;

        String time = hour + ":" + sMinute + ":" + "00";
        tvTime.setText(time);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_VISITOR:
                CursorLoader visitorLoader = new CursorLoader(
                        this,
                        VisitorEntry.CONTENT_URI,
                        null, null, null, null
                );

                return visitorLoader;

            case LOADER_STAFF:
                CursorLoader staffLoader = new CursorLoader(
                        this,
                        StaffEntry.CONTENT_URI,
                        null, null, null, null
                );

                return staffLoader;

            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case LOADER_VISITOR:

                while (data.moveToNext()) {
                    int id = data.getInt(data.getColumnIndexOrThrow(VisitorEntry._ID));
                    String name = data.getString(data.getColumnIndexOrThrow(VisitorEntry.COLUMN_NAME));
                    String business = data.getString(data.getColumnIndexOrThrow(VisitorEntry.COLUMN_BUSINESS_NAME));
                    visitorList.add(id + "-" + name + "-" + business);
                }

                ArrayAdapter<String> adapterVisitor = new ArrayAdapter<>(EditAppointmentActivity.this,
                        android.R.layout.simple_spinner_item, visitorList);
                adapterVisitor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spVisitor.setAdapter(adapterVisitor);

                if (visitor != null) {
                    for (int indexOfStaff = 0; indexOfStaff < visitorList.size(); indexOfStaff++) {
                        String staffId = visitorList.get(indexOfStaff).split("-")[0];
                        if (staffId.equals(String.valueOf(visitor.getId()))) {
                            spVisitor.setSelection(indexOfStaff);
                            break;
                        }
                    }

                }

                break;
            case LOADER_STAFF:

                while (data.moveToNext()) {
                    int id = data.getInt(data.getColumnIndexOrThrow(StaffEntry._ID));
                    String name = data.getString(data.getColumnIndexOrThrow(StaffEntry.COLUMN_NAME));
                    String title = data.getString(data.getColumnIndexOrThrow(StaffEntry.COLUMN_TITLE));
                    staffList.add(id + "-" + name + "-" + title);
                }

                ArrayAdapter<String> adapterStaff = new ArrayAdapter<String>(EditAppointmentActivity.this,
                        android.R.layout.simple_spinner_item, staffList);
                adapterStaff.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spStaff.setAdapter(adapterStaff);

                if (staff != null) {
                    for (int indexOfStaff = 0; indexOfStaff < staffList.size(); indexOfStaff++) {
                        String staffId = staffList.get(indexOfStaff).split("-")[0];
                        if (staffId.equals(String.valueOf(staff.getId()))) {
                            spStaff.setSelection(indexOfStaff);
                            break;
                        }
                    }
                }

                break;
            default:
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // do nothing
    }

    public void backToAdminActivity() {
        Intent i = new Intent (this, AdminActivity.class);
        startActivity(i);
    }

}
