package com.example.citeapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    FloatingActionButton actionButton;

    FirebaseAuth auth = FirebaseAuth.getInstance();

    Person[] persons = {new Person("Carlos","/"),new Person("Juan","d/asd"),new Person("Carlos","/"),new Person("Carlos","/"),new Person("Carlos","/"),new Person("Carlos","/"),new Person("Carlos","/"),new Person("Carlos","/"),new Person("Carlos","/"),new Person("Carlos","/")};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this,auth.getCurrentUser().getEmail(),Toast.LENGTH_SHORT).show();

        listView = (ListView) findViewById(R.id.main_activity_list_view);
        actionButton = (FloatingActionButton) findViewById(R.id.main_activity_float_button_search);

        listView.setAdapter(new Main_List_View_Adapter(this,persons));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),ChatActivity.class);//pasamos a la siguiente vista
                startActivity(intent);
            }
        });

        actionButton.setOnClickListener(new View.OnClickListener() {//toda esta clase se revisa maniana
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        });

        setupUI();
    }

    private void setupUI(){
        View rootView = getWindow().getDecorView(); //obtenemos la vista principal
        rootView.setBackgroundResource(R.color.backroundApp);
    }
}
