package com.assignment.binlix26.case_study_bmc.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.assignment.binlix26.case_study_bmc.AdminActivity;
import com.assignment.binlix26.case_study_bmc.R;

/**
 * Created by binlix26 on 15/06/17.
 */

public class AdminSignInFragment extends Fragment {

    private static final String PASSWORD = "ad";
    EditText adminPasswordInput;
    Button adminSignInButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in_admin,container,false);

        adminPasswordInput = (EditText) view.findViewById(R.id.adminPasswordInput);
        adminSignInButton = (Button) view.findViewById(R.id.adminSignInButton);

        adminSignInButton.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        adminSignIn();
                    }
                }
        );




        return view;
    }

    public void adminSignIn(){
        //This is where we need to search the database for the admins details
        //If they are correct they will go to the admin page
        //If they cant be found it will call another method to display a pop up box saying the info is incorect could not be found
        String password = adminPasswordInput.getText().toString();

        if (password!=null && password.equals(PASSWORD)) {
            Intent intent = new Intent(getContext(), AdminActivity.class);
            startActivity(intent);
        } else {
            new AlertDialog.Builder(getContext())
                    .setTitle("ERROR")
                    .setMessage("Incorrect password, input again")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, null)
                    .show();
        }
    }
}
