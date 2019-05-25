package com.example.citeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.LinkedList;

public class Profile_Activity_Gustos_Item_List_View_Adapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    LinkedList<String> gustos;

    public Profile_Activity_Gustos_Item_List_View_Adapter(Context context, LinkedList<String> gustos){
        this.context = context;
        this.gustos = gustos;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return gustos.size();
    }

    @Override
    public Object getItem(int position) {
        return gustos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.activity_profile_gustos_item,null);

        TextView label = (TextView) view.findViewById(R.id.activity_profile_gustos_item_label);

        label.setText(gustos.get(position));
        return view;
    }
}
