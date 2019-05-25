package com.example.citeapp;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Esta clase lo que hace es manejar los fragmentos para el activity de de login y asi que puedas
 * cambiar de fragmento dependiendo del que elijas 
 * @author Carlos Daniel Hernandez Chauteco
 */


public class Login_Activity_Fragment_Page_Adapter extends FragmentPagerAdapter {

    /**
     * @param fragmentManager la clase se inizializa con un FragmentManager para que sea mas facil
     */
    public Login_Activity_Fragment_Page_Adapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    /**
     * Este metodo difiere de en que fragmento de encuentra y que debe hacer en cada caso
     * @param i el indice del fragment en el cual se encuentra
     * @return retorna el fragmento modificado ya sea el de login o registro
     */
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

    /**
     * Define el numero de fragments que tendra el page adapter
     * @return
     */
    @Override
    public int getCount() {
        return 2;
    }

    /**
     * Define el titulo de los fragmentos 
     * @param position la posicion del fragment que esta
     * @return retorna el titulo del fragment
     */

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
