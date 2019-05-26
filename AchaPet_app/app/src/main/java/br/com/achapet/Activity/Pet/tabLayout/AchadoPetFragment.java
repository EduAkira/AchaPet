package br.com.achapet.Activity.Pet.tabLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import br.com.achapet.Activity.Pet.listaCard.CardPetAdapter;
import br.com.achapet.Modal.PetModal;
import br.com.achapet.R;

import static br.com.achapet.Modal.PetModal.COLLECTION_PET_ACHADO;
import static br.com.achapet.Modal.PetModal.COLLECTION_PET_PERDIDO;

public class AchadoPetFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private CardPetAdapter cardPetAdapter;
    private FirebaseFirestore db;
    private PetModal petModal;
    private List<PetModal> petModals;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.pet_fragment_achado, container, false);

        petModals = new ArrayList<>();
        db = FirebaseFirestore.getInstance();

        CollectionReference collectionPetAchado = db.collection(COLLECTION_PET_ACHADO);
        collectionPetAchado.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentPetAchado : task.getResult()) {
                        petModal = documentPetAchado.toObject(PetModal.class);
                        petModal.setId(documentPetAchado.getId());
                        petModals.add(petModal);
                        setRecyclerView();
                    }
                }
            }
        });
        return view;
    }

    private void setRecyclerView() {
        recyclerView = view.findViewById(R.id.recycler);

        RecyclerView.LayoutManager layout = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layout);

        cardPetAdapter = new CardPetAdapter(petModals, getContext());
        recyclerView.setAdapter(cardPetAdapter);
    }
}
