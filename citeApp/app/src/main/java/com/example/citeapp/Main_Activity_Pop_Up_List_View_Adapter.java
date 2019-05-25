package com.example.citeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.LinkedList;

public class Main_Activity_Pop_Up_List_View_Adapter extends BaseAdapter {

    private static LayoutInflater inflater;
    private Context context;
    private LinkedList<Person> persons;

    public Main_Activity_Pop_Up_List_View_Adapter(Context context, LinkedList<Person> persons){
        this.context = context;
        this.persons = persons;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return persons.size();
    }

    @Override
    public Object getItem(int position) {
        return persons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

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
