package com.assignment.binlix26.case_study_bmc;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.assignment.binlix26.case_study_bmc.home.AdminSignInFragment;
import com.assignment.binlix26.case_study_bmc.home.VisitorSigningFragment;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


    }

    private void setupViewPager(ViewPager viewPager){
        HomePageAdapter adapter = new HomePageAdapter(getSupportFragmentManager());
        adapter.addFragment(new VisitorSigningFragment(), "Visitor");
        adapter.addFragment(new AdminSignInFragment(), "Admin");
        viewPager.setAdapter(adapter);
    }
}
