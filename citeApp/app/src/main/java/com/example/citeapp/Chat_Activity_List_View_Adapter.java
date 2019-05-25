package com.example.citeapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.LinkedList;

public class Chat_Activity_List_View_Adapter extends BaseAdapter {

    String imageUrl;

    private static LayoutInflater inflater;
    private Context context;
    private LinkedList<Message> messages;

    public Chat_Activity_List_View_Adapter(LinkedList<Message> messages, Context context, String imageUrl){
        this.messages = messages;
        this.context = context;
        this.imageUrl = imageUrl;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //instancia del mensaje
        Message message = messages.get(position);
        //para saber que no eres el autor
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        View view;
        if(!messages.get(position).autor.equals(user.getUid())){//cuando carga uno que no seas tu para usar la otra
            view = inflater.inflate(R.layout.chat_activity_list_item_emisor,null);

            TextView messageLabel = (TextView) view.findViewById(R.id.chat_activity_list_item_label_emisor);
            ImageView emisorImage = (ImageView) view.findViewById(R.id.chat_activity_list_item_image_emisor);

            Picasso.get().load(imageUrl).into(emisorImage);

            messageLabel.setText(message.message);
        }else{//cuando tu seas el remitente
            view = inflater.inflate(R.layout.chat_activity_list_item_you,null);

            TextView messageLabel = (TextView) view.findViewById(R.id.chat_activity_list_item_label_you);

            messageLabel.setText(message.message);
        }
        return view;
    }
}
