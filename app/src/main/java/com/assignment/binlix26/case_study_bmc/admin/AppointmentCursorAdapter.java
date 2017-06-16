package com.assignment.binlix26.case_study_bmc.admin;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.assignment.binlix26.case_study_bmc.R;
import com.assignment.binlix26.case_study_bmc.data.BMCContract.AppointmentEntry;
import com.assignment.binlix26.case_study_bmc.data.BMCContract.StaffEntry;
import com.assignment.binlix26.case_study_bmc.data.BMCContract.VisitorEntry;

/**
 * Created by binlix26 on 13/06/17.
 */

public class AppointmentCursorAdapter extends CursorAdapter {

    public AppointmentCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(
                R.layout.fragment_appointment, parent, false
        );
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView tvStaffName = (TextView) view.findViewById(R.id.app_staff);
        TextView tvVisitorName = (TextView) view.findViewById(R.id.app_visitor);
        TextView tvAppTime = (TextView) view.findViewById(R.id.app_time);

        // Extract properties from cursor
        int staffNameCol = cursor.getColumnIndexOrThrow(StaffEntry.COLUMN_NAME);
        int visitorNameCol = cursor.getColumnIndexOrThrow(VisitorEntry.COLUMN_NAME);
        int appTimeCol = cursor.getColumnIndexOrThrow(AppointmentEntry.COLUMN_DATETIME);


        String staffName = cursor.getString(staffNameCol);
        String visitorName = cursor.getString(visitorNameCol);
        String appTime = cursor.getString(appTimeCol);

        // Populate fields with extracted properties
        tvStaffName.setText(staffName);
        tvVisitorName.setText(visitorName);
        tvAppTime.setText(appTime);

    }
}
