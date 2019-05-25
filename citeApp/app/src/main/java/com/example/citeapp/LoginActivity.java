package com.example.citeapp;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
/**
 * Esta clase es el controlador del activity de Login
 * @author Carlos Daniel Hernandez Chauteco
 */
public class LoginActivity extends AppCompatActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    /**
     * Este metodo se mada a llamar cada que se crea una intancia del activity
     */
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

    /**
     * Verifica si el usuario antes se habia logeado
     */
    private void theUserIsLoged(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();//elimina el activity
    }
}
