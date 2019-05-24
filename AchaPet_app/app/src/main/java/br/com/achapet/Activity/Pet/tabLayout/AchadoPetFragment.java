package br.com.achapet.Activity.Pet.tabLayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.achapet.Activity.Pet.listaCard.CardPetAdapter;
import br.com.achapet.Modal.PetModal;
import br.com.achapet.R;

public class AchadoPetFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private CardPetAdapter cardPetAdapter;

    private List<PetModal> petModals;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.pet_fragment_achado, container, false);

        petModals = new ArrayList<PetModal>();

        PetModal petModals1 = new PetModal("NOME1", "DESCRICAO1", "DATA1");
        PetModal petModals2 = new PetModal("NOME2", "DESCRICAO2", "DATA2");
        petModals.add(petModals1);
        petModals.add(petModals2);

        setRecyclerView();

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
