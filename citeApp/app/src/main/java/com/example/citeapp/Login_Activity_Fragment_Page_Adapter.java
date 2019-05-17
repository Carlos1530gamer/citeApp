package com.example.citeapp;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class Login_Activity_Fragment_Page_Adapter extends FragmentPagerAdapter {

    public Login_Activity_Fragment_Page_Adapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment;
        switch (i){
            case 0://login case
                fragment = new Login_Activity_Login_Fragment();
                break;
            case 1:
                fragment = new Login_Activity_Register_Fragment();
                break;
            default:
                fragment = new Fragment();
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String tittle;
        switch (position){
            case 0://login case
                tittle = "Login";
                break;
            case 1:
                tittle = "Register";
                break;
            default:
                tittle = "";
                break;
        }

        return tittle;
    }
}
