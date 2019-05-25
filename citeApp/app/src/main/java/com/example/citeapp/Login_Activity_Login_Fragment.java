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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login_Activity_Login_Fragment extends Fragment {

    EditText emailEditText;
    EditText passwordEditText;
    Button loginButton;

    FirebaseAuth auth = FirebaseAuth.getInstance();

    //cuando se crea la vista salta hasta aqui
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_activity_login_fragment, container, false);

        //obteniedo la referencia a los widgets
        emailEditText = (EditText) view.findViewById(R.id.login_activity_login_fragment_email_edit_text);
        passwordEditText = (EditText) view.findViewById(R.id.login_activity_login_fragment_password_edit_text);
        loginButton = (Button) view.findViewById(R.id.login_activity_login_fragment_login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButtonHasPressed(v);
            }
        });

        return view;
    }

    private void loginButtonHasPressed(View view){
        if(!isEmpty(emailEditText) && !isEmpty(passwordEditText)){
            final String email = emailEditText.getText().toString();
            final String password = passwordEditText.getText().toString();
            final Task<AuthResult> task = auth.signInWithEmailAndPassword(email,password);

            task.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        doLogin();
                    }else{
                        Toast.makeText(getContext(),task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            Toast.makeText(getContext(),"Rellena todos los campos",Toast.LENGTH_LONG).show();
        }
    }

    private boolean isEmpty(EditText editText){
        if(TextUtils.isEmpty(editText.getText().toString())){
            editText.setError("Campo vacio");
            return true;
        }
        return false;
    }

    private void doLogin(){
        Intent intent = new Intent(getActivity(),MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
