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

public class Login_Activity_Register_Fragment extends Fragment {

    FirebaseAuth auth = FirebaseAuth.getInstance();

    EditText emailEditText;
    EditText passwordEditText;
    EditText confirmPasswordEditText;
    Button registerButton;

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

    private boolean isEmpty(EditText editText){
        if(TextUtils.isEmpty(editText.getText().toString())){
            editText.setError("Campo vacio");
            return true;
        }
        return false;

    }

    private boolean equalsEditText(EditText firstEditText, EditText secondEditText){
        final String firstText = firstEditText.getText().toString();
        final String secondText = secondEditText.getText().toString();
        if(firstText.equals(secondText)){
            return true;
        }
        secondEditText.setError("Las contasenias no coinciden");
        return false;
    }

    private void doRegister(){
        Intent intent = new Intent(getContext(),MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
