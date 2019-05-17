package com.example.citeapp;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //verificando que no se haya iniciado session

        if(user != null){
            theUserIsLoged();
        }

        Login_Activity_Fragment_Page_Adapter pageAdapter = new Login_Activity_Fragment_Page_Adapter(getSupportFragmentManager());

        ViewPager viewPager = (ViewPager) findViewById(R.id.login_activity_view_pager);
        viewPager.setAdapter(pageAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.login_activity_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void theUserIsLoged(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();//elimina el activity
    }
}
