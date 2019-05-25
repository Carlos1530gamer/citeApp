package com.example.citeapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Esta clase controla todo la logica e interfaz del fragment de register
 * @author Carlos Daniel Hernandez Chauteco
 */


public class Login_Activity_Register_Fragment extends Fragment {
    //es la imagen por defecto de que se le asigna en la base de datos
    public static final String IMAGE_URL = "https://firebasestorage.googleapis.com/v0/b/citeapp-7010a.appspot.com/o/anonymous-user.png?alt=media&token=3eabfda9-6c66-4b70-b8ed-dcc44e28ef9f";
    FirebaseAuth auth = FirebaseAuth.getInstance();

    EditText emailEditText;
    EditText passwordEditText;
    EditText confirmPasswordEditText;
    Button registerButton;

    /**
     * Este metodo se llama cuando se crea un fragment y se define to lo que los botones hacen y 
     * tambien se guardan la referencia de los widgets
     * @return La vista con la cual se trabaja
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_activity_register_fragment, container, false);

        emailEditText = (EditText) view.findViewById(R.id.login_activity_register_fragment_email_edit_text);
        passwordEditText = (EditText) view.findViewById(R.id.login_activity_register_fragment_password_edit_text);
        confirmPasswordEditText = (EditText) view.findViewById(R.id.login_activity_register_fragment_confirm_password_edit_text);
        registerButton = (Button) view.findViewById(R.id.login_activity_register_fragment_register_button);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerButtonHasPressed(v);
            }
        });

        return view;
    }

    /**
     * Este metodo se llama cuando el boton de registro fue presionado
     * @param view la vista la cual fue presionada
     */
    private void registerButtonHasPressed(View view){
        if(!isEmpty(emailEditText) && !isEmpty(passwordEditText) && equalsEditText(passwordEditText,confirmPasswordEditText)){
            final String email = emailEditText.getText().toString();
            final String password = passwordEditText.getText().toString();
            final Task<AuthResult> task = auth.createUserWithEmailAndPassword(email,password);

            //listener for complete action
            task.addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        doRegister();
                    }else{
                        Toast.makeText(getActivity(),"Error al autenticar: " + task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            Toast.makeText(getContext(),"Rellena todos los campos",Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Esta funcion ayuda a saber si un edit text esta vacio 
     * @param editText el edit text el cual se revisa
     * @return si es cierto
     */
    private boolean isEmpty(EditText editText){
        if(TextUtils.isEmpty(editText.getText().toString())){
            editText.setError("Campo vacio");
            return true;
        }
        return false;

    }

    /**
     * Metodo para saber si dos edit Text son similares
     * @param firstEditText El primer edi text a comparar
     * @param secondEditText El segundo edit text a comparar
     * @return si es cierto
     */
    private boolean equalsEditText(EditText firstEditText, EditText secondEditText){
        final String firstText = firstEditText.getText().toString();
        final String secondText = secondEditText.getText().toString();
        if(firstText.equals(secondText)){
            return true;
        }
        secondEditText.setError("Las contasenias no coinciden");
        return false;
    }
    /**
     * Este metodo se manda a llamar cuando se ha creado exitosamente el ususario
     */
    private void doRegister(){
        //get current user
        final FirebaseUser user = auth.getCurrentUser();
        //create new porfile user data
        final Person person = new Person("",user.getEmail(), IMAGE_URL,"-1");
        //create the new user in the database
        FirestoreUtils.instance.createUser(person,user.getUid());
        //pass to next view
        Intent intent = new Intent(getContext(),MainActivity.class);
        startActivity(intent);
        getActivity().finish();

    }
}
