package com.assignment.binlix26.case_study_bmc.admin;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.assignment.binlix26.case_study_bmc.R;
import com.assignment.binlix26.case_study_bmc.data.BMCContract.*;
import com.assignment.binlix26.case_study_bmc.model.Appointment;
import com.assignment.binlix26.case_study_bmc.model.Staff;
import com.assignment.binlix26.case_study_bmc.model.Visitor;

public class AppointmentFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int APP_LOADER = 101;

    ListView listView;
    Cursor cursor;
    AppointmentCursorAdapter adapter;


    // This event fires 2nd, before views are created for the fragment
    // The onCreate method is called when the Fragment instance is being created, or re-created.
    // Use onCreate for any standard setup that does not require the activity to be fully created
    // loading data happens hrer
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: 13/06/17 check option menu boolean
        getLoaderManager().initLoader(APP_LOADER, null, this);
    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_appointment_list, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);

        listView = (ListView) view.findViewById(R.id.app_list);
        adapter = new AppointmentCursorAdapter(getContext(), cursor);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //move the cursor to the selected row
                cursor = (Cursor) parent.getItemAtPosition(position);

                // get data from this cursor
                int appID = cursor.getInt(cursor.getColumnIndex(AppointmentEntry._ID));
                String appTime = cursor.getString(cursor.getColumnIndex(AppointmentEntry.COLUMN_DATETIME));
                String appDesc = cursor.getString(cursor.getColumnIndex(AppointmentEntry.COLUMN_DESCRIPTION));

                Appointment app = new Appointment(appID, appDesc, appTime);

                int visitorID = cursor.getInt(cursor.getColumnIndex(AppointmentEntry.COLUMN_VISITOR));
                String visitorName = cursor.getString(cursor.getColumnIndex(VisitorEntry.COLUMN_NAME));
                String visitorBusiness = cursor.getString(cursor.getColumnIndex(VisitorEntry.COLUMN_BUSINESS_NAME));
                Visitor visitor = new Visitor(visitorID, visitorName, visitorBusiness);

                int staffID = cursor.getInt(cursor.getColumnIndex(AppointmentEntry.COLUMN_STAFF));
                String staffName = cursor.getString(cursor.getColumnIndex(StaffEntry.COLUMN_NAME));
                String staffTittle = cursor.getString(cursor.getColumnIndex(StaffEntry.COLUMN_TITLE));
                Staff staff = new Staff(staffID, staffName, staffTittle);

                Intent appDetail = new Intent(getActivity(), EditAppointmentActivity.class);
                appDetail.putExtra("exist", true);
                appDetail.putExtra("appointment", app);
                appDetail.putExtra("visitor", visitor);
                appDetail.putExtra("staff", staff);

                startActivity(appDetail);


               /*
               String staffName = cursor.getString(cursor.getColumnIndex(StaffEntry.COLUMN_NAME));
                String staffPhone = cursor.getString(cursor.getColumnIndex(StaffEntry.COLUMN_PHONE));
                String visitorPhone = cursor.getString(cursor.getColumnIndex(VisitorEntry.COLUMN_PHONE));
                String staffDepartment = cursor.getString(cursor.getColumnIndex(StaffEntry.COLUMN_DEPARTMENT));*/
            }
        });

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // TODO: 13/06/17 filer result at here for future needs

        // Construct the loader
        Loader<Cursor> cursorLoader = new CursorLoader(
                getActivity(),
                AppointmentEntry.CONTENT_URI,
                null, null, null, null
        );

        return cursorLoader;
    }

    // When the system finishes retrieving the Cursor through the CursorLoader,
    // a call to the onLoadFinished() method takes place.
    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    // This method is triggered when the loader is being reset
    // and the loader data is no longer available. Called if the data
    // in the provider changes and the Cursor becomes stale.
    @Override
    public void onLoaderReset(android.support.v4.content.Loader loader) {
        adapter.swapCursor(null);
    }

}
