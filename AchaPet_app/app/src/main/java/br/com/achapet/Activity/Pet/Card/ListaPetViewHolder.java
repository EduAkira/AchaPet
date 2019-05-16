package br.com.achapet.Activity.Pet.Card;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.com.achapet.R;

public class ListaPetViewHolder extends RecyclerView.ViewHolder {

    private TextView descriptionTextView;
    private TextView nameTextView;
    private TextView dateTextView;
    private LinearLayout linearCard;

    public ListaPetViewHolder(@NonNull View itemView) {
        super(itemView);
        descriptionTextView = itemView.findViewById(R.id.titulo1);
        nameTextView = itemView.findViewById(R.id.titulo2);
        dateTextView = itemView.findViewById(R.id.titulo3);
        linearCard = itemView.findViewById(R.id.linearCard);
    }

    public TextView getDescriptionTextView() {
        return descriptionTextView;
    }

    public TextView getNameTextView() {
        return nameTextView;
    }

    public TextView getDateTextView() {
        return dateTextView;
    }

    public LinearLayout getLinearCard() {
        return linearCard;
    }
}
