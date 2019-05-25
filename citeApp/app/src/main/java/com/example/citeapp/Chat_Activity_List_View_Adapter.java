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

/**
 * Esta clase crea un adapter que hereda de base adapter para crear una 
 * lista personalizada de los chats
 * @author Carlos Daniel Hernandez Chauteco
 */

public class Chat_Activity_List_View_Adapter extends BaseAdapter {

    String imageUrl;

    private static LayoutInflater inflater;
    private Context context;
    private LinkedList<Message> messages;

    /**
     * @param messages Pasa una lista de mensajes que son los que se desplegaran en la vista de chats 
     * @param context El contexto lo utilizamos para tener una referencia al contexto de la app que nos deja obtener los componentes graficos
     * @param imageUrl El image url nos ayuda a poner la imagen de la otra persona
     */
    public Chat_Activity_List_View_Adapter(LinkedList<Message> messages, Context context, String imageUrl){
        this.messages = messages;
        this.context = context;
        this.imageUrl = imageUrl;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Este metodo cuenta el numero de elementos que tendra el list view y en este caso serian el numero de mesajes que tiene la lista
     */
    @Override
    public int getCount() {
        return messages.size();
    }

    /**
     * Este metodo sirve cuando al adapter se le pregunta un elemento del list view este pueda regresar el modelo original del cual se extrae
     * @param position La posision del elemeto en la lista
     * @return El modelo de datos de la posicion seleccionada 
     */
    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * Este metodo se llama por cada una de las vistas que se crearon antes
     * @param position La posicion de la vista en la tabla
     * @return la vista final que se presentara
     */

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
