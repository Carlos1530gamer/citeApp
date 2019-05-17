package com.example.citeapp;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Login_Activity_Fragment_Page_Adapter pageAdapter = new Login_Activity_Fragment_Page_Adapter(getSupportFragmentManager());

        ViewPager viewPager = (ViewPager) findViewById(R.id.login_activity_view_pager);
        viewPager.setAdapter(pageAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.login_activity_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}
