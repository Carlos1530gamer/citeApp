package com.example.citeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Esta clase es el adapter que se ve cuando te aparecen los nuevos posibles amigos
 * @author Carlos Daniel Hernandez Chauteco
 */
public class Main_Activity_Pop_Up_List_View_Adapter extends BaseAdapter {

    private static LayoutInflater inflater;
    private Context context;
    private LinkedList<Person> persons;

    /**
     * @param context Conexto de la aplicacion para poder obtener la vista grafica
     * @param persons Una lista de personas las cuales seran las que se veran en el adapter
     */
    public Main_Activity_Pop_Up_List_View_Adapter(Context context, LinkedList<Person> persons){
        this.context = context;
        this.persons = persons;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * @return El numero de vistas que tendra el list view
     */
    @Override
    public int getCount() {
        return persons.size();
    }

    /**
     * @param position posicion del item en la lista de vistas
     * @return El modelo de datos de la vista selecionada
     */
    @Override
    public Object getItem(int position) {
        return persons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * Se mada a llamar en cada una de las vistas del list view
     * @return La vista modificada del list view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view = inflater.inflate(R.layout.main_activity_pop_up_item_list_view,null);
        //obteniendo los widgets
        TextView textView = view.findViewById(R.id.main_activity_pop_up_label);
        //setup the view
        view.setBackgroundResource(R.color.clear);
        textView.setText(persons.get(position).name);
        return view;
    }
}
