package com.assignment.binlix26.case_study_bmc.admin;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static android.R.attr.button;
import static com.assignment.binlix26.case_study_bmc.R.drawable.staff;

public class EditStaffActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private ImageView ivPhoto;
    private EditText etName;
    private EditText etTitle;
    private EditText etDepartment;
    private EditText etPhone;
    private Button btUpdate;
    private Bitmap staffAavatar;

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

        // set up select image
        Button btPhoto = (Button) findViewById(R.id.bt_take_photo);
        btPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                // Show only images, no videos or anything else
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                // Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_IMAGE_CAPTURE);
            }
        });

        // check where it came from
        boolean exit = getIntent().getExtras().getBoolean("exist");

        if (exit) {
            final Staff staff = (Staff) getIntent().getExtras().get("staff");

            byte[] checkPhoto = staff.getPhoto();

            // only for testing purpose
            // when the DatabaseHelper init() data, photo can not be added.
            // so this 'if' is only for the 3 pre-inserted records
            if (checkPhoto == null || checkPhoto.length < 1) {
                checkPhoto = Utility.getImageBytes(Utility.bitmap);
            }

            staffAavatar = Utility.toBitmapImage(checkPhoto);

            ivPhoto.setImageBitmap(staffAavatar);
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
            // set default image for the staff
            staffAavatar = BitmapFactory.decodeResource(getResources(),
                    R.drawable.staff);
            ivPhoto.setImageBitmap(staffAavatar);

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
                    .setPositiveButton(android.R.string.yes, null)
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

        // in order to save photo to SQLite
        byte[] img = Utility.getImageBytes(staffAavatar);

        ContentValues values = new ContentValues();

        values.put(StaffEntry.COLUMN_NAME, name);
        values.put(StaffEntry.COLUMN_TITLE, title);
        values.put(StaffEntry.COLUMN_DEPARTMENT, department);
        values.put(StaffEntry.COLUMN_PHONE, phone);
        values.put(StaffEntry.COLUMN_PHOTO, img);

        getContentResolver().update(StaffEntry.CONTENT_URI, values, selection, selectionArgs);

        Intent admin = new Intent(this, AdminActivity.class);
        startActivity(admin);
    }

    private void insertStaff() {
        String name = etName.getText().toString();
        String title = etTitle.getText().toString();
        String department = etDepartment.getText().toString();
        String phone = etPhone.getText().toString();

        // in order to save photo to SQLite
        byte[] img = Utility.getImageBytes(staffAavatar);

        ContentValues values = new ContentValues();
        values.put(StaffEntry.COLUMN_NAME, name);
        values.put(StaffEntry.COLUMN_TITLE, title);
        values.put(StaffEntry.COLUMN_DEPARTMENT, department);
        values.put(StaffEntry.COLUMN_PHONE, phone);
        values.put(StaffEntry.COLUMN_PHOTO, img);

        getContentResolver().insert(StaffEntry.CONTENT_URI, values);

        Intent admin = new Intent(this, AdminActivity.class);
        startActivity(admin);
    }

    // If you want to return the image taken, by the trigger above
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                staffAavatar = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(staffAavatar));

                ivPhoto.setImageBitmap(staffAavatar);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
