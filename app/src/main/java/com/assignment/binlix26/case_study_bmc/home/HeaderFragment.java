package com.assignment.binlix26.case_study_bmc.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.assignment.binlix26.case_study_bmc.R;

import java.text.SimpleDateFormat;

/**
 * Created by binlix26 on 15/06/17.
 */

public class HeaderFragment extends Fragment {
    private TextView dateDisplay;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_header, container, false);

        dateDisplay = (TextView) view.findViewById(R.id.dateText);
        getDate();
        return view;
    }


    private void getDate(){

        long date = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        String dateString = sdf.format(date);
        dateDisplay.setText(dateString);

    }
}
