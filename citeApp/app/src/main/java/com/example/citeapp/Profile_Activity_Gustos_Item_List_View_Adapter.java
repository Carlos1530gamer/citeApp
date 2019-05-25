package com.example.citeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Esta clase es el adapter que conrola el list view de gustos del Activity del perfil
 * @author Carlos Daniel Hernandez Chauteco
 */
public class Profile_Activity_Gustos_Item_List_View_Adapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    LinkedList<String> gustos;

    /**
     * Constructor
     * @param context El contexto que utilizaremos para obtener la vista grafica del xml
     * @param gustos La lista de gustos que se mostran en la lista
     */
    public Profile_Activity_Gustos_Item_List_View_Adapter(Context context, LinkedList<String> gustos){
        this.context = context;
        this.gustos = gustos;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * @return El numero de elementos del list view
     */
    @Override
    public int getCount() {
        return gustos.size();
    }
    
    /**
     * Obtiene el modelo de datos de la vista selecionada 
     * @param position posicion de la vista en el list view
     * @return el modelo de datos de la vista
     */
    @Override
    public Object getItem(int position) {
        return gustos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * Se llama en cada uno de los elementos que se crearan en el list view
     * @param position posicion en la cual de pondra el view
     * @return el view modificado
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.activity_profile_gustos_item,null);

        TextView label = (TextView) view.findViewById(R.id.activity_profile_gustos_item_label);

        label.setText(gustos.get(position));
        return view;
    }
}
