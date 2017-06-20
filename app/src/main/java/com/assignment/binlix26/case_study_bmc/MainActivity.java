package com.assignment.binlix26.case_study_bmc;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.assignment.binlix26.case_study_bmc.home.AdminSignInFragment;
import com.assignment.binlix26.case_study_bmc.home.VisitorSigningFragment;
import com.assignment.binlix26.case_study_bmc.utility.Utility;

public class MainActivity extends AppCompatActivity {

    // TODO: 16/06/17 splash screen

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // only for testing photo avatar Bitmap
        Utility.bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.staff);

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
