package br.com.achapet.Activity.Pet.Card;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.achapet.Activity.Pet.Detalhe.DetalhePetActivity;
import br.com.achapet.Modal.PetModal;
import br.com.achapet.R;

public class CardPetAdapter extends RecyclerView.Adapter<CardPetViewHolder> {

    private List<PetModal> petModals;
    private Context context;

    public CardPetAdapter(List<PetModal> petModals, Context context) {
        this.petModals = petModals;
        this.context = context;
    }

    @NonNull
    @Override
    public CardPetViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.pet_card_item, viewGroup, false);
        return new CardPetViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardPetViewHolder cardPetViewHolder, int position) {
        PetModal petModal = petModals.get(position);

        /*cardPetViewHolder.getDescriptionTextView().setText(petModal.getDescricao());
        cardPetViewHolder.getNameTextView().setText(petModal.getNome());
        cardPetViewHolder.getDateTextView().setText(petModal.getDate());*/

        cardPetViewHolder.getCardView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetalhePetActivity.class);
                intent.putExtra("indexTabLayout", 1);
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return petModals.size();
    }
}
