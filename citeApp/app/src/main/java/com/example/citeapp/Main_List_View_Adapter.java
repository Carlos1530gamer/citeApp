package com.example.citeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Main_List_View_Adapter extends BaseAdapter {

    //propieties

    private static LayoutInflater inflater;
    private Context context;
    private Person[] persons;

    public Main_List_View_Adapter(Context context, Person[] persons){
        this.context = context;
        this.persons = persons;

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return persons.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view = inflater.inflate(R.layout.main_list_item,null);
        //obteniendo los widgets
        TextView textView = (TextView) view.findViewById(R.id.main_item_name_label);
        ImageView imageView = (ImageView) view.findViewById(R.id.main_item_user_image);
        //setup the view
        view.setBackgroundResource(R.color.clear);
        textView.setText(persons[position].name);
        return view;
    }
}
