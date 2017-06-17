package com.assignment.binlix26.case_study_bmc.admin;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.assignment.binlix26.case_study_bmc.AdminActivity;
import com.assignment.binlix26.case_study_bmc.R;
import com.assignment.binlix26.case_study_bmc.data.BMCContract.VisitorEntry;

public class EditVisitorActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etBusiness;
    private EditText etPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_visitor);

        etName = (EditText) findViewById(R.id.edit_visitor_name);
        etBusiness = (EditText) findViewById(R.id.edit_visitor_business);
        etPhone = (EditText) findViewById(R.id.edit_visitor_phone);

        Button btAddVisitor = (Button) findViewById(R.id.edit_visitor_bt_add);

        btAddVisitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInput()) {
                    insertVisitor();
                }
            }
        });
    }

    private void insertVisitor() {
        String name = etName.getText().toString();
        String business = etBusiness.getText().toString();
        String phone = etPhone.getText().toString();

        ContentValues values = new ContentValues();
        values.put(VisitorEntry.COLUMN_NAME, name);
        values.put(VisitorEntry.COLUMN_BUSINESS_NAME, business);
        values.put(VisitorEntry.COLUMN_PHONE, phone);

        getContentResolver().insert(VisitorEntry.CONTENT_URI, values);

        Intent admin = new Intent(this, AdminActivity.class);
        startActivity(admin);
    }

    private boolean checkInput() {
        String name = etName.getText().toString();
        String title = etBusiness.getText().toString();
        String phone = etPhone.getText().toString();

        if (name.equals("") || title.equals("")
                || phone.equals("")) {
            new AlertDialog.Builder(this)
                    .setTitle("Field Required")
                    .setMessage("Please enter each field!")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, null)
                    .show();

            return false;
        }

        return true;
    }
}
