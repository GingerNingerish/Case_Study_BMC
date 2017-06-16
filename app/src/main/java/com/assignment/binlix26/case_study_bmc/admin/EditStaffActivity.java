package com.assignment.binlix26.case_study_bmc.admin;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.assignment.binlix26.case_study_bmc.AdminActivity;
import com.assignment.binlix26.case_study_bmc.R;
import com.assignment.binlix26.case_study_bmc.data.BMCContract.StaffEntry;
import com.assignment.binlix26.case_study_bmc.model.Staff;

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
}
