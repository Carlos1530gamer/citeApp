package com.example.citeapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Login_Activity_Register_Fragment extends Fragment {

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

        return view;
    }
}
