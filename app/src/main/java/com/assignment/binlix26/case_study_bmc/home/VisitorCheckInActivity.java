package com.assignment.binlix26.case_study_bmc.home;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.assignment.binlix26.case_study_bmc.AdminActivity;
import com.assignment.binlix26.case_study_bmc.MainActivity;
import com.assignment.binlix26.case_study_bmc.R;
import com.assignment.binlix26.case_study_bmc.data.BMCContract;
import com.assignment.binlix26.case_study_bmc.model.Visitor;
import com.assignment.binlix26.case_study_bmc.utility.Utility;

import static com.assignment.binlix26.case_study_bmc.data.BMCContract.CHECKIN;
import static com.assignment.binlix26.case_study_bmc.data.BMCContract.CHECKOUT;
import static com.assignment.binlix26.case_study_bmc.data.BMCContract.VisitorEntry;
import static com.assignment.binlix26.case_study_bmc.utility.Utility.purposeList;

public class VisitorCheckInActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etBusiness;
    private EditText etPhone;
    private Button btSign;
    private Spinner spPurpose;

    private boolean fromVisitor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor_checkin);

        etName = (EditText) findViewById(R.id.sign_in_visitor_name);
        etBusiness = (EditText) findViewById(R.id.sign_in_visitor_business);
        etPhone = (EditText) findViewById(R.id.sign_in_visitor_phone);
        btSign = (Button) findViewById(R.id.sign_in_visitor_bt);

        // set up the drop down for purpose
        spPurpose = (Spinner) findViewById(R.id.sign_in_purpose_spi);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(VisitorCheckInActivity.this,
                android.R.layout.simple_spinner_item, purposeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPurpose.setAdapter(adapter);

        final Visitor visitor = (Visitor) getIntent().getExtras().getSerializable("visitor");
        boolean exist = getIntent().getExtras().getBoolean("exist");
        fromVisitor = getIntent().getExtras().getBoolean("fromVisitor");

        Integer checkStatus = visitor.getStatus();

        if (exist) {

            populateInfo(visitor);

            // check if is sign in or sign out
            if (checkStatus.equals(BMCContract.CHECKOUT)) {
                btSign.setText("Check In");
                btSign.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkIn(visitor.getId());
                    }
                });

            } else {

                etName.setEnabled(false);
                etBusiness.setEnabled(false);
                etPhone.setEnabled(false);
                spPurpose.setEnabled(false);

                btSign.setText("Check Out");

                btSign.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkOut(visitor.getId());
                    }
                });
            }

        } else {

            final String phone = visitor.getPhone();

            etPhone.setText(phone);

            btSign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = etName.getText().toString().trim();
                    String business = etBusiness.getText().toString().trim();
                    String purpose = spPurpose.getSelectedItem().toString();
                    String signInTime = Utility.getCurrentTimeAsString();

                    ContentValues values = new ContentValues();
                    values.put(VisitorEntry.COLUMN_NAME, name);
                    values.put(VisitorEntry.COLUMN_BUSINESS_NAME, business);
                    values.put(VisitorEntry.COLUMN_PURPOSE, purpose);
                    values.put(VisitorEntry.COLUMN_PHONE, phone);
                    values.put(VisitorEntry.COLUMN_STATUS, BMCContract.CHECKIN);
                    values.put(VisitorEntry.COLUMN_SIGN_IN, signInTime);

                    getContentResolver().insert(VisitorEntry.CONTENT_URI, values);

                    Intent homePage = new Intent(VisitorCheckInActivity.this, MainActivity.class);
                    startActivity(homePage);
                }
            });

        }
    }

    private void populateInfo(Visitor visitor) {
        etName.setText(visitor.getName());
        etBusiness.setText(visitor.getBusiness());
        etPhone.setText(visitor.getPhone());

        String purpose = visitor.getPurpose();

        if (purpose != null && purpose.length() > 0) {
            // set purpose spinner position
            for(int indexOfPurpose = 0;indexOfPurpose<purposeList.size();indexOfPurpose++){
                if(purposeList.get(indexOfPurpose).equals(visitor.getPurpose())){
                    spPurpose.setSelection(indexOfPurpose);
                    break;
                }
            }
        }
    }

    private void checkOut(final int visitorID) {
        new AlertDialog.Builder(this)
                .setTitle("Confirm Checkout")
                .setMessage("Check out Confirmation")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                        //update checkout status
                        String selection = VisitorEntry._ID + "=?";
                        String[] arguments = new String[]{
                                String.valueOf(visitorID)
                        };

                        ContentValues values = new ContentValues();
                        values.put(VisitorEntry.COLUMN_STATUS, CHECKOUT);
                        values.put(VisitorEntry.COLUMN_SIGN_OUT, Utility.getCurrentTimeAsString());

                        getContentResolver().update(VisitorEntry.CONTENT_URI, values, selection, arguments);

                        Intent homePage;

                        if (fromVisitor)
                            homePage = new Intent(VisitorCheckInActivity.this, MainActivity.class);
                        else
                            homePage = new Intent(VisitorCheckInActivity.this, AdminActivity.class);
                        startActivity(homePage);
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    private void checkIn(int id) {

        //update checkout status
        String selection = VisitorEntry._ID + "=?";
        String[] arguments = new String[]{
                String.valueOf(id)
        };

        ContentValues values = new ContentValues();
        values.put(VisitorEntry.COLUMN_STATUS, CHECKIN);
        values.put(VisitorEntry.COLUMN_SIGN_IN, Utility.getCurrentTimeAsString());
        values.put(VisitorEntry.COLUMN_PURPOSE, spPurpose.getSelectedItem().toString());

        getContentResolver().update(VisitorEntry.CONTENT_URI, values, selection, arguments);

        Intent homePage;

        if (fromVisitor)
            homePage = new Intent(VisitorCheckInActivity.this, MainActivity.class);
        else
            homePage = new Intent(VisitorCheckInActivity.this, AdminActivity.class);
        startActivity(homePage);
    }
}
