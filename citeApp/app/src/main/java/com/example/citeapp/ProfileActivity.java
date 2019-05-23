package com.example.citeapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

public class ProfileActivity extends Activity {

    ImageView imageView;
    EditText usernameEditText;
    EditText ageEditText;
    FloatingActionButton actionButton;
    ListView listView;
    Button addGustoButton;
    Dialog popUpAgregarGusto;

    ListenerRegistration userDocListener;
    ListenerRegistration collectionGustosUserListener;

    LinkedList<String> gustos = new LinkedList<>();
    Profile_Activity_Gustos_Item_List_View_Adapter listViewAdapter;

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //get all viewa
        imageView = (ImageView) findViewById(R.id.activity_profile_image_user);
        usernameEditText = (EditText) findViewById(R.id.activity_profile_nombre_edit_text);
        ageEditText = (EditText) findViewById(R.id.activity_profile_edad_user);
        actionButton = (FloatingActionButton) findViewById(R.id.activity_profile_float_button);
        listView = (ListView) findViewById(R.id.activity_profile_list_view);
        addGustoButton = (Button) findViewById(R.id.activity_profile_add_gusto);
        //creando el adapter para que se vean los gustos en el list view

        listViewAdapter = new Profile_Activity_Gustos_Item_List_View_Adapter(this,gustos);

        listView.setAdapter(listViewAdapter);

        final DocumentReference userDocRef = firestore.collection("users").document(user.getUid());
        //para registrar los eventos del documento del ussuario
        userDocListener = userDocRef.addSnapshotListener(new com.google.firebase.firestore.EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException exeption) {
                if(exeption != null){
                    return;
                }

                if(documentSnapshot != null && documentSnapshot.exists()){

                    Map<String, Object> userMap = documentSnapshot.getData();

                    final String userName = (String) userMap.get(Person.NAME);
                    final String ageUser = (String) userMap.get(Person.AGE);
                    final String imageUrl = (String) userMap.get(Person.IMAGE_PATH);

                    if(!imageUrl.equals("")){
                        Picasso.get().load(imageUrl).into(imageView);
                    }
                    usernameEditText.setText(userName);
                    ageEditText.setText(ageUser);

                    todoLosComponentesSonEditables(false);
                }else{
                    return;
                }
            }
        });

        //listener para ver lso gustos del usuario en real time
        final CollectionReference gustosCollectionReference = userDocRef.collection("gustos");

        collectionGustosUserListener = gustosCollectionReference.orderBy("field", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException exeption) {
                if(exeption != null){
                    return;
                }

                if(snapshots!= null && !snapshots.isEmpty()){
                    for(DocumentChange documentChange : snapshots.getDocumentChanges()){
                        Map<String, Object> gustoMap = documentChange.getDocument().getData();

                        final String gusto = (String) gustoMap.get("field");

                        gustos.add(gusto);

                        listViewAdapter.notifyDataSetChanged();
                    }
                }else{
                    return;
                }
            }
        });

        //acccion para el floatButton
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ageEditText.isEnabled() && usernameEditText.isEnabled()){
                    final String newAge = ageEditText.getText().toString();
                    final String newName = usernameEditText.getText().toString();
                    userDocRef.update(Person.NAME,newName);
                    userDocRef.update(Person.AGE,newAge);

                    todoLosComponentesSonEditables(false);
                }else{
                    todoLosComponentesSonEditables(true);
                }
            }
        });

        //mostrar pop up
        popUpAgregarGusto = new Dialog(this);

        addGustoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp();
            }
        });

    }

    @Override
    protected void onStop() {
        if(userDocListener != null){
            userDocListener.remove();
        }
        if(collectionGustosUserListener != null){
            collectionGustosUserListener.remove();
        }

        //guardamos los datos de los gustos

        SharedPreferences preferences = getSharedPreferences("utils",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        final Set<String> setOfGustos = new HashSet<String>();
        for(String gusto : gustos){
            setOfGustos.add(gusto);
        }

        editor.putStringSet("gustos",setOfGustos);
        editor.commit();

        super.onStop();
    }

    private void todoLosComponentesSonEditables(boolean condicion){
        usernameEditText.setEnabled(condicion);
        ageEditText.setEnabled(condicion);
    }

    private void showPopUp(){
        popUpAgregarGusto.setContentView(R.layout.activity_profile_pop_up_add_gusto);

        final EditText newGustoEditText = (EditText) popUpAgregarGusto.findViewById(R.id.activity_profile_pop_up_gusto_edit_text);
        Button addNewGustoButton = (Button) popUpAgregarGusto.findViewById(R.id.activity_profile_pop_up_gusto_add_new_gusto_button);
        Button closePopUp = (Button) popUpAgregarGusto.findViewById(R.id.main_activity_pop_up_close_pop_up);

        addNewGustoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String gusto = newGustoEditText.getText().toString().replace(" ","_").toLowerCase();
                FirestoreUtils.instance.setContext(getApplicationContext());
                FirestoreUtils.instance.addNewGusto(gusto,listViewAdapter.gustos);
                popUpAgregarGusto.dismiss();
            }
        });

        closePopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpAgregarGusto.dismiss();
            }
        });

        popUpAgregarGusto.show();
    }
}
