package com.example.citeapp;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    FloatingActionButton actionButton;

    FirebaseAuth auth = FirebaseAuth.getInstance();

    //LinkedList<Person> persons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Toast.makeText(this,auth.getCurrentUser().getEmail(),Toast.LENGTH_SHORT).show();

        listView = (ListView) findViewById(R.id.main_activity_list_view);
        actionButton = (FloatingActionButton) findViewById(R.id.main_activity_float_button_search);

        //cargando los chats
        final FirebaseUser user = auth.getCurrentUser();
        final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
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
                            final LinkedList<Person> persons = new LinkedList<>();
                            if(task.isSuccessful()){
                                for(QueryDocumentSnapshot document : task.getResult()){
                                    for(String item : idList){
                                        if(item.equals(document.getId())){
                                            final Map<String,Object> mapUser = document.getData();
                                            Person person = Person.fromhMap(mapUser);
                                            person.chatId = idChatList.get(idList.indexOf(item));
                                            persons.push(person);
                                        }
                                    }
                                }
                            }
                            Main_List_View_Adapter adapter = new Main_List_View_Adapter(getApplicationContext(),persons);
                            listView.setAdapter(adapter);
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

            }
        });

        setupUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

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

    private void setupUI(){
        View rootView = getWindow().getDecorView(); //obtenemos la vista principal
        this.setTitle("Chats");//cambia el nombre del titulo
    }
}
