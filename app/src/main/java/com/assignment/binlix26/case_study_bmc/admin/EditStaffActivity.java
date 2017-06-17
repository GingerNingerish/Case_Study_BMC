package com.assignment.binlix26.case_study_bmc.admin;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.assignment.binlix26.case_study_bmc.AdminActivity;
import com.assignment.binlix26.case_study_bmc.MainActivity;
import com.assignment.binlix26.case_study_bmc.R;
import com.assignment.binlix26.case_study_bmc.data.BMCContract.StaffEntry;
import com.assignment.binlix26.case_study_bmc.home.VisitorCheckInActivity;
import com.assignment.binlix26.case_study_bmc.model.Staff;
import com.assignment.binlix26.case_study_bmc.utility.Utility;

import static com.assignment.binlix26.case_study_bmc.R.drawable.staff;

public class EditStaffActivity extends AppCompatActivity {

    private ImageView ivPhoto;
    private EditText etName;
    private EditText etTitle;
    private EditText etDepartment;
    private EditText etPhone;
    private Button btUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_staff);

        ivPhoto = (ImageView) findViewById(R.id.edit_staff_photo);
        etName = (EditText) findViewById(R.id.edit_staff_name);
        etTitle = (EditText) findViewById(R.id.edit_staff_title);
        etDepartment = (EditText) findViewById(R.id.edit_staff_department);
        etPhone = (EditText) findViewById(R.id.edit_staff_phone);
        btUpdate = (Button) findViewById(R.id.edit_staff_bt_update);

        boolean exit = getIntent().getExtras().getBoolean("exist");

        if (exit) {
            final Staff staff = (Staff) getIntent().getExtras().get("staff");
            ivPhoto.setImageResource(Integer.parseInt(staff.getPhoto()));
            etName.setText(staff.getName());
            etTitle.setText(staff.getTitle());
            etDepartment.setText(staff.getDepartment());
            etPhone.setText(staff.getPhone());

            btUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateStaff(staff.getId());
                }
            });
        } else {
            ivPhoto.setImageResource(R.drawable.staff);
            btUpdate.setText("Add");
            btUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (checkInput()) {
                        insertStaff();
                    }
                }
            });
        }
    }

    private boolean checkInput() {
        String name = etName.getText().toString();
        String title = etTitle.getText().toString();
        String department = etDepartment.getText().toString();
        String phone = etPhone.getText().toString();

        if (name.equals("") || title.equals("")
                || department.equals("") || phone.equals("")) {
            new AlertDialog.Builder(this)
                    .setTitle("Field Required")
                    .setMessage("Please enter each field!")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes,null)
                    .show();

            return false;
        }

        return true;
    }

    private void updateStaff(int id) {
        String selection = StaffEntry._ID + "=?";
        String[] selectionArgs = new String[]{
                String.valueOf(id)
        };

        String name = etName.getText().toString().trim();
        String title = etTitle.getText().toString().trim();
        String department = etDepartment.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        ContentValues values = new ContentValues();

        values.put(StaffEntry.COLUMN_NAME, name);
        values.put(StaffEntry.COLUMN_TITLE, title);
        values.put(StaffEntry.COLUMN_DEPARTMENT, department);
        values.put(StaffEntry.COLUMN_PHONE, phone);

        getContentResolver().update(StaffEntry.CONTENT_URI, values, selection, selectionArgs);

        Intent admin = new Intent(this, AdminActivity.class);
        startActivity(admin);
    }
    private void insertStaff() {
        String name = etName.getText().toString();
        String title = etTitle.getText().toString();
        String department = etDepartment.getText().toString();
        String phone = etPhone.getText().toString();

        ContentValues values = new ContentValues();
        values.put(StaffEntry.COLUMN_NAME, name);
        values.put(StaffEntry.COLUMN_TITLE, title);
        values.put(StaffEntry.COLUMN_DEPARTMENT, department);
        values.put(StaffEntry.COLUMN_PHONE, phone);
        values.put(StaffEntry.COLUMN_PHOTO, R.drawable.staff);

        getContentResolver().insert(StaffEntry.CONTENT_URI,values);

        Intent admin = new Intent(this, AdminActivity.class);
        startActivity(admin);
    }
}
