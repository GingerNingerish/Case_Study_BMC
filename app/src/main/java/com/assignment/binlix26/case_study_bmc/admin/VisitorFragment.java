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
import com.assignment.binlix26.case_study_bmc.data.BMCContract.VisitorEntry;
import com.assignment.binlix26.case_study_bmc.home.VisitorCheckInActivity;
import com.assignment.binlix26.case_study_bmc.model.Visitor;

public class VisitorFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int CHECK_IN_LOADER = 101;

    ListView listView;
    Cursor cursor;
    VisitorCursorAdapter adapter;

    public VisitorFragment() {
        // Required empty public constructor
    }

    public static VisitorFragment newInstance() {
        VisitorFragment fragment = new VisitorFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLoaderManager().initLoader(CHECK_IN_LOADER, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_visitor_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        listView = (ListView) view.findViewById(R.id.checkin_list);
        adapter = new VisitorCursorAdapter(getContext(), cursor);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //move the cursor to the selected row
                cursor = (Cursor) parent.getItemAtPosition(position);

                // get data from this cursor

                int visitorID = cursor.getInt(cursor.getColumnIndex(VisitorEntry._ID));
                String visitorName = cursor.getString(cursor.getColumnIndex(VisitorEntry.COLUMN_NAME));
                String visitorPhone = cursor.getString(cursor.getColumnIndex(VisitorEntry.COLUMN_PHONE));
                String visitorBusiness = cursor.getString(cursor.getColumnIndex(VisitorEntry.COLUMN_BUSINESS_NAME));
                String visitorPurpose = cursor.getString(cursor.getColumnIndex(VisitorEntry.COLUMN_PURPOSE));
                int visitorStatus = cursor.getInt(cursor.getColumnIndex(VisitorEntry.COLUMN_STATUS));

                Visitor visitor = new Visitor(visitorID, visitorName,
                        visitorBusiness, visitorPhone, visitorPurpose, visitorStatus);

                Intent selectedVisitor = new Intent(getActivity(), VisitorCheckInActivity.class);

                selectedVisitor.putExtra("visitor", visitor);
                selectedVisitor.putExtra("exist", true);
                startActivity(selectedVisitor);
            }
        });

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // TODO: 13/06/17 filer result at here for future needs

        // Construct the loader
        Loader<Cursor> cursorLoader = new CursorLoader(
                getActivity(),
                VisitorEntry.CONTENT_URI,
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
