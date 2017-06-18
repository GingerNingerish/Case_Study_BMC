package com.assignment.binlix26.case_study_bmc.admin;

import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.assignment.binlix26.case_study_bmc.R;
import com.assignment.binlix26.case_study_bmc.data.BMCContract.StaffEntry;
import com.assignment.binlix26.case_study_bmc.utility.Utility;

/**
 * Created by binlix26 on 13/06/17.
 */

public class StaffCursorAdapter extends CursorAdapter {
    public StaffCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(
                R.layout.fragment_staff, parent, false
        );
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        ImageView ivPhoto = (ImageView) view.findViewById(R.id.staff_photo);
        TextView tvStaffName = (TextView) view.findViewById(R.id.staff_name);
        TextView tvStaffTitle = (TextView) view.findViewById(R.id.staff_title);
        TextView tvStaffDe = (TextView) view.findViewById(R.id.staff_department);

        // Extract properties from cursor
        int photo = cursor.getColumnIndexOrThrow(StaffEntry.COLUMN_PHOTO);
        int staff = cursor.getColumnIndexOrThrow(StaffEntry.COLUMN_NAME);
        int title = cursor.getColumnIndexOrThrow(StaffEntry.COLUMN_TITLE);
        int department = cursor.getColumnIndexOrThrow(StaffEntry.COLUMN_DEPARTMENT);

        byte[] _photo = cursor.getBlob(photo);
        String _staff = cursor.getString(staff);
        String _title = cursor.getString(title);
        String _department = cursor.getString(department);

        // set up default staff avatar
        if (_photo ==null || _photo.length < 1) {
            _photo = Utility.getImageBytes(Utility.bitmap);
        }

        // Populate fields with extracted properties
        ivPhoto.setImageBitmap(Utility.toBitmapImage(_photo));

//        Picasso.with(context)
//                .load(new File(_phtoto))
//                .error(R.drawable.staff)
//                .resize(60, 60)
//                .centerCrop()
//                .into(ivPhoto);

        tvStaffName.setText(_staff);
        tvStaffTitle.setText(_title);
        tvStaffDe.setText(_department);
    }
}
