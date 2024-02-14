package com.example.tp1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    //EXO 3

    private String[] skillDomain = {"Software Engineering", "Artificial Intelligence", "Algorithm", "Other"};
    private EditText ETName, ETSurname, ETPhoneNbr, ETAge, ETSkillDomain;
    private Button sendBtn;

    private ArrayAdapter<String> adapterSkillDomain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //EXO 2
        /*
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        */

        //EXO 3

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        adapterSkillDomain = new ArrayAdapter<>(this, R.layout.list_skilldomain, skillDomain);


        //Classe anonyme utilisant la méthode de callback onClick

    }
}