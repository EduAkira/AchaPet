package br.com.achapet.Activity;

import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.HashMap;
import java.util.Map;

import br.com.achapet.Modal.AnimalModal;
import br.com.achapet.R;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class CadrastoAnimalActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String[] raceNames = {"Raça", "Não definido", "Pastor", "Bulldog", "Labrador"};
    private String[] porteAnimal = {"Porte", "Grande", "Medio", "Pequeno"};
    private String[] corAnimal = {"Cor", "Preta", "Branca", "Cinza", "Mesclado"};
    private String[] sexoanimal = {"Macho", "Femea"};
    private String tipoAnimal = "Não definido";
    private String raca;
    private String portes;
    private String cors;
    private String sexo;
    String localidade;
    private RadioGroup rg;
    private EditText mylocation;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadrasto_animal);

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
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, raceNames);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        raca = String.valueOf(spin.getSelectedItem());

        ArrayAdapter porte = new ArrayAdapter(this, android.R.layout.simple_spinner_item, porteAnimal);
        porte.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        portes = String.valueOf(spinPorte.getSelectedItem());

        ArrayAdapter cor = new ArrayAdapter(this, android.R.layout.simple_spinner_item, corAnimal);
        cor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cors = String.valueOf(spinCor.getSelectedItem());

        ArrayAdapter gender = new ArrayAdapter(this, android.R.layout.simple_spinner_item, sexoanimal);
        gender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexo = String.valueOf(spinSexo.getSelectedItem());

        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
        spinPorte.setAdapter(porte);
        spinCor.setAdapter(cor);
        spinSexo.setAdapter(gender);

        //Qual o tipo de animal ESTA ERRADO
        /*rg = (RadioGroup) findViewById(R.id.radio_group);
        tipoAnimal = ((RadioButton) findViewById(rg.getCheckedRadioButtonId())).getText().toString();*/

        //localização escrita
        mylocation = (EditText) findViewById(R.id.location);
        localidade = mylocation.getText().toString();
    }

    public void finalizar(View v){
        AnimalModal bd = new AnimalModal();
        bd.AnimalModal(tipoAnimal, raca, portes, cors, localidade, sexo);



    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}


