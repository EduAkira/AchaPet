package br.com.achapet.Modal;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class AnimalModal {

    private String tipoAnimal;
    private String raca;
    private String portes;
    private String localidade;
    private String cors;
    private String sexo;
    private FirebaseFirestore db;


    public void AnimalModal(String tipoAnimal, String raca, String portes, String cors, String localidade, String sexo) {
        this.tipoAnimal = tipoAnimal;
        this.raca = raca;
        this.cors = cors;
        this.portes = portes;
        this.localidade = localidade;
        this.sexo = sexo;
        db = FirebaseFirestore.getInstance();
        Map<String, Object> cadastroAnimal = new HashMap<>();
        cadastroAnimal.put("Animal", this.tipoAnimal);
        cadastroAnimal.put("Raça", this.raca);
        cadastroAnimal.put("Porte", this.portes);
        cadastroAnimal.put("Cor", this.cors);
        cadastroAnimal.put("Localidade", this.localidade);
        cadastroAnimal.put("Sexo", this.sexo);


        db.collection("cadastroAnimal").document("Animal")
                .set(cadastroAnimal)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });

    }
}