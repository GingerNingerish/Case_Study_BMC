package com.assignment.binlix26.case_study_bmc;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.assignment.binlix26.case_study_bmc.admin.AppointmentFragment;
import com.assignment.binlix26.case_study_bmc.admin.VisitorFragment;
import com.assignment.binlix26.case_study_bmc.admin.StaffFragment;

/**
 * Created by binlix26 on 13/06/17.
 */

public class AdminPageAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 3;
    private String[] tabTitles = {
            AdminActivity.APP_TAB,
            AdminActivity.VISITOR_TAB,
            AdminActivity.STAFF_TAB
    };
    private Context context;


    public AdminPageAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        if (position == 0) {
            fragment = new AppointmentFragment();
        } else if (position == 1) {

            fragment = VisitorFragment.newInstance();
        } else if (position == 2) {
            fragment = StaffFragment.newInstance();
        } else {
            throw new IllegalArgumentException("Selecting Unknown Tab Position: " + position);
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }


}
