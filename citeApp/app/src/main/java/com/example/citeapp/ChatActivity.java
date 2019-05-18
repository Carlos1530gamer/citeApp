package com.example.citeapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;

import javax.annotation.Nullable;

public class ChatActivity extends AppCompatActivity {

    ListView listView;
    Button sendButton;
    EditText sendTextEditText;

    ListenerRegistration listener;
    LinkedList<Message> messages = new LinkedList<>();


    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        setupUI();

        //traer todo el layout

        listView = (ListView) findViewById(R.id.chat_activity_list_view);
        sendButton = (Button) findViewById(R.id.chat_activity_send_button);
        sendTextEditText = (EditText) findViewById(R.id.chat_activity_edit_text);

        final Chat_Activity_List_View_Adapter adapter = new Chat_Activity_List_View_Adapter(messages,getApplicationContext());
        listView.setAdapter(adapter);

        Intent intent = getIntent();
        String chatId = intent.getStringExtra("chatId");
        String userName = intent.getStringExtra("userName");

        setTitle("Message con " + userName);

        final EventListener eventListener = new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException exeption) {
                if(exeption != null){
                    //error
                    return;
                }

                if(!snapshots.isEmpty()){
                    for(DocumentSnapshot document : snapshots.getDocuments()){
                        messages.add(Message.fromMap(document.getData()));
                        adapter.notifyDataSetChanged();
                    }
                }else{
                    //error
                }
            }
        };

        //cargamos los mensages
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();//obtenemos la referencia
        final CollectionReference reference = firestore.collection("chats").document(chatId).collection("messages");

        reference.addSnapshotListener(eventListener);

        //agregando un listener para saber cuando haya cambios


        //accion para el boton de enviar

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String messageText = sendTextEditText.getText().toString();
                if(!TextUtils.isEmpty(messageText)){
                    Message newMessage = new Message(user.getUid(),messageText);
                    reference.document().set(newMessage.toMap());
                }
            }
        });

    }

    @Override
    public void finish() {
        listener.remove();
        super.finish();
    }

    @Override
    public void onBackPressed() {
        //listener.remove();
        super.onBackPressed();
    }

    private void setupUI(){
        View rootView = getWindow().getDecorView(); //obtenemos la vista principal
    }
}
