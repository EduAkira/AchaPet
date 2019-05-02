package com.example.achapet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class LostPerfil extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private String[] raceNames={"Raça","Não definido", "Pastor", "Bulldog", "Labrador" };
    private String[] porteAnimal={"Porte", "Grande", "Medio", "Pequeno"};
    private String[] corAnimal = {"Cor", "Preta", "Branca", "Cinza", "Mesclado"};
    private String[] sexoanimal = {"Macho","Femea"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_achado_perfil);
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spin = (Spinner) findViewById(R.id.spinner_race);
        spin.setOnItemSelectedListener(this);

        Spinner spinPorte = (Spinner) findViewById(R.id.spinner_porte);
        spinPorte.setOnItemSelectedListener(this);

        Spinner spinCor = (Spinner) findViewById(R.id.spinner_color);
        spinCor.setOnItemSelectedListener(this);

        Spinner spinSexo = (Spinner) findViewById(R.id.spinner_gender);
        spinCor.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,raceNames);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter porte = new ArrayAdapter(this, android.R.layout.simple_spinner_item,porteAnimal);
        porte.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter cor = new ArrayAdapter(this, android.R.layout.simple_spinner_item,corAnimal);
        cor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter gender = new ArrayAdapter(this, android.R.layout.simple_spinner_item,sexoanimal);
        gender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
        spinPorte.setAdapter(porte);
        spinCor.setAdapter(cor);
        spinSexo.setAdapter(gender);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
