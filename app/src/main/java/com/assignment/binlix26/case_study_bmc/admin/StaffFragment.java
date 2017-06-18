package com.assignment.binlix26.case_study_bmc.admin;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.assignment.binlix26.case_study_bmc.R;
import com.assignment.binlix26.case_study_bmc.data.BMCContract.StaffEntry;
import com.assignment.binlix26.case_study_bmc.model.Staff;


public class StaffFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int STAFF_LOADER = 101;

    ListView listView;
    Cursor cursor;
    StaffCursorAdapter adapter;

    public StaffFragment() {
        // Required empty public constructor
    }

    public static StaffFragment newInstance() {
        StaffFragment fragment = new StaffFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLoaderManager().initLoader(STAFF_LOADER, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_staff_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        listView = (ListView) view;
        adapter = new StaffCursorAdapter(getContext(), cursor);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //move the cursor to the selected row
                cursor = (Cursor) parent.getItemAtPosition(position);

                // get data from this cursor
                int staffId = cursor.getInt(cursor.getColumnIndex(StaffEntry._ID));
                String staffName = cursor.getString(cursor.getColumnIndex(StaffEntry.COLUMN_NAME));
                String staffPhone = cursor.getString(cursor.getColumnIndex(StaffEntry.COLUMN_PHONE));
                String staffTittle = cursor.getString(cursor.getColumnIndex(StaffEntry.COLUMN_TITLE));
                String staffDepartment = cursor.getString(cursor.getColumnIndex(StaffEntry.COLUMN_DEPARTMENT));
                byte[] staffPhoto = cursor.getBlob(cursor.getColumnIndex(StaffEntry.COLUMN_PHOTO));

                Staff staff = new Staff(staffId, staffName,
                        staffTittle, staffDepartment, staffPhone, staffPhoto);

                Intent editStaff = new Intent(getActivity(), EditStaffActivity.class);

                editStaff.putExtra("staff", staff);
                editStaff.putExtra("exist", true);
                startActivity(editStaff);
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        //TODO: 13/06/17 filer result at here for future needs

        // Construct the loader
        Loader<Cursor> cursorLoader = new CursorLoader(
                getActivity(),
                StaffEntry.CONTENT_URI,
                null, null, null, null
        );

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
