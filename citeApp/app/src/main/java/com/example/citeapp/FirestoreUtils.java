package com.example.citeapp;

import android.content.Context;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class FirestoreUtils {
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private Context context;

    //Esta clase solo funciona para hacer mas facil y mas limpio el codifo

    public static FirestoreUtils instance = new FirestoreUtils();

    private FirestoreUtils(){

    }


    public void createUser(Person person, String nameFile){
        CollectionReference reference = firestore.collection("users");
        final Task<Void> task = reference.document(nameFile).set(person.toHashMap());
        //generando las funciones anonimas para que nos avisen de los avanses

        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if(context != null){
                    Toast.makeText(context,"usuario generado correctamente", Toast.LENGTH_SHORT).show();
                }
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exption) {
                Toast.makeText(context,exption.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void setContext(Context context) {
        this.context = context;
    }
}
