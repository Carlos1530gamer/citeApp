package com.example.citeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.LinkedList;

/**
 * Esta clase controla el adapter del list view de chats del MainActivity
 * @author Carlos Daniel Hernandez Chauteco
 */
public class Main_List_View_Adapter<T> extends BaseAdapter {

    //propieties

    private static LayoutInflater inflater;
    private Context context;
    private LinkedList<Person> persons;

    /**
     * @param context El contexto para obetner los xml de UI
     * @param persons la de personas que son los modelos de datos que se vaciaran en el list view
     */
    public Main_List_View_Adapter(Context context, LinkedList<Person> persons){
        this.context = context;
        this.persons = persons;

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * @return El numero de elementos de la tabla
     */
    @Override
    public int getCount() {
        return persons.size();
    }

    /**
     * El metodo regresa el modelo de datos seleccionado
     * @param position posicion en el list view
     * @return El modelo de datos de la vista seleccionada
     */
    @Override
    public Object getItem(int position) {
        return persons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    /***
     * Este metodo antes de poner un elemento en la tabla
     * @param position posicion en la cual se va a poner la vista
     * @return la vista modificada
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view = inflater.inflate(R.layout.main_list_item,null);
        //obteniendo los widgets
        TextView textView = view.findViewById(R.id.main_item_name_label);
        ImageView imageView = view.findViewById(R.id.main_item_user_image);
        //setup the view
        view.setBackgroundResource(R.color.clear);
        textView.setText(persons.get(position).name);
        Picasso.get().load(persons.get(position).imageUrl).into(imageView);
        return view;
    }
}
