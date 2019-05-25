package com.example.citeapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * Clase que controla Main Activity
 * @author Carlos Daniel Hernandez Chauteco
 */
public class MainActivity extends AppCompatActivity {

    ListView listView;
    FloatingActionButton actionButton;
    Dialog popUpSerachFriends;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    LinkedList<Person> persons = new LinkedList<>();
    LinkedList<String> excludeUsers = new LinkedList<>();
    Main_List_View_Adapter mainListViewAdapter;

    /**
     * Se manda a llamar cada que se crea una nueva instancia del main activity 
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.main_activity_list_view);
        actionButton = (FloatingActionButton) findViewById(R.id.main_activity_float_button_search);

        mainListViewAdapter = new Main_List_View_Adapter(this,persons);
        listView.setAdapter(mainListViewAdapter);

        popUpSerachFriends = new Dialog(this);

        //cargando los chats
        CollectionReference chats = firestore.collection("users").document(user.getUid()).collection("chats");

        final Task task = chats.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    //
                    final LinkedList<String> idList = new LinkedList();
                    final LinkedList<String> idChatList = new LinkedList();
                    for (QueryDocumentSnapshot document : task.getResult()){
                        final String idChat  = (String) document.getData().get("idChat");
                        final String idUser = (String) document.getData().get("emisor");
                        idList.push(idUser);
                        idChatList.push(idChat);
                    }
                    final CollectionReference usersRef = firestore.collection("users");
                    usersRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(String user : idList){
                                    for(QueryDocumentSnapshot document : task.getResult()){
                                        if(document.getId().equals(user)){
                                            final Map<String,Object> mapUser = document.getData();
                                            Person person = Person.fromhMap(mapUser);
                                            person.chatId = idChatList.get(idList.indexOf(user));
                                            persons.push(person);
                                            excludeUsers.add(document.getId());
                                            mainListViewAdapter.notifyDataSetChanged();
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    });
                }
            }
        });

        //

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),ChatActivity.class);//pasamos a la siguiente vista
                Person person =(Person) listView.getAdapter().getItem(position);
                intent.putExtra("chatId",person.chatId);
                intent.putExtra("userName",person.name);
                intent.putExtra("imageUrl",person.imageUrl);
                startActivity(intent);
            }
        });

        actionButton.setOnClickListener(new View.OnClickListener() {//toda esta clase se revisa maniana
            @Override
            public void onClick(View v) {
                showPopUpResultsFriends();
            }
        });

        setupUI();
    }

    /**
     * Este metodo obtine los botones de la parte superior de la pantalla
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    /**
     * Esta funcion se manda a llamar cada que se presiona una opcion de la lista superios
     * @param item el item seleccionado
     * @return si este fue seleccionado
     */
    @Override//hace que puedas usar los items del tool bar
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_favorite){
            Intent intent = new Intent(this,ProfileActivity.class);
            startActivity(intent);
        }else if(item.getItemId() == R.id.log_out){
            auth.signOut();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Esta funcion modifica algunas cositas del la UI como el titulo
     */
    private void setupUI(){
        View rootView = getWindow().getDecorView(); //obtenemos la vista principal
        this.setTitle("Chats");//cambia el nombre del titulo
    }

    /**
     * Este metodo controla la logica y vista del pop up que aparece cuando buscas un amigo
     */
    private void showPopUpResultsFriends(){
        popUpSerachFriends.setContentView(R.layout.main_activity_pop_up_search_friends);
        excludeUsers.add(user.getUid());//Obviamente esto debe pasar jejeje

        SharedPreferences preferences = getSharedPreferences("utils", Context.MODE_PRIVATE);
        final Set<String> gustosUsuario = preferences.getStringSet("gustos",new HashSet<String>());
        final LinkedList<Person> posiblesNuevosAmigos = new LinkedList<>();

        final Button popUpCloseButton = (Button) popUpSerachFriends.findViewById(R.id.main_activity_pop_up_close_pop_up);
        ListView popUpListView = (ListView) popUpSerachFriends.findViewById(R.id.main_activity_pop_up_list_view);

        final Main_Activity_Pop_Up_List_View_Adapter popUpAdapter = new Main_Activity_Pop_Up_List_View_Adapter(this,posiblesNuevosAmigos);
        popUpListView.setAdapter(popUpAdapter);

        CollectionReference collectionGustos = firestore.collection("gustos");

        //get documents of gustos
        collectionGustos.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(String gusto : gustosUsuario){
                        for (QueryDocumentSnapshot document : task.getResult()){
                            if(document.getId().equals(gusto)){
                                CollectionReference usersWithEqualGusto = document.getReference().collection("users");

                                usersWithEqualGusto.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()){

                                            for(QueryDocumentSnapshot document : task.getResult()){
                                                final String newUserFriend = (String) document.getData().get("user");
                                                boolean elNuevoUsuarioNoESConocido = true;
                                                for(String excludeUser : excludeUsers){
                                                    if(newUserFriend.equals(excludeUser)){
                                                        elNuevoUsuarioNoESConocido = false;
                                                        break;
                                                    }
                                                }

                                                if(elNuevoUsuarioNoESConocido){
                                                    firestore.collection("users").document(newUserFriend).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                            if(task.isSuccessful()){
                                                                Map<String,Object> nuevoPosibleAmigoMap = task.getResult().getData();
                                                                Person nuevoPosibleAnigo = Person.fromhMap(nuevoPosibleAmigoMap);
                                                                nuevoPosibleAnigo.userId = newUserFriend;
                                                                posiblesNuevosAmigos.add(nuevoPosibleAnigo);
                                                                popUpAdapter.notifyDataSetChanged();
                                                            }else{
                                                                //error
                                                            }
                                                        }
                                                    });
                                                }
                                            }

                                        }else{
                                            //error
                                        }
                                    }
                                });

                                break;
                            }
                        }
                    }
                }else{
                    //error al obtener los documentos
                }
            }
        });

        //agregar un nuevo chat

        popUpListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Person nuevoAmigo = (Person) popUpAdapter.getItem(position);
                //
                final String autor = user.getUid();
                final String remitente = nuevoAmigo.userId;

                final ArrayList<String> participantes = new ArrayList();
                participantes.add(autor);
                participantes.add(remitente);

                Map<String,Object> chatMap = new HashMap<>();
                chatMap.put("anonimo",false);
                chatMap.put("partipantes",participantes);
                //
                firestore.collection("chats").add(chatMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        final String newDocId = documentReference.getId();
                        //restart activity
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        finish();
                        startActivity(intent);
                        //
                    }
                });
            }
        });

        popUpCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpSerachFriends.dismiss();
            }
        });

        popUpSerachFriends.show();
    }
}
