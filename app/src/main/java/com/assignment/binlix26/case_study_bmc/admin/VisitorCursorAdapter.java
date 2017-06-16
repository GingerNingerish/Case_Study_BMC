package com.assignment.binlix26.case_study_bmc.admin;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.assignment.binlix26.case_study_bmc.R;
import com.assignment.binlix26.case_study_bmc.data.BMCContract.VisitorEntry;

/**
 * Created by binlix26 on 13/06/17.
 */

public class VisitorCursorAdapter extends CursorAdapter {
    public VisitorCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(
                R.layout.fragment_visitor, parent, false
        );
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView tvCheckVisitor = (TextView) view.findViewById(R.id.checkin_visitor);
        TextView tvCheckPurpose = (TextView) view.findViewById(R.id.checkin_purpose);
        TextView tvCheckTime = (TextView) view.findViewById(R.id.checkin_time);

        // Extract properties from cursor
        int visitor = cursor.getColumnIndexOrThrow(VisitorEntry.COLUMN_NAME);
        int purpose = cursor.getColumnIndexOrThrow(VisitorEntry.COLUMN_PURPOSE);
        int inTime = cursor.getColumnIndexOrThrow(VisitorEntry.COLUMN_SIGN_IN);


        String _visitor = cursor.getString(visitor);
        String _purpose = cursor.getString(purpose);
        String _inTime = cursor.getString(inTime);

        // Populate fields with extracted properties
        tvCheckVisitor.setText(_visitor);
        tvCheckPurpose.setText(_purpose);
        tvCheckTime.setText(_inTime);
    }
}
