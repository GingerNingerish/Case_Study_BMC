package com.assignment.binlix26.case_study_bmc.admin;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.assignment.binlix26.case_study_bmc.AdminActivity;
import com.assignment.binlix26.case_study_bmc.R;
import com.assignment.binlix26.case_study_bmc.data.BMCContract;
import com.assignment.binlix26.case_study_bmc.data.BMCContract.*;
import com.assignment.binlix26.case_study_bmc.model.Appointment;

import static android.R.attr.name;

public class EditAppointmentActivity extends AppCompatActivity {

    private EditText etStaffName;
    private EditText etVisitorName;
    private EditText etAppDesc;
    private EditText etAppTime;
    private TextView tvStaffPhone;
    private TextView tvVisitorPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_appointment);

        etStaffName = (EditText) findViewById(R.id.edit_app_staff_name);
        etVisitorName = (EditText) findViewById(R.id.edit_app_visitor_name);
        tvStaffPhone = (TextView) findViewById(R.id.edit_app_staff_phone);
        tvVisitorPhone = (TextView) findViewById(R.id.edit_app_visitor_phone);
        etAppDesc = (EditText) findViewById(R.id.edit_app_desc);
        etAppTime = (EditText) findViewById(R.id.edit_app_time);

        final Appointment app = (Appointment) getIntent().getExtras().get("appointment");
        String sName = getIntent().getExtras().getString("staff");
        String sPhone = getIntent().getExtras().getString("staffPhone");

        String vName = getIntent().getExtras().getString("visitor");
        String vPhone = getIntent().getExtras().getString("visitorPhone");

        etStaffName.setText(sName);
        etVisitorName.setText(vName);

        tvStaffPhone.setText(sPhone);
        tvVisitorPhone.setText(vPhone);

        etAppDesc.setText(app.getDescription());
        etAppTime.setText(app.getTime());

        Button btUpdate = (Button) findViewById(R.id.edit_app_bt_update);
        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateApp(app.getId());
            }
        });
    }

    private void updateApp(int id) {
        String selection = BMCContract.AppointmentEntry._ID + "=?";
        String[] selectionArgs = new String[]{
                String.valueOf(id)
        };


        String description = etAppDesc.getText().toString().trim();
        String time = etAppTime.getText().toString().trim();

        ContentValues values = new ContentValues();


        values.put(AppointmentEntry.COLUMN_DESCRIPTION, description);
        values.put(AppointmentEntry.COLUMN_DATETIME, time);

//        getContentResolver().update(AppointmentEntry.CONTENT_URI, values, selection, selectionArgs);

        Intent admin = new Intent(this, AdminActivity.class);
        startActivity(admin);
    }
}
