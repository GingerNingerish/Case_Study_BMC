package com.assignment.binlix26.case_study_bmc.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.assignment.binlix26.case_study_bmc.R;
import com.assignment.binlix26.case_study_bmc.data.BMCContract.VisitorEntry;
import com.assignment.binlix26.case_study_bmc.model.Visitor;


/**
 * Created by binlix26 on 15/06/17.
 */

public class VisitorSigningFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    EditText visitorNumberInput;
    Button visitorNumberSearchButton;

    private boolean firstTimeLoad = true;
    private String inputMobile;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in_visitor, container, false);

        visitorNumberInput = (EditText) view.findViewById(R.id.visitorNumberInput);
        visitorNumberSearchButton = (Button) view.findViewById(R.id.visitorNumberSearchButton);

        visitorNumberSearchButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        visitorSearchNumber();
                    }
                }
        );


        return view;
    }

    public void visitorSearchNumber() {

        inputMobile = visitorNumberInput.getText().toString().trim();

        if (inputMobile.length() == 0) {
            //alert input mobile is null
            new AlertDialog.Builder(getContext())
                    .setTitle("ERROR")
                    .setMessage("Please input the Mobile")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, null)
                    .show();
            return;
        }

        if (firstTimeLoad) {
            // load search cell phone number
            getLoaderManager().initLoader(1, null, this);
            firstTimeLoad = false;
        } else {
            getLoaderManager().restartLoader(1, null, this);
        }

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String selection = VisitorEntry.COLUMN_PHONE + "=?";
        String[] selectionArg = new String[]{inputMobile};

        return new CursorLoader(
                getActivity(),
                VisitorEntry.CONTENT_URI,
                null, selection, selectionArg, null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if (data != null && data.getCount() > 0) {
            data.moveToFirst();

            int visitorID = data.getInt(data.getColumnIndex(VisitorEntry._ID));
            String visitorName = data.getString(data.getColumnIndex(VisitorEntry.COLUMN_NAME));
            String visitorPhone = data.getString(data.getColumnIndex(VisitorEntry.COLUMN_PHONE));
            String visitorBusiness = data.getString(data.getColumnIndex(VisitorEntry.COLUMN_BUSINESS_NAME));
            String purpose = data.getString(data.getColumnIndexOrThrow(VisitorEntry.COLUMN_PURPOSE));
            int status = data.getInt(data.getColumnIndexOrThrow(VisitorEntry.COLUMN_STATUS));

            // store data in the object
            Visitor visitor = new Visitor(visitorID, visitorName, visitorBusiness, visitorPhone, purpose, status);

            Intent intent = new Intent(getContext(), VisitorCheckInActivity.class);

            intent.putExtra("visitor", visitor);
            intent.putExtra("exist", true);
            intent.putExtra("fromVisitor", true);

            data.close();

            startActivity(intent);
        } else {
            new AlertDialog.Builder(getContext())
                    .setTitle("Sign Up Confirmation")
                    .setMessage("Your Number does not exist, Do you want to Sign Up?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {

                            Visitor visitor = new Visitor();
                            visitor.setPhone(inputMobile);

                            Intent intent = new Intent(getActivity(), VisitorCheckInActivity.class);

                            intent.putExtra("visitor", visitor);
                            intent.putExtra("exist", false);
                            intent.putExtra("fromVisitor", true);

                            startActivity(intent);
                        }
                    })
                    .setNegativeButton(android.R.string.no, null).show();

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // do nothing
    }
}
