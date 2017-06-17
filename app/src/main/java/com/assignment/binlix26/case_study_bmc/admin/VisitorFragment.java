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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.assignment.binlix26.case_study_bmc.R;
import com.assignment.binlix26.case_study_bmc.data.BMCContract;
import com.assignment.binlix26.case_study_bmc.data.BMCContract.VisitorEntry;
import com.assignment.binlix26.case_study_bmc.home.VisitorCheckInActivity;
import com.assignment.binlix26.case_study_bmc.model.Visitor;

import static com.assignment.binlix26.case_study_bmc.utility.Utility.visitorListFilter;

public class VisitorFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int ALL_LOADER = 101;
    private static final int CHECK_IN_LOADER = 102;
    private static final int CHECK_OUT_LOADER = 103;

    private Spinner listFilter;
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

        getLoaderManager().initLoader(ALL_LOADER, null, this);
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
                selectedVisitor.putExtra("fromVisitor", false); // help to fix automatic log out when admin checks visitor in and out
                startActivity(selectedVisitor);
            }
        });

        listFilter = (Spinner) view.findViewById(R.id.checkin_list_filter);

        ArrayAdapter<String> filterAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, visitorListFilter);
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listFilter.setAdapter(filterAdapter);

        listFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getLoader(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void getLoader(int position) {
        switch (position) {
            case 0:
                getLoaderManager().initLoader(ALL_LOADER, null, this);
                break;
            case 1:
                getLoaderManager().initLoader(CHECK_IN_LOADER, null, this);
                break;
            case 2:
                getLoaderManager().initLoader(CHECK_OUT_LOADER, null, this);
                break;
            default:
                break;
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String selection;
        String[] selectionArgs;
        switch (id) {
            case ALL_LOADER:
                // Construct the loader
                Loader<Cursor> allCursor = new CursorLoader(
                        getActivity(),
                        VisitorEntry.CONTENT_URI,
                        null, null, null, null
                );

                return allCursor;
            case CHECK_IN_LOADER:
                selection = VisitorEntry.COLUMN_STATUS + "=?";
                selectionArgs = new String[]{String.valueOf(BMCContract.CHECKIN)};

                // Construct the loader
                Loader<Cursor> checkInCursor = new CursorLoader(
                        getActivity(),
                        VisitorEntry.CONTENT_URI,
                        null, selection, selectionArgs, null
                );

                return checkInCursor;
            case CHECK_OUT_LOADER:
                selection = VisitorEntry.COLUMN_STATUS + "=?";
                selectionArgs = new String[]{String.valueOf(BMCContract.CHECKOUT)};

                // Construct the loader
                Loader<Cursor> checkOutCursor = new CursorLoader(
                        getActivity(),
                        VisitorEntry.CONTENT_URI,
                        null, selection, selectionArgs, null
                );

                return checkOutCursor;
            default:
                return null;
        }

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
