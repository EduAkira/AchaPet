package br.com.achapet.Activity.Pet.Card;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.achapet.Modal.PetModal;
import br.com.achapet.R;

public class ListaPetAdapter extends RecyclerView.Adapter<ListaPetViewHolder> {

    private List<PetModal> petModals;
    private Context context;

    public ListaPetAdapter(List<PetModal> petModals, Context context) {
        this.petModals = petModals;
        this.context = context;
    }

    @NonNull
    @Override
    public ListaPetViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.pet_lista_card_item, viewGroup, false);
        return new ListaPetViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListaPetViewHolder listaPetViewHolder, int position) {
        PetModal petModal = petModals.get(position);

        listaPetViewHolder.getDescriptionTextView().setText(petModal.getDescricao());
        listaPetViewHolder.getNameTextView().setText(petModal.getNome());
        listaPetViewHolder.getDateTextView().setText(petModal.getDate());


    }

    @Override
    public int getItemCount() {
        return petModals.size();
    }

}
