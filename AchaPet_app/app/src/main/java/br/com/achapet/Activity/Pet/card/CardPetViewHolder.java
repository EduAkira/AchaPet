package br.com.achapet.Activity.Pet.card;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import br.com.achapet.R;


public class CardPetViewHolder extends RecyclerView.ViewHolder {

    private CardView cardView;

    public CardPetViewHolder(@NonNull View itemView) {
        super(itemView);
        cardView = itemView.findViewById(R.id.card_view);
    }

    public CardView getCardView() {
        return cardView;
    }
}
