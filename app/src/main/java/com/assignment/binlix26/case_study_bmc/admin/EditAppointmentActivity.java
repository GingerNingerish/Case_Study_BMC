package com.assignment.binlix26.case_study_bmc.admin;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.assignment.binlix26.case_study_bmc.AdminActivity;
import com.assignment.binlix26.case_study_bmc.R;
import com.assignment.binlix26.case_study_bmc.data.BMCContract;
import com.assignment.binlix26.case_study_bmc.data.BMCContract.*;
import com.assignment.binlix26.case_study_bmc.model.Appointment;

import java.util.ArrayList;
import java.util.List;

import static com.assignment.binlix26.case_study_bmc.utility.Utility.purposeList;

public class EditAppointmentActivity extends AppCompatActivity {


    private TextView tvDate;
    private TextView tvTime;

    private Spinner spStaff;
    private Spinner spVisitor;
    private Spinner spDesc;

    private List<String> staffs = new ArrayList<>();
    private List<String> visitors = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_appointment);

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

        ArrayAdapter<String> adapter = new ArrayAdapter<>(EditAppointmentActivity.this,
                android.R.layout.simple_spinner_item, purposeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDesc.setAdapter(adapter);

        boolean exist = getIntent().getExtras().getBoolean("exist");

        if (exist) {
            final Appointment app = (Appointment) getIntent().getExtras().get("appointment");

//        String sName = getIntent().getExtras().getString("staff");
//        String sPhone = getIntent().getExtras().getString("staffPhone");
//
//        String vName = getIntent().getExtras().getString("visitor");
//        String vPhone = getIntent().getExtras().getString("visitorPhone");


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
            insertApp();
        }
    }

    private void insertApp() {

    }

    private void updateApp(int id) {
        String selection = BMCContract.AppointmentEntry._ID + "=?";
        String[] selectionArgs = new String[]{
                String.valueOf(id)
        };

        String description = spDesc.getSelectedItem().toString();

        ContentValues values = new ContentValues();

        values.put(AppointmentEntry.COLUMN_DESCRIPTION, description);

//        getContentResolver().update(AppointmentEntry.CONTENT_URI, values, selection, selectionArgs);

        Intent admin = new Intent(this, AdminActivity.class);
        startActivity(admin);
    }
}
